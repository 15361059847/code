package com.immoc.service.impl;

import com.immoc.client.ProductClient;
import com.immoc.dataobject.OrderDetail;
import com.immoc.dataobject.OrderMaster;
import com.immoc.dataobject.ProductInfo;
import com.immoc.dto.CartDTO;
import com.immoc.dto.OrderDTO;
import com.immoc.enums.OrderStatusEnum;
import com.immoc.enums.PayStatusEnum;
import com.immoc.enums.ResultEnum;
import com.immoc.exception.OrderException;
import com.immoc.repository.OrderDetailRepository;
import com.immoc.repository.OrderMasterRepository;
import com.immoc.service.OrderService;
import com.immoc.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by lenovo on 2019/8/19.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailRepository  orderDetailRepository;
    @Autowired
    private OrderMasterRepository  orderMasterRepository;
    @Autowired
    private ProductClient productClient;


    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.getKey();
        //查询商品信息
        List<String> productIdList = orderDTO.getOrderDetailList().stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());
        List<ProductInfo> productInfoList = productClient.listForOrder1(productIdList);

        //读redis
        //减库存并将新值重新设置进redis(考虑多线程)
        //订单入库异常，手动回滚redis

        /*
        *   异步扣库存分析
        *   1、库存在Redis中保存
        *   2、收到请求Redis判断是否库存充足，减掉Redis中库存
        *   3、订单服务创建订单写入数据库，并发送消息。
        * */


        //计算总价
        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            for(ProductInfo productInfo : productInfoList ){
                if(orderDetail.getProductId().equals(productInfo.getProductId())){
                    //单价* 数量
                    orderAmout = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmout);
                    BeanUtils.copyProperties(productInfo,orderDetail);
                    orderDetail.setOrderId(orderId);
                    orderDetail.setDetailId(KeyUtil.getKey());
                    //订单详情入库
                    orderDetailRepository.save(orderDetail);
                }
            }

        }
        //扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e ->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productClient.decreaseStock(cartDTOList);
        //订单入库
        OrderMaster  orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(String orderId) {
        //1、先查询订单
        Optional<OrderMaster> orderMasterOptional =  orderMasterRepository.findById(orderId);
        if(!orderMasterOptional.isPresent()){
            throw new OrderException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2、判断订单抓状态
        OrderMaster orderMaster =  orderMasterOptional.get();
        if(OrderStatusEnum.NEW.getCode() != orderMaster.getOrderStatus()){
            throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //3、修改订单状态为完结
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderMasterRepository.save(orderMaster);

        //4、查询订单详情

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new OrderException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}

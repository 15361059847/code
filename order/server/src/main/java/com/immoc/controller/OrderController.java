package com.immoc.controller;

import com.immoc.VO.ResultVO;
import com.immoc.converter.OrderForm2OrderDTO;
import com.immoc.dto.OrderDTO;
import com.immoc.enums.ResultEnum;
import com.immoc.exception.OrderException;
import com.immoc.form.OrderForm;
import com.immoc.service.OrderService;
import com.immoc.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2019/8/19.
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {


    @Autowired
    private OrderService orderService;
    /**
     * 1、参数校验
     * 2、查询商品信息
     * 3、计算总价
     * 4、扣库存(调用商品服务)
     * 5、订单入库
     */
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("[创建订单] 参数不正确，orderForm = {}",orderForm);
            throw new OrderException(bindingResult.getFieldError().getDefaultMessage(), ResultEnum.PARAM_ERROR.getCode());
        }

        //orderForm --> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购策车信息为空");
            throw new OrderException(ResultEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());
        return ResultVOUtil.success(map);
    }
}

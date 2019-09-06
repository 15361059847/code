package com.immoc.service;

import com.immoc.dto.OrderDTO;

/**
 * Created by lenovo on 2019/8/19.
 */
public interface OrderService {

    /**
     *  创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);
}

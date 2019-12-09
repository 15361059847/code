package com.imooc.miaosha.rabbitmq.model;

import com.imooc.miaosha.user.model.MiaoShaUser;
import lombok.Data;

@Data
public class MiaoshaMessage {
    private MiaoShaUser miaoShaUser;
    private long goodsId;
}

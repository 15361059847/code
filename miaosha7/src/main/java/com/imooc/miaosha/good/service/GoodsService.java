package com.imooc.miaosha.good.service;

import com.imooc.miaosha.good.model.Goods;
import com.imooc.miaosha.vo.GoodsVo;

import java.util.List;

/**
 * Created by lenovo on 2019/10/31.
 */
public interface GoodsService {
    List<GoodsVo> getGoodsVoList();

    GoodsVo getGoodsVoByGoodsId(long goodsId);

    boolean reduceStock(Goods g);
}

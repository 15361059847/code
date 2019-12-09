package com.imooc.miaosha.good.service.impl;

import com.imooc.miaosha.good.dao.GoodsDao;
import com.imooc.miaosha.good.model.Goods;
import com.imooc.miaosha.good.model.MiaoshaGoods;
import com.imooc.miaosha.good.service.GoodsService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lenovo on 2019/10/31.
 */
@Service
public class GoodsServiceImpl implements GoodsService{

    @Autowired(required = false)
    private GoodsDao goodsDao;

    @Override
    public List<GoodsVo> getGoodsVoList() {
        return goodsDao.getGoodsVoList();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    public boolean reduceStock(Goods goods) {
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(g);
        return  ret > 0;
    }
}

package com.imooc.miaosha.good.dao;

import com.imooc.miaosha.good.model.Goods;
import com.imooc.miaosha.good.model.MiaoshaGoods;
import com.imooc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by lenovo on 2019/10/31.
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.miaosha_price,mg.stock_count, mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> getGoodsVoList();


    @Select("select g.*,mg.miaosha_price,mg.stock_count, mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);

    @Update("update miaosha_goods set stock_count = stock_count -1 where goods_id = #{goodsId}")
    int reduceStock(MiaoshaGoods g);
}

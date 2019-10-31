package com.imooc.miaosha.good.dao;

import com.imooc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by lenovo on 2019/10/31.
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*,mg.miaosha_price,mg.stock_count, mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> getGoodsVoList();
}

package cn.wolfcode.mycat.mapper;


import cn.wolfcode.mycat.domain.Config;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConfigMapper {
    @Insert("insert into sys_config(id,code) value (#{id},#{code})")
    int insert(Config config);
    @Select("select * from sys_config")
    List<Config> selectAll();
}

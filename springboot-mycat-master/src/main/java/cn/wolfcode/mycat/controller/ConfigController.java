package cn.wolfcode.mycat.controller;

import cn.wolfcode.mycat.domain.Config;
import cn.wolfcode.mycat.domain.User;
import cn.wolfcode.mycat.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by wolfcode-lanxw
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private ConfigMapper configMapper;

    @RequestMapping("/save")
    public String save(Config config){
        configMapper.insert(config);
        return "保存成功";
    }

    @RequestMapping("/list")
    public List<Config> list(){
        return configMapper.selectAll();
    }
}

package com.imooc.miaosha.order.service.impl;

import com.imooc.miaosha.good.service.GoodsService;
import com.imooc.miaosha.order.model.MiaoshaOrder;
import com.imooc.miaosha.order.model.OrderInfo;
import com.imooc.miaosha.order.service.MiaoshaService;
import com.imooc.miaosha.order.service.OrderService;
import com.imooc.miaosha.redis.service.RedisService;
import com.imooc.miaosha.redis.service.impl.MiaoShaKey;
import com.imooc.miaosha.user.model.MiaoShaUser;
import com.imooc.miaosha.util.MD5Util;
import com.imooc.miaosha.util.UUIDUtil;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by lenovo on 2019/11/1.
 */
@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired(required = false)
    private GoodsService goodsService;

    @Autowired(required = false)
    private OrderService orderService;

    @Autowired(required = false)
    private RedisService redisService;


    @Transactional
    public OrderInfo miaosha(MiaoShaUser user, GoodsVo goods) {
        //减库存、下订单、写入秒杀订单
        Boolean success = goodsService.reduceStock(goods);
        if(success){
            //减库存成功，往redis写一个标记。表示该用户已经秒杀该商品。
            setGoodsOver(goods.getId());
            return orderService.createOrder(user,goods);
        }
        return null;
    }

    @Override
    public long getMiaoshaResult(Long userid, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userid,goodsId);
        if(order != null){
            return order.getId();
        }else{
             boolean isOver = getGoodsOver(goodsId);
             if(isOver){
                return -1;
             }else {
                return 0   ;
             }
        }
    }

    @Override
    public boolean checkPath(MiaoShaUser user, long goodsId, String path) {
        if(user == null || path == null){
            return false;
        }
        String pathOld = redisService.get(MiaoShaKey.getMiaoShaPath,user.getId()+"_" + goodsId,String.class);
        return path.equals(pathOld);
    }

    @Override
    public String createMiaoshaPath(MiaoShaUser user, long goodsId) {
        String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
        redisService.set(MiaoShaKey.getMiaoShaPath,user.getId()+"_" + goodsId,str);
        return str;
    }

    @Override
    public BufferedImage createVerifyCode(MiaoShaUser user, long goodsId) {
        if(user == null || goodsId <=0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        //生成随机代码
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoShaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId, rnd);
        //输出图片
        return image;
    }

    public boolean checkVerifyCode(MiaoShaUser user, long goodsId, int verifyCode) {
        if(user == null || goodsId <=0) {
            return false;
        }
        Integer codeOld = redisService.get(MiaoShaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId, Integer.class);
        if(codeOld == null || codeOld - verifyCode != 0 ) {
            return false;
        }
        redisService.delete(MiaoShaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId);
        return true;
    }

    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     * + - *
     * */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoShaKey.idGoodsOver,""+goodsId,true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoShaKey.idGoodsOver,""+goodsId);
    }




}

package com.immoc.message;

import com.immoc.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * Created by lenovo on 2019/9/9.
 */
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

//    @StreamListener(value = StreamClient.INPUT )
//    public void process(Object message){
//        log.info("StreamReceiver: {} ",message);
//    }

    /**
     * 接收OrderDTO对象消息
     * @param message
     */
    @StreamListener(value = StreamClient.INPUT)
    public void process(OrderDTO message){
        log.info("StreamReceiver: {} ",message.getOrderId());
    }
}

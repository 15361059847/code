package com.immoc.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;


/**
 * Created by lenovo on 2019/9/9.
 */
public interface StreamClient {
    String INPUT = "myMessage1";
    String OUTPUT = "myMessage2";


    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.OUTPUT)
    MessageChannel output();
}

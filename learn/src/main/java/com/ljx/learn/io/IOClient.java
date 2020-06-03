package com.ljx.learn.io;

import java.net.Socket;
import java.util.Date;

public class IOClient {

    public static void main(String[] args) {
        while (true) {
            Thread t = new Thread(new T());
            t.start();
        }
    }
}

class T implements Runnable {

    public void run() {
            try {
                Socket socket = new Socket("192.168.1.216", 4444);
                socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}

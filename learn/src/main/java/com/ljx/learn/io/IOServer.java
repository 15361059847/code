package com.ljx.learn.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOServer {
   public static void main(String[] args) throws IOException {
           ServerSocket serverSocket = new ServerSocket(4444);
//           Thread thread = new Thread(new B(serverSocket));
//           thread.start();
           ExecutorService threadPool = Executors.newCachedThreadPool();
           for(int i=0;i<2;i++) {
               threadPool.execute(new B(serverSocket));
           }

   }
}
class B implements Runnable {
    private ServerSocket serverSocket;
    public B(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        int len;
                        byte[] data = new byte[1024];
                        InputStream inputStream = socket.getInputStream();
                        // 按字节流方式读取数据
                        while ((len = inputStream.read(data)) != -1) {
                            System.out.println(new String(data, 0, len));
                        }
                    } catch (IOException e) {
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
package com.kxw.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kangxiongwei on 2017/4/17.
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress("localhost", 8888));

        while (true) {
            Socket socket = server.accept();
            new Thread(new SocketThread(socket)).start();
        }

    }

    static class SocketThread implements Runnable {

        Socket socket;

        public SocketThread (Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();


                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String param = reader.readLine();
                System.out.println("从客户端接收到字符串" + param);

                PrintWriter writer = new PrintWriter(out);
                writer.println("ok-" + param);
                writer.flush();

                writer.close();
                reader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

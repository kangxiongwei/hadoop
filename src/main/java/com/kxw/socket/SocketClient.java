package com.kxw.socket;

import java.io.*;
import java.net.Socket;

/**
 * Created by kangxiongwei on 2017/4/17.
 */
public class SocketClient {


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        PrintWriter writer = new PrintWriter(out);
        writer.println("hello socket");
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String param = reader.readLine();
        System.out.println("服务端返回的字符串为：" + param);

        writer.close();
        reader.close();
        socket.close();
    }

}

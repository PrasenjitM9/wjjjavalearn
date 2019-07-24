package com.wjjzst.juc.learn._26httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @Author: Wjj
 * @Date: 2019/7/22 8:30
 * @desc:
 */
public class HttpServer {
    public static void main(String[] args) throws IOException {
        // 启动服务器 监听8888端口
        ServerSocket server = new ServerSocket(9999);
        System.out.println("服务器启动,监听" + 9999 + "端口");
        while (!Thread.interrupted()) {
            // 不停接收客户端请求
            Socket client = server.accept();
            System.out.println("客户端连接成功");
            // 获取输入流
            InputStream ins = client.getInputStream();
            OutputStream out = client.getOutputStream();
            // 读取请求内容
            /*int len = 0;
            byte[] b = new byte[1024];
            while ((len = ins.read(b)) != -1) {
                System.out.println(new String(b, 0, len));
            }*/
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String line = reader.readLine();
            // 第一行请求行
            System.out.println(line);
            // 给用户响应
            PrintWriter pw = new PrintWriter(out);
            InputStream i = new FileInputStream("C:/Users/Administrator/IdeaProjects/javalearn/juc/src/main/resources/index1.html");
            BufferedReader fr = new BufferedReader(new InputStreamReader(i));
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type: text/html;charset=utf-8");
            pw.println("Content-Length: " + i.available());
            pw.println("Server: hello");
            pw.println("Date: " + new Date());
            pw.println("");
            pw.flush();
            String c = null;
            while ((c = fr.readLine()) != null) {
                pw.print(c);
            }
            pw.flush();
            client.close();
        }
        server.close();
    }
}

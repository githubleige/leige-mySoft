package it.heima.socketProgram.HalfClose;


import java.io.*;
import java.net.*;
import java.util.*;

public class Client
{
    public static void main(String[] args)
            throws Exception
    {
        Socket s = new Socket("localhost" , 30000);
        Scanner scan = new Scanner(s.getInputStream());
        //在服务端通过调用socket.shutdownOutput();方法关闭了输出流，这边客户端的输入流就不会在scan.hasNextLine()阻塞。
        while (scan.hasNextLine())
        {
            System.out.println(scan.nextLine());
        }
        PrintStream ps = new PrintStream(s.getOutputStream());
        ps.println("客户端的第一行数据");
        ps.println("客户端的第二行数据");
        ps.close();
        scan.close();
        s.close();
    }
}



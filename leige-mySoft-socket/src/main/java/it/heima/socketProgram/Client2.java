package it.heima.socketProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) throws IOException {
        Socket socket=new Socket("192.168.0.105",30000);
        System.out.println("socket创建成功");
        BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("获取流成功");
        //Socket s = ss.accept();只要在服务端这段代码执行完了（也就是服务端监听到client2连接并创建成功socket），上面的代码都能执行成功，并且不会阻塞
//        System.out.println(br.readLine());
        String str;
        //因为是阻塞式的io,所以只有在服务端的对应的socket输出流关闭后这个br.readLine()才会立即返回null,否则会一直阻塞等待server端对client端的输入
        //服务端输出流关闭后才能跳出下面的while循环（就不会阻塞线程了）
        while((str=br.readLine())!=null){
            System.out.println(str);
        }
        br.close();
        socket.close();
    }
}

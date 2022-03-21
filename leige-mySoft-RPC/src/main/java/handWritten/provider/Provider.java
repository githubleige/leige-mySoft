package handWritten.provider;

import handWritten.framework.Protocol;
import handWritten.framework.ProtocolFactory;
import handWritten.framework.URL;
import handWritten.framework.register.LocalRegister;
import handWritten.framework.register.RemoteMapRegister;
import handWritten.provider.api.HelloService;
import handWritten.provider.impl.HelloServiceImpl;

public class Provider {

    private static boolean isRun = true;

    public static void main(String[] args) {
        // 1. 注册服务
        // 2. 本地注册
        // 3. 启动tomcat

        // 注册服务
        URL url = new URL("localhost", 8080); //NetUtil
        RemoteMapRegister.regist(HelloService.class.getName(), url);

        //  服务：实现类
        LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl.class);


        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(url);


    }
}

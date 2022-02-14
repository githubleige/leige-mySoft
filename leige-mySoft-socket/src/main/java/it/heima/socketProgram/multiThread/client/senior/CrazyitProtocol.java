package it.heima.socketProgram.multiThread.client.senior;


public interface CrazyitProtocol
{
    // 定义协议字符串的长度
    int PROTOCOL_LEN = 2;
    // 下面是一些协议字符串，服务器和客户端交换的信息
    // 都应该在前、后添加这种特殊字符串。
    //如果是广播的信息的前后接入的字符串
    String MSG_ROUND = "§γ";
    //用户名前后拼接的字符串
    String USER_ROUND = "∏∑";
    //如果用户名登录成功的话，返回的响应符号
    String LOGIN_SUCCESS = "1";
    //如果用户登录时候，用户名已经存在的情况下，返回的响应符号
    String NAME_REP = "-1";
    //私线转发的标志符
    String PRIVATE_ROUND = "★【";
    //用户名和发送的用户信息间隔符
    String SPLIT_SIGN = "※";
}

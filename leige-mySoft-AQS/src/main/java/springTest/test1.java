package springTest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        User user = ac.getBean(User.class);
        System.out.println(user);
        // 关闭销毁
        ac.registerShutdownHook();
    }
}

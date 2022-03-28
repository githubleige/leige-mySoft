package com.zhouyu;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

public class mainCallbakfilter {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallbackFilter(new OrderDTOCallbackFilter());
        Callback[] callbacks=new Callback[]{new MyMethodInterceptor(),new MyMethodInterceptor1(),new NoOpImpl()};
        enhancer.setCallbacks(callbacks);
        UserService userService = (UserService) enhancer.create();
        userService.test();
    }
}

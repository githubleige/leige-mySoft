package com.zhouyu;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class OrderDTOCallbackFilter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if (method.getName().startsWith("test"))
        return 0;
        else if(method.getName().startsWith("test1")){
            return 2;
        }
        else return 1;
    }
}

package cglib.study2;


import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class filter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if(method.getName().equals("toString")) {
            return 1;
        }
        return 0;
    }
}


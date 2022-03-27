package cglib.study3;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class filter implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if(method.getName().equals("toString")||method.getName().equals("hello")) {
            return 1;
        }
        return 0;
    }
}

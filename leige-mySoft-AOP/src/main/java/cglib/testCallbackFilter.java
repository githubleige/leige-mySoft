package cglib;

import org.springframework.cglib.proxy.CallbackHelper;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;
import org.springframework.cglib.proxy.NoOp;


import java.lang.reflect.Method;

public class testCallbackFilter {
    public static void main(String[] args) throws Exception {
        testCallbackFilter();
    }

    public static void testCallbackFilter() throws Exception {
            Enhancer enhancer = new Enhancer();
            CallbackHelper callbackHelper = new CallbackHelper(SampleClass.class, new Class[0]) {
                @Override
                protected Object getCallback(Method method) {
                    if (method.getDeclaringClass() == Object.class ) {
                        return new FixedValue() {
                            @Override
                            public Object loadObject() throws Exception {
                                return "Hello cglib";
                            }
                        };
                    } else {
                        return NoOp.INSTANCE;
                    }
                }
            };
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        SampleClass proxy = (SampleClass) enhancer.create();
        System.out.println(proxy.test());
        proxy.name="sg";
        System.out.println(proxy.name);
    }
}

class SampleClass{
    public String name;
    public String test(){
        return "ebgejhbv";
    }
}
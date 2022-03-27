package cglib.study2;

import net.sf.cglib.proxy.Callback;

public class test1 {
    public static void main(String[] args) {
        Boolean classOnly=true;
//        Callback[] callbacks =new Callback[]{};
        Callback[] callbacks=null;
        System.out.println(classOnly ^ (callbacks == null));

    }
}

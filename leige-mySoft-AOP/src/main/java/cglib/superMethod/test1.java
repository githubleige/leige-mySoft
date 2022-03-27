package cglib.superMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class test1 {
    public static void main(String[] args) throws NoSuchMethodException, SecurityException{
        superClass aa=new subClass();
        Constructor<? extends superClass> cc=aa.getClass().getConstructor(new Class<?>[]{});
        Method method=aa.getClass().getMethod("print",new Class<?>[]{});
        System.out.println(method.getDeclaringClass());
        System.out.println(cc.toString());

    }
}


class superClass{
    public void print(){

    }
}


class subClass extends superClass{

    public subClass(){

    }

    @Override
    public void print(){

    }
}
package cglib.typeTest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.lang.reflect.Field;

public class test6 {
    public static void main(String[] args) {
        Class clazz=beanTest.class;
        Field[] fields=clazz.getDeclaredFields();
        ParameterizedType type=(ParameterizedType)fields[0].getGenericType();
        Type[] types=type.getActualTypeArguments();
        for(Type e : types){
            System.out.println(e);
        }
    }
}

class beanTest{
    Map<Integer,String> map;
}
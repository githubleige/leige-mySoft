package cglib.typeTest;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class test4 {
    public static void main(String[] args) {
        Field[] fields=genericParam.class.getDeclaredFields();
        //class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
        System.out.println(fields[0].getGenericType().getClass());
        for(Field field : fields){
            ParameterizedType pType=(ParameterizedType)field.getGenericType();
            Type type=pType.getActualTypeArguments()[0];
            System.out.println(type.getClass());
            //这一行在带泛型的属性中：
            //如果泛型不确定，那么type的真实类型就是：class sun.reflect.generics.reflectiveObjects.TypeVariableImpl
            //如果不带泛型(ParameterizedType)field.getGenericType()转化就会失败，因为返回的是一个Class对象
            //如果确定泛型（例如List<String>），那么type类型就是class java.lang.Class，tClass就是class java.lang.String
            Class tClass = (Class) type;
            System.out.println(tClass);
//            System.out.println(type.getClass());
//            Class clazz=(Class)type;
//            System.out.println();
        }
    }
}


class genericParam<T>{
    T list;
}

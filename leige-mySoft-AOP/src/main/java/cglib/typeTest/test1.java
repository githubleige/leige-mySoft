package cglib.typeTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class test1 {
    public static List<String> list=new ArrayList<>();

    public static List list1=new ArrayList();

    public static void main(String[] args) throws Exception{
        Field field=test1.class.getField("list");
        //interface java.util.List,返回的是Class对象
        System.out.println(field.getType());
        //java.util.List<java.lang.String>,返回的是Type对象
        System.out.println(field.getGenericType());
        System.out.println(field.getGenericType().getTypeName());
        System.out.println(field.getGenericType().getClass());
        System.out.println(List.class);
        System.out.println(test1.class);
        //返回的是实际类型的Class对象
        System.out.println(list.getClass());
    }
}

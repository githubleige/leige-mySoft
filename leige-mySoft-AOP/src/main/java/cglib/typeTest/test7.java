package cglib.typeTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class test7 {

    private List<String> list=new ArrayList<>();
    {
        list.add("a");
        list.add("b");
    }

    public static void main(String[] args) throws Exception{
        test7 aa=new test7();
        Field field=aa.getClass().getDeclaredField("list");
        Class<?> clazz=field.get(aa).getClass();

        System.out.println(field.get(aa).getClass().toString());
        System.out.println(field.get(aa));
        System.out.println(field.get(aa)==aa.list);
    }
}

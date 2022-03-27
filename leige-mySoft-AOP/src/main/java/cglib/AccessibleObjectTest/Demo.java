package cglib.AccessibleObjectTest;

import java.lang.reflect.Field;

public class Demo {
    public static void main(String[] args) throws Exception {
        Class clazz = User.class;
        User u = new User("小明");
        for (Field f : clazz.getDeclaredFields()) {
            System.out.println(f.isAccessible());//这里的结果是false
            f.setAccessible(true);
            System.out.println(f.getName()+":"+f.get(u));
        }
    }
}


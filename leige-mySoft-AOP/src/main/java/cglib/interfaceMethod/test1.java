package cglib.interfaceMethod;

public class test1 {
    public static void main(String[] args) {
        Class<C> clazz=C.class;
        System.out.println(clazz.getSuperclass());
        System.out.println(clazz.getInterfaces());
        Class[] interfaces = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
//            addAllMethods(interfaces[i], list);

            System.out.println(interfaces[i].getDeclaredMethods());
        }
    }
}

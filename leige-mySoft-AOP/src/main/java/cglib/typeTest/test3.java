package cglib.typeTest;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class test3 {
    public static void main(String[] args) {
        GericObj<String,Integer> g=new GericObj<>();
        Class geric=g.getClass();
        Field[] fields=geric.getDeclaredFields();
        if(fields!=null){
            for(Field field : fields){
                //在GericObj类里声明了5个属性，带有泛型T的List<T>items,泛型类型是string的names，
                // 不带泛型的gericObj,带有泛型的gericObj2和不带泛型的List list ，还有泛型变量T t。
                // 使用field.getGenericType()方法测试，
                // 只要是带有泛型的不论泛型是指定类型比如指定string类型的names还是未指定确定类型的
                // items或者是gericObj2通过Field的getGenericType()方法获取的类型都是ParameterizedType类型，
                // 而不带泛型的List  list和gericObj获取到的Type类型是Class类型，而泛型变量T t获取到的是TypeVariable。

                //总结：带泛型的属性（不管泛型是否已经被完全指定）返回的是ParameterizedType类型
                //不带泛型的属性返回的是Class类型
                //泛型变量T t获取到的是TypeVariable
                System.out.println(field.getName());
                Type genericType=field.getGenericType();
                System.out.println(genericType.getClass());
                if(genericType instanceof ParameterizedType){
                    ParameterizedType parameterizedType=(ParameterizedType)genericType;
                    //获取的是泛型尖括号前面类型，比如List<T>获取的就是List，此时的Type就是class类型
                    Type rawType=parameterizedType.getRawType();
                    System.out.println(rawType.getClass());
                    System.out.println(rawType);
                    //比如这种Map<String,String>.Entry<String,String>这时候获取的就是Map<String,String>
                    //返回的是内部类所属的外部类，如果没有内部类返回null
                    Type ownerType=parameterizedType.getOwnerType();
//                    System.out.println(ownerType.getClass());
//                    System.out.println(ownerType);
                    String typrName=parameterizedType.getTypeName();
                    Class<? extends ParameterizedType> aClass=parameterizedType.getClass();
                    Type[] actualTypeArguments=parameterizedType.getActualTypeArguments();
                    Class tClass = (Class) parameterizedType.getActualTypeArguments()[0];
                    if(actualTypeArguments!=null){
                        for(Type actualTypeArgument : actualTypeArguments){
//                            Class tClass = (Class)actualTypeArgument;
                            System.out.println(actualTypeArgument.getClass());
                            System.out.println(actualTypeArgument);
                        }
                    }
                }
                int modifiers=field.getModifiers();
                Class<?> type=field.getType();
                System.out.println(genericType);
                System.out.println("------------------------------");
            }

        }
    }
}

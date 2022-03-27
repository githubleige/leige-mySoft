package cglib.typeTest;

import java.lang.reflect.TypeVariable;

public class GericObjTest {
    public static void main(String[] args) {
        GericObj<String,Integer> g=new GericObj<>();
        Class geric=g.getClass();
//        Class geric1=GericObj.class;
        //TypeVariable是Type的子类
        TypeVariable<Class>[] typeParameters=geric.getTypeParameters();
        if(null!=typeParameters){
            for(TypeVariable<Class> e : typeParameters){
                System.out.println(e);
            }
        }
    }
}

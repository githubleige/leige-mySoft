package cglib.typeTest;

import java.util.List;

public class GericObj<T,E> {

//    private GericObj.innerClass gericObj3;




    //
//    private List<T> items;

    private List<GericObj<String,Integer>> names;

    private GericObj gericObj;

    private GericObj<T,E> gericObj2;

    private List list;

    private T t;

    static class innerClass{

    }

    private <G> T getItem(T t,G e){
        return t;
    }

    public GericObj<T,E> test(List<T> items,GericObj<String,Integer> g,T t){
        return null;
    }
}

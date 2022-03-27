package JDKAop.weakCacheTest;

public class test1 extends super1 implements interface1{
    public static void main(String[] args) {
        test1 aa=new test1();
        System.out.println(aa.get());
    }
}


interface interface1{
    int  get();
}

class super1{
    public int get(){
        return 1;
    }
}

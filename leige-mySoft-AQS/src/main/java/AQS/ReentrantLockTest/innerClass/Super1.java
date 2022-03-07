package AQS.ReentrantLockTest.innerClass;

public class Super1 {
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public class Sub1 implements Isub1{
        //实例内部类里面不能定义静态属性
//        static int a=0;
        @Override
        public int get(){
            return getState();
        }
    }

    private void test(){
        Sub1 sub1 = new Sub1();
        sub1.get();
    }

    public Sub1 getSub1(){
        return new Sub1();
    }

    public static void main(String[] args) {
//        sub1 sub1 = new sub1();
//        super1 super1=new super1();
    }
}



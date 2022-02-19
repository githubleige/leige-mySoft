package volatileTest;

public class FinalInit {
    public final int a;
    public static FinalInit thisPoint;
    public FinalInit() throws InterruptedException {
        //引用逃逸
        thisPoint = this;

        Thread.sleep(5000);
        //由于上面的thisPoint已经被初始化，所以此时FinalInit.thisPoint！=null
        a = 10;
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                new FinalInit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);
        //输出未初始化的final值0
        System.out.println(FinalInit.thisPoint.a);
    }
}


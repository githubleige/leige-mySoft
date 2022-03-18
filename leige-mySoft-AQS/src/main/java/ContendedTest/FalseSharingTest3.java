package ContendedTest;


public class FalseSharingTest3 {

    //-XX:-RestrictContended
    public static void main(String[] args) throws InterruptedException {
        testPointer(new Pointer());
    }

    private static void testPointer(Pointer pointer) throws InterruptedException{
        long start  = System.currentTimeMillis();
        Thread t1 = new Thread(() -> {
            for(int i=0;i<1000000000;i++){
                pointer.x++;
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i=0;i<1000000000;i++){
                pointer.y++;
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("cost ["+(System.currentTimeMillis()-start)+"] ms");
        System.out.println(pointer);
    }


}

/*@sun.misc.Contended*/
class Pointer{
//            @sun.misc.Contended
    volatile long x;
//            @sun.misc.Contended
    volatile long y;

    @Override
    public String toString() {
        return "Pointer{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}


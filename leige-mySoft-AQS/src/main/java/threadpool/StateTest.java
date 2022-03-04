package threadpool;

import java.util.concurrent.atomic.AtomicInteger;

public class StateTest {
    //29
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    //注意的-1的补码是32个1,进行-1 << COUNT_BITS;  计算后，变成：111+29个0
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;
    public static void main(String[] args) {
        System.out.println(StateTest.RUNNING);
//        System.out.println(StateTest.SHUTDOWN);
//        System.out.println(StateTest.STOP);
//        System.out.println(StateTest.TIDYING);
//        System.out.println(StateTest.TIDYING);
//        System.out.println(StateTest.TERMINATED);
//        StateTest aa=new StateTest();
//        System.out.println(aa.ctl);
//        System.out.println(-1|1);
        StateTest aa=new StateTest();
        System.out.println(aa.ctl.get());
        System.out.println(aa.ctl.get()+1);
        aa.add();
        System.out.println(aa.ctl.get());
    }



    private static int ctlOf(int rs, int wc) { return rs | wc; }

    private void add() {
        int expect = ctl.get();
        while (!ctl.compareAndSet(expect, expect + 1)) {
        }
    }
}

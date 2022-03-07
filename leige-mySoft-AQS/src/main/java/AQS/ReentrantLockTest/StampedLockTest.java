package AQS.ReentrantLockTest;

import java.util.concurrent.locks.StampedLock;


/**
 * @author Fox
 */
public class StampedLockTest{

    public static void main(String[] args) throws InterruptedException {
        Point point = new Point();

        //第一次移动x,y
        new Thread(()-> point.move(100,200)).start();
        Thread.sleep(100);
        new Thread(()-> point.distanceFromOrigin()).start();
        Thread.sleep(500);
        //第二次移动x,y
        new Thread(()-> point.move(300,400)).start();

    }
}

//@Slf4j
class Point {
    private final StampedLock stampedLock = new StampedLock();

    private double x;
    private double y;

    public void move(double deltaX, double deltaY) {
        System.out.println();
        // 获取写锁
        long stamp = stampedLock.writeLock();
        System.out.println("获取到writeLock");
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            // 释放写锁
            stampedLock.unlockWrite(stamp);
            System.out.println("释放writeLock");
        }
    }

    /**
     * 计算当前坐标到原点的距离
     * @return
     */
    public double distanceFromOrigin() {
        // 获得一个乐观读锁
        long stamp = stampedLock.tryOptimisticRead();
        // 注意下面两行代码不是原子操作
        // 假设x,y = (100,200)
        // 此处已读取到x=100，但x,y可能被写线程修改为(300,400)
        double currentX = x;
        System.out.println("第1次读,x:"+x+",y:"+y+",currentX:"+currentX);
//        log.debug("第1次读，x:{},y:{},currentX:{}",
//                x,y,currentX);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 此处已读取到y，如果没有写入，读取是正确的(100,200)
        // 如果有写入，读取是错误的(100,400)
        double currentY = y;
        System.out.println("第2次读,x:"+x+",y:"+y+",currentX:"+currentX+",currentY:"+currentY);
//        log.debug("第2次读，x:{},y:{},currentX:{},currentY:{}",
//                x,y,currentX,currentY);

        // 检查乐观读锁后是否有其他写锁发生
        if (!stampedLock.validate(stamp)) {
            // 获取一个悲观读锁
            stamp = stampedLock.readLock();
            try {
                currentX = x;
                currentY = y;

//                log.debug("最终结果，x:{},y:{},currentX:{},currentY:{}",
//                        x,y,currentX,currentY);
                System.out.println("最终结果,x:"+x+",y:"+y+",currentX:"+currentX+",currentY:"+currentY);
            } finally {
                // 释放悲观读锁
                stampedLock.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }

}
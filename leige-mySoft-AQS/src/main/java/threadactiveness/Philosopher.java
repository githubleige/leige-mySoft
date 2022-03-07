package threadactiveness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fox
 * 哲学家
 */

public class Philosopher extends Thread {
    public static final Logger log = LoggerFactory.getLogger(Philosopher.class);

    private Chopstick left;
    private Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    public void eat() {
        log.debug("eating...");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void think() {
        log.debug("thinking...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            // 获得左手筷子
            synchronized (left) {
                log.debug("获得左手筷子" + left.getNumber());
                // 获得右手筷子
                synchronized (right) {
                    log.debug("获得右手筷子" + right.getNumber());
                    // 吃饭
                    eat();
                }
                // 放下右手筷子
            }
            // 放下左手筷子
            log.debug("吃完了，把筷子放回了原处，开始thinking");
            think();
        }
    }

    // 思考： 如何用ReentrentLock+Condition实现


}

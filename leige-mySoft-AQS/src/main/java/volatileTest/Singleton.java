package volatileTest;

/**
 * 实际上当程序执行到2处的时候，如果我们没有使用volatile关键字修饰变量singleton，就可能会造成错误。
 * 这是因为使用new关键字初始化一个对象的过程并不是一个原子的操作，它分成下面三个步骤进行：
 * a. 给 singleton 分配内存
 * b. 调用 Singleton 的构造函数来初始化成员变量
 * c. 将 singleton 对象指向分配的内存空间（执行完这步 singleton 就为非 null 了）
 *
 *如果虚拟机存在指令重排序优化，则步骤b和c的顺序是无法确定的。如果A线程率先进入同步代码块并先执行了c而没有执行b，
 * 此时因为singleton已经非null。这时候线程B到了1处，判断singleton非null并将其返回使用(调用getA()方法为0，即初始值)，
 * 因为此时Singleton实际上还未初始化，自然就会出错。synchronized可以解决内存可见性，但是不能解决重排序问题。
 *
 *
 */
public class Singleton {
    //这里会用volatile修饰，这个很关键
    private volatile static Singleton singleton;

    //在init方法（构造方法）中进行执行初始化
    private int a=3;

    public int getA() {
        return a;
    }

    private Singleton() {}

    public static Singleton getInstance() {
        if (singleton == null) { // 1
            synchronized(Singleton.class) {
                if (singleton == null) {
                    //下面这段代码从jvm指令级别分为以下三步：
                    //a. 给 singleton 分配内存
                    //b. 调用 Singleton 的构造函数来初始化成员变量(在这个类中是指：private int a=3;)
                    //c. 将 singleton 对象指向分配的内存空间（执行完这步 singleton 就为非 null 了）
                    singleton = new Singleton(); // 2
                }
            }
        }
        return singleton;
    }
}

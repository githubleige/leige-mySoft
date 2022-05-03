package factoryMethod;

/**
 * 框架设计的话主要用就是这种  工厂方法（Factory Method）
 * 把框架执行的主流程放在父类中，把主流程中涉及到的具体实现放在子类中实现。
 * 例如我们的ap.process(),回去调用父类的public <E> void process(final E... e)主流程
 * 主流程中会去调用父类中的抽象方法abstract process(IStatus status);这样代码整洁又好维护
 */
public abstract class Factory {
    abstract public Product factoryMethod();
    public void doSomething() {
        Product product = factoryMethod();
        // do something with the product
    }
}

interface Product{

}

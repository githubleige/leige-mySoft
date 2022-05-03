package rabbitmq.spring.consumer;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean {

    // 保存一句话，用来区分不同的对象。
    private String message;
    // 无参构造器。
    public MyFactoryBean() {
        // 意思是：当前对象是 MyFactoryBean 的对象。
        this.message = "object of myFactoryBeanSelf";
    }
    // 有参构造器。
    public MyFactoryBean(String message) {
        this.message = message;
    }
    // 获取 message。
    public String getMessage() {
        return this.message;
    }


    /**
     *  这个方法在执行时创建了新的 MyFactoryBean 类型的对象。
     *  这里继续沿用了 MyFactoryBean 类型，但是可以是别的类型
     *  比如：Person、Car、等等。
     */
    @Override
    public Object getObject() throws Exception {
        // 意思是：当前对象是 MyFactoryBean 的 getObject() 创建的。
        return new MyFactoryBean("object from getObject() of MyFactoryBean");
    }

    @Override
    public Class<?> getObjectType() {
        return MyFactoryBean.class;
    }
}
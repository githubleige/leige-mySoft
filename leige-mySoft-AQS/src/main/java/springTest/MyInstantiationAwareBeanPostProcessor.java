package springTest;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import java.beans.PropertyDescriptor;

/**
 * 自定义处理器
 * @author dengp
 *
 */
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DisposableBean {

    /**
     * BeanPostProcessor接口中的方法
     * 在Bean的自定义初始化方法之前执行
     * Bean对象已经存在了
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // TODO Auto-generated method stub
        System.out.println(">>postProcessBeforeInitialization");
        return bean;
    }

    /**
     * BeanPostProcessor接口中的方法
     * 在Bean的自定义初始化方法执行完成之后执行
     * Bean对象已经存在了
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("<<postProcessAfterInitialization");
        return bean;
    }

    /**1
     * InstantiationAwareBeanPostProcessor中自定义的方法
     * 在方法实例化之前执行  Bean对象还没有
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("--->postProcessBeforeInstantiation");
        return null;
    }

    /**2
     * InstantiationAwareBeanPostProcessor中自定义的方法
     * 在方法实例化之后执行  Bean对象已经创建出来了
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("<---postProcessAfterInstantiation");
        return true;
    }

    /**3
     * InstantiationAwareBeanPostProcessor中自定义的方法
     * 可以用来修改Bean中属性的内容
     * 这里面是可以对xml文件中name值进行修改的
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean,
                                                    String beanName) throws BeansException {
        for(PropertyValue propertyValue : pvs.getPropertyValues()){
            System.out.println(propertyValue.getName());
        }
//        System.out.println(pvs.getPropertyValues());
        PropertyValue value = pvs.getPropertyValue("name");
        System.out.println("修改前name的值是:"+value.getValue());
        System.out.println("修改前name的值是:"+value.getValue().getClass());
        value.setConvertedValue("gelei");
        System.out.println("<---postProcessPropertyValues--->");
        return pvs;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("org.springframework.beans.factory.DisposableBean.destroy");
    }
}


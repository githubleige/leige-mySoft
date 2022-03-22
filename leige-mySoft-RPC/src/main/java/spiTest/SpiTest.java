package spiTest;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiTest {
    public static void main(String[] args) {

        ServiceLoader<Car> loadedDrivers = ServiceLoader.load(Car.class);
        Iterator<Car> driversIterator = loadedDrivers.iterator();
        System.out.println(Car.class.getName());
        try{
            //回去classPath环境下的META-INF/services/找Car的全限定名字
            while(driversIterator.hasNext()) {
                //创建实例
                System.out.println(driversIterator.next());
            }
        } catch(Throwable t) {
            // Do nothing
        }

//        ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
//        Protocol protocol = extensionLoader.getExtension("http");
//        System.out.println(protocol);

//        ExtensionLoader<Person> extensionLoader = ExtensionLoader.getExtensionLoader(Person.class);
//        Person person = extensionLoader.getExtension("black");
//
//        URL url = new URL("x", "localhost", 8080);
//        url = url.addParameter("car", "black");
//        System.out.println(person.getCar().getCarName(url));


//        ExtensionLoader<Filter> extensionLoader = ExtensionLoader.getExtensionLoader(Filter.class);
//        URL url = new URL("http://", "localhost", 8080);
//        url = url.addParameter("cache", "test");
//        List<Filter> activateExtensions = extensionLoader.getActivateExtension(url, new String[]{"validation"}, CommonConstants.CONSUMER);
//        for (Filter activateExtension : activateExtensions) {
//            System.out.println(activateExtension);
//        }

    }
}

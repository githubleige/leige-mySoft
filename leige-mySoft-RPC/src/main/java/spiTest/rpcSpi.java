package spiTest;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.URL;
import java.util.Iterator;
import java.util.ServiceLoader;
import org.apache.dubbo.rpc.Protocol;

public class rpcSpi {
    public static void main(String[] args) {


//        ExtensionLoader<Protocol> extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
//        Protocol protocol = extensionLoader.getExtension("http");
//        System.out.println(protocol);
        //dubbo自己实现的类加载。类似ServiceLoader.load
        ExtensionLoader<Car> extensionLoader = ExtensionLoader.getExtensionLoader(Car.class);
        Car person = extensionLoader.getExtension("red");
        System.out.println(person);
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

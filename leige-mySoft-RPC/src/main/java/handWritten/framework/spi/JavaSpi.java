package handWritten.framework.spi;

import handWritten.framework.Protocol;
import handWritten.framework.URL;

import java.util.Iterator;
import java.util.ServiceLoader;

public class JavaSpi {
    public static void main(String[] args) {
        ServiceLoader<Protocol> serviceLoader = ServiceLoader.load(Protocol.class);
        Iterator<Protocol> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Protocol protocol = iterator.next();
            protocol.start(new URL("localhost", 8080));
        }
    }
}

package BlockingQueue;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {

    public static final Logger logger = LoggerFactory.getLogger(TestLog .class);

    public static void main(String[] args) {
        String message = "Hello gelei";
        String andMessage="hello huangrui";
        logger.info("The message is : {},{}", message,andMessage);

        logger.info("slf4j for info");
        logger.debug("slf4j for debug");
        logger.error("slf4j for error");
        logger.warn("slf4j for warn");

    }
}


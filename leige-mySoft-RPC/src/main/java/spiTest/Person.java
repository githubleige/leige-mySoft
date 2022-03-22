package spiTest;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Person {

    Car getCar();
}

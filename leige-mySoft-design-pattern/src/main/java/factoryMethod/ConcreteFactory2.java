package factoryMethod;

public class ConcreteFactory2 extends Factory {
    public Product factoryMethod() {
        return new ConcreteProduct2();
    }
}

class ConcreteProduct2 implements Product{

}



package factoryMethod;

public class ConcreteFactory1 extends Factory {
    public Product factoryMethod() {
        return new ConcreteProduct1();
    }
}

class ConcreteProduct1 implements Product{

}

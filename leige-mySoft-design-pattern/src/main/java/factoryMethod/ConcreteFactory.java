package factoryMethod;

public class ConcreteFactory extends Factory {
    public Product factoryMethod() {
        return new ConcreteProduct();
    }
}

class ConcreteProduct implements Product{

}

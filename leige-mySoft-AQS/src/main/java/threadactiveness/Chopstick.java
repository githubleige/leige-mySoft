package threadactiveness;

/**
 * @author Fox
 * 筷子
 */

public class Chopstick {
    int number;

    public Chopstick(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "筷子{" + number + '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}


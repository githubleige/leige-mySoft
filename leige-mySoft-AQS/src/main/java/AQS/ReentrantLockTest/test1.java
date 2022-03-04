package AQS.ReentrantLockTest;


import java.io.Serializable;

public class test1 {
    private transient volatile Node tail;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("hello world");
        Node node=new Node(0);
        test1 aa=new test1();
        aa.print();
    }

    private void print() throws InterruptedException {
        wait();
        System.out.println("hello world");
    }
}

class Node implements Serializable {
    private volatile int state;

    public Node(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

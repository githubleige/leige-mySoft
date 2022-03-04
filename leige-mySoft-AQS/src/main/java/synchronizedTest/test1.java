package synchronizedTest;

public class test1 {
    Object o=new Object();
    public synchronized void print(){
        int i=0;
        int y=i++;
    }

    public  void print1(){
        synchronized (o){
            int i=0;
            int y=i++;
        }

    }

    public static void main(String[] args) {

    }
}

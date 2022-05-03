package it.heima.nio.selector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class test2 {
    private static List<Integer> LIST = new ArrayList<Integer>(){
        {
            add(1);
            add(2);
            add(3);
        }
    };
    public static void main(String[] args) {

        new Thread(() -> {
            Iterator<Integer> iterator = LIST.iterator();

            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LIST.add(4);
        }).start();
    }

}

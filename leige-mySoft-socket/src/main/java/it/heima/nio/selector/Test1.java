package it.heima.nio.selector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {

        List<String> list=new ArrayList<String>(){
            {
                add("1");
                add("2");
                add("3");
            }
        };
        Iterator<String> iterator=list.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
            //注意迭代器的remove操作是直接会对原集合起作用的，例如下面的答应就是0（迭代器并没有开辟新的内存，而是继续使用原来的内存）
//            iterator.remove();
            //下面的代码会出现：Exception in thread "main" java.util.ConcurrentModificationException
            list.add("5");
        }
        System.out.println(list.size());
    }
}

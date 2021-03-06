package MapTest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapTest {
    public static void main(String[] args) {
       /* Map<String,String> map=new HashMap<>();
        //ConcurrentHashMap的线程安全是通过CAS和synchronized实现的
        //
        ConcurrentHashMap<Person, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
//        Hashtable hashtable = new Hashtable();
//        hashtable.put()
        objectObjectConcurrentHashMap.put(new Person("gelei",25),"1");
        objectObjectConcurrentHashMap.put(new Person("gelei",25),"2");
        System.out.println(16>>>2);*/

        List<Person> list=new ArrayList<>();
        list.add(new Person("gelei",26));
//        list.add(new Person("geji",27));

        Iterator<Person> iterator= list.iterator();
        Person person;
        while (iterator.hasNext()){
            person=iterator.next();
//            person.setAge(25);
            list.remove(person);
            System.out.println(person);
        }
    }

     static class Person{
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

         @Override
         public String toString() {
             return "名字："+name+",年龄:"+age;
         }

//         @Override
//        public boolean equals(Object o) {
//            return false;
//        }


         @Override
         public boolean equals(Object obj) {
            if(obj instanceof Person){
                obj=(Person)obj;
                return name.equals(((Person) obj).getName())&&(age==((Person) obj).getAge());
            }
             return false;
         }

         //返回值全都是一样的，在插入hashMap的时候，就会差生hash冲突，但是下面重写的equals方法，总是返回false
        //最终导致在插入hashMap的时候全部都插入一个坑位，在<8的时候形成链表，在>=8的时候呀形成红黑树
        @Override
        public int hashCode() {
            return 1;
        }

         public Person(String name, int age) {
             this.name = name;
             this.age = age;
         }
     }
}

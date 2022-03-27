package cglib;

public class test4 {

    public static void main(String[] args) {
        subClass[] aa=new subClass[]{};
        System.out.println(aa.getClass());
        System.out.println(aa.getClass().getSuperclass());
    }
}

class superClass{

}

class subClass extends superClass{

}

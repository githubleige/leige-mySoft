package it.heima.socketProgram;

public class test1 {
    public static void main(String[] args) {
        //1100 0000
        byte a=(byte)192;
        //-64（byte类型）的补码是：1100 0000
        System.out.println(a);
        //a & 0xff运算后会自动升级为int类型
//        byte b=a & 0xff;
        System.out.println(a & 0xff);
        //输出的值是：-4194304,对应的补码是：
        /*11111111
          11000000
          00000000
          00000000*/
        //左移16位，总共3乘8是24，但是没有类型是24位，所以向上转型32位。左边全部补符号位1。所以变成上面样子
        System.out.println(a << 16);
        //16位：-16384，对应的补码是：
        // 11000000
        // 00000000
        //左移8位得到16位，是short类型
        System.out.println(a << 8);

        byte c=(byte)( a & 0xff);
        System.out.println(c);

        int aa=448;
        System.out.println(aa&0xff);
    }
}

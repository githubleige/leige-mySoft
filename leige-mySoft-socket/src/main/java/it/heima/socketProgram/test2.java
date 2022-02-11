package it.heima.socketProgram;

public class test2 {
    public static void main(String[] args) {
        byte[] addrs=new byte[]{(byte)192,(byte)168,0,105};
        int addr=addrs[3]&0xff;
        addr|=(addrs[2]<<8)&0xff00;
        addr|=(addrs[1]<<16)&0xff0000;
        addr|=(addrs[0]<<24)&0xff000000;

        System.out.println(addr>>>24&0xff);
        System.out.println(addr>>>16&0xff);
        System.out.println(addr>>>8&0xff);
        System.out.println(addr&0xff);
    }
}

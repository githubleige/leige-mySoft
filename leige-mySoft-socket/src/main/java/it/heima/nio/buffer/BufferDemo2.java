package it.heima.nio.buffer;


import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo2 {

    static private final int start = 0;
    static private final int size = 1024;

    //内存映射文件io
     @Test
     public void b04() throws Exception {
         RandomAccessFile raf = new RandomAccessFile("d:\\atguigu\\01.txt", "rw");
         FileChannel fc = raf.getChannel();
         MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);

         mbb.put(0, (byte) 97);
         mbb.put(1023, (byte) 122);
         raf.close();
     }
    //直接缓冲区（其实我们进行i/o操作的时候），可能先从应用进程的内存中拷贝到系统惊醒i/o的缓冲区（也是在内存中），然后再统一从系统缓存刷入到i/o设备中去，
    // 但是直接i/o提高效率就是直接从i/o设备拷贝到jvm内存中去（堆）。不通过系统的缓存，减少中间拷贝的代价，
    // 但是付出的代价就是系统需要给该进程进行i/o足够高的优先级
    @Test
    public void b03() throws Exception {
        String infile = "d:\\atguigu\\01.txt";
        FileInputStream fin = new FileInputStream(infile);
        FileChannel finChannel = fin.getChannel();

        String outfile = "d:\\atguigu\\02.txt";
        FileOutputStream fout = new FileOutputStream(outfile);
        FileChannel foutChannel = fout.getChannel();

        //创建直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            buffer.clear();
            int r = finChannel.read(buffer);
            if(r == -1) {
                break;
            }
            buffer.flip();
            foutChannel.write(buffer);
        }
    }

    //只读缓冲区
    @Test
    public void b02() {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        //创建只读缓冲区
        ByteBuffer readonly = buffer.asReadOnlyBuffer();

        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b *=10;
            buffer.put(i,b);
        }

        readonly.position(0);
        readonly.limit(buffer.capacity());

        while (readonly.remaining()>0) {
            System.out.println(readonly.get());
        }
    }


    //缓冲区分片
    @Test
    public void b01() {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        //buffer.capacity()返回的是10
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        //创建子缓冲区
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();

        //改变子缓冲区内容
        for (int i = 0; i <slice.capacity() ; i++) {
            byte b = slice.get(i);
            b *=10;
            slice.put(i,b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while(buffer.remaining()>0) {
            System.out.println(buffer.get());
        }
    }
}

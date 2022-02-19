package it.heima.nio;


import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class RandomFileChannelTest
{
    private static final String Path="D:\\FTPShare\\15\\15.9\\a.txt";
    public static void main(String[] args)
            throws IOException
    {
        File f = new File(Path);
        try(
                // 创建一个RandomAccessFile对象
                RandomAccessFile raf = new RandomAccessFile(f, "rw");
                // 获取RandomAccessFile对应的Channel
                FileChannel randomChannel = raf.getChannel())
        {
            // 将Channel中所有数据映射成ByteBuffer(将磁盘中的文件映射到内存中去了)
            ByteBuffer buffer = randomChannel.map(FileChannel
                    .MapMode.READ_ONLY, 0 , f.length());
            // 把Channel的记录指针移动到最后（注意这里移动的是channel的记录指针，不是buffer的记录指针）
            randomChannel.position(f.length());
            // 将buffer中所有数据输出
            randomChannel.write(buffer);
        }
    }
}


package it.heima.nio;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class FileChannelTest
{
    private static final String outPath="D:\\FTPShare\\15\\15.9\\a.txt";
    private static final String inputPath="D:\\FTPShare\\15\\15.9\\FileChannelTest.java";
    public static void main(String[] args)
    {
        File f = new File(inputPath);
        try(
                // 创建FileInputStream，以该文件输入流创建FileChannel，FileInputStream创建的FileChannel只能读
                FileChannel inChannel = new FileInputStream(f).getChannel();
                // 以文件输出流创建FileBuffer，用以控制输出。FileOutputStream创建的FileChannel只能写
                FileChannel outChannel = new FileOutputStream(outPath)
                        .getChannel())
        {
            // 将FileChannel里的全部数据映射成ByteBuffer
            MappedByteBuffer buffer = inChannel.map(FileChannel
                    .MapMode.READ_ONLY , 0 , f.length());   // ①
            // 使用GBK的字符集来创建解码器
            Charset charset = Charset.forName("GBK");
            // 直接将buffer里的数据全部输出
            outChannel.write(buffer);     // ②
            // 再次调用buffer的clear()方法，复原limit、position的位置(limit复原到capacity位置，position复原到0)
//            buffer.clear();
            MappedByteBuffer buffer1=(MappedByteBuffer)buffer.position(10);
            System.out.println(buffer1.position());
            System.out.println(buffer.position());
            // 创建解码器(CharsetDecoder)对象
            CharsetDecoder decoder = charset.newDecoder();
            // 使用解码器将ByteBuffer转换成CharBuffer（这个解码器只会解码buffer中的position 和 limit之间的额位置）
            CharBuffer charBuffer =  decoder.decode(buffer);
            // CharBuffer的toString方法可以获取对应的字符串
            System.out.println(charBuffer);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}


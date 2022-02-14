package it.heima.socketProgram;

import java.io.*;

public class BlockIO {
    private static final String path="D:\\FTPShare\\Client.java";
    private static final String str1="hello\n";
    private static final String str2="world\n";
    public static void main(String[] args) throws FileNotFoundException {
        try (

//                FileOutputStream outputStream=new FileOutputStream(path);

                FileInputStream inputStream=new FileInputStream(path);
                BufferedReader br=new BufferedReader(new InputStreamReader(inputStream))
                ){
                    File file=new File(path);
                    long length=file.length();
                    new Thread(()->{

                        try {
                            RandomAccessFile raf=new RandomAccessFile(path,"rw");
                            raf.seek(length);
                            raf.write(str1.getBytes());
                            Thread.sleep(10000);
                            raf.write(str2.getBytes());
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    String line=null;
                    while ((line= br.readLine())!=null){
                        System.out.println(line);
                    }
                    System.out.println("主进程读写完毕");
                }catch (Exception e){
                    e.printStackTrace();
                }
    }
}

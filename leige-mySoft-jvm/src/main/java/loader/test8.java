package loader;

import java.io.*;

public class test8 extends ClassLoader{
    private String classLoaderName;
    private final String fileExtension=".class";
    public test8(String name) {
        super();//指定应用加载器（APPClassLoader）为自定义加载器（test6）的父加载器
        this.classLoaderName=name;
    }

    public test8(ClassLoader classloadername) {
        super(classloadername);
    }

    private test8(String name,ClassLoader parentLoader) {
        super(parentLoader);//指定加载器（parentLoader）为自定义加载器（test6）的父加载器
        this.classLoaderName=name;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "["+classLoaderName+"]";
    }



    public Class findClass(String name) {
        byte[] b = loadClassData(name);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassData(String name) {
        InputStream in=null;
        byte[] data=null;
        ByteArrayOutputStream baos=null;

        try{String rootDir=new String("C:\\Users\\w\\Desktop\\东软任务\\")+name.replace('.', '\\')+fileExtension;
            in=new FileInputStream(new File(rootDir));
            baos=new ByteArrayOutputStream();
            int ch=0;
            while(-1!=(ch=in.read())) {
                baos.write(ch);
            }
            data=baos.toByteArray();
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
                baos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return data;
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        test8 cl=new test8("loader1");
        Class<?> clazz=cl.loadClass("classloader.mysample");
//		clazz.newInstance();
        System.out.println(clazz.getClassLoader());
    }
}

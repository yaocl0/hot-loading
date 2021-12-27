package com.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 功能描述:<br/>
 * 开发人员:Mr_Yao<br/>
 * 创建时间:2021/12/27 下午2:11<br/>
 * 修改时间:<br/>
 * 修改内容:<br/>
 */
public class MyClassLoader extends ClassLoader {


    private static final String SUFFIX = ".class";

    private String rootPath;

    public MyClassLoader(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * 破坏双亲委派机制,自定义类加载方式
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (null == loadedClass) {
            try {
                return findClass(name);
            } catch (ClassNotFoundException e) {
                return super.loadClass(name);
            }
        }

        return loadedClass;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = rootPath + name.replace(".", "/") + SUFFIX;
        File file = new File(path);
        byte[] classBytes = null;
        try {
            classBytes = getClassBytes(file);
        } catch (Exception e) {
        }
        if (null != classBytes) {
            if (null != super.findLoadedClass(name)) {
                return super.findLoadedClass(name);
            }
            Class<?> aClass = defineClass(name, classBytes, 0, classBytes.length);
            if (null != aClass) {
                return aClass;
            }
        }
        return super.findClass(name);
    }

    /**
     * 加载类
     * @param file
     * @return
     * @throws Exception
     */
    private byte[] getClassBytes(File file) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fc = fileInputStream.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel writableByteChannel = Channels.newChannel(baos);
        ByteBuffer by = ByteBuffer.allocate(1024);
        while (true) {
            int read = fc.read(by);
            if (read == 0 || read == -1) {
                break;
            }
            by.flip();
            writableByteChannel.write(by);
            by.clear();
        }
        fileInputStream.close();
        return baos.toByteArray();
    }
}

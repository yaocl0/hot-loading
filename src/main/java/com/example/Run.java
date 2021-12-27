package com.example;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

/**
 * 功能描述:<br/>
 * 开发人员:Mr_Yao<br/>
 * 创建时间:2021/12/27 下午2:15<br/>
 * 修改时间:<br/>
 * 修改内容:<br/>
 */
public class Run {

    public static String rootPath;

    public static void run(Class cl) {
        rootPath = cl.getClass().getResource("/").getPath();
        System.out.println(rootPath);
        MyClassLoader myClassLoader = new MyClassLoader(rootPath);
        startFileListener(rootPath);
        start0(myClassLoader);
    }

    public static void startFileListener(String rootPath) {
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(rootPath);
        fileAlterationObserver.addListener(new FileListener());
        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(5);
        fileAlterationMonitor.addObserver(fileAlterationObserver);
        try {
            fileAlterationMonitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 启动应用程序
     *
     * @param classLoader
     */
    public static void start0(MyClassLoader classLoader) {
        Class<?> clazz = null;
        try {
            clazz = classLoader.findClass("com.example.Run");
            clazz.getMethod("start").invoke(clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟启动应用程序
     */
    public static void start() {
        Application application = new Application();
        application.printApplicationName();
    }
}

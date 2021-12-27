package com.example;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

/**
 * 功能描述:<br/>
 * 开发人员:Mr_Yao<br/>
 * 创建时间:2021/12/27 下午2:14<br/>
 * 修改时间:<br/>
 * 修改内容:<br/>
 */
public class FileListener extends FileAlterationListenerAdaptor {

    @Override
    public void onFileCreate(File file) {
        System.out.println(file.getName());
        if (file.getName().indexOf(".class") != -1) {

            try {
                MyClassLoader myClassLoader = new MyClassLoader(Run.rootPath);
                Run.start0(myClassLoader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onFileCreate(file);
    }


    @Override
    public void onFileChange(File file) {
        System.out.println(file.getName());
        if (file.getName().indexOf(".class") != -1) {
            try {
                MyClassLoader myClassLoader = new MyClassLoader(Run.rootPath);
                Run.start0(myClassLoader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package io.jing.server.classloader;

import io.jing.server.sms.bean.UserBean;
import io.jing.server.sms.constant.SmsConstant;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.sql.DriverManager;

public class FileUrlClassLoader extends URLClassLoader {

    public FileUrlClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public FileUrlClassLoader(URL[] urls) {
        super(urls);
    }

    public FileUrlClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }


    public static void main(String[] args) throws Exception {
//        String rootDir = "/home/jing/workspace/micro-service/user/target/classes";
//        File file = new File(rootDir);
//        URI uri = file.toURI();
//        URL[] urls = {uri.toURL()};
//        FileUrlClassLoader loader = new FileUrlClassLoader(urls);
//
//        Class<?> c1 = loader.loadClass("io.jing.server.user.bean.UserBean");
//        System.out.println(c1.hashCode());
//        System.out.println(UserBean.class.hashCode());
//        Class<?> c2 = loader.findClass("io.jing.server.user.bean.UserBean");
//        System.out.println(c1.hashCode());
//        System.out.println(c2.hashCode());
//        System.out.println(1);

        DriverManager.getConnection(SmsConstant.DS_URL);
        System.out.println(1);

    }
}

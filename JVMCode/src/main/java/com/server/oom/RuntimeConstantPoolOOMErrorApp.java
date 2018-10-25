package com.server.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CYX
 */
public class RuntimeConstantPoolOOMErrorApp {

    /**
     * 运行时常量池溢出
     * <p/>
     * VM options : -XX:PermSize=10M -XX:MaxPermSize=10M
     * <p/>
     * -XX:PermSize：JVM初始分配的非堆内存
     * <p/>
     * -XX:MaxPermSize：JVM最大允许分配的非堆内存
     * <p/>
     * 注：
     * 在https://blog.csdn.net/simba_cheng/article/details/82871778中，提到过，方法区的别名是非堆。
     * <p/>
     * while(true)的方式将，String字符串塞入运行时常量池(字符串常量池)中，同时使用list保持对String字符串的引用，避免回收
     * <p/>
     * 使用JDK1.6(包含)之前版本，会抛出异常：
     * Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
     * <p/>
     * 使用JDK1.6(不包含)之后版本，不会抛出异常
     *
     * @param args
     */
    public static void main(String[] args) {

        //使用list保持常量池引用，避免Full GC回收常量池行为
        List<String> list = new ArrayList<String>();

        //10M的permSize在integer范围内足够产生OOM了.
        int i = 0;

        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }

}

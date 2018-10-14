package com.server.other;

/**
 * @author CYX
 * @create 2018-06-27-22:31
 */
public class JavacTestOverload {

    public String method1(String str) {
        String mtdName = Thread.currentThread().getStackTrace()[1].getMethodName();//获取当前方法名称，具体使用数组的那个元素和JVM的实现有关，具体说明可以查看Thread.getStackTrace方法的javadoc
        System.out.println("invoke " + mtdName + " return String");
        return "";
    }

    public int method2(String str) {
        String mtdName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("invoke " + mtdName + " return int");
        return 1;
    }

    public static void main(String[] args) {
        JavacTestOverload javacTestOverload = new JavacTestOverload();
        String str = javacTestOverload.method1("Test");
        int i = javacTestOverload.method2("Test");
    }


}

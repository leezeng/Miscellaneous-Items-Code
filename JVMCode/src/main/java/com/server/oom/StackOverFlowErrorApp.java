package com.server.oom;

/**
 * @author CYX
 */
public class StackOverFlowErrorApp {

    private int stackLenght = 1;

    public void stackLeak() {
        stackLenght++;
        stackLeak();
    }

    /**
     * 测试-Java虚拟机栈溢出
     * <p>
     * VM options : -Xss128k
     * <p>
     * -Xss:设置每个线程的Java虚拟机栈的大小
     * <p>
     * 注意：JDK5.0之前，每个线程的Java虚拟机栈大小为128K；JDK5.0之后，大小调整为1M。
     * <p>
     * while(true)递归调用stackLeak()方法，每调用一次方法，在Java虚拟机栈中创建一个栈帧。
     * 由于递归调用，栈帧将Java虚拟机栈撑爆，造成溢出
     *
     * @param args
     * @throws Throwable
     */
    public static void main(String[] args) throws Throwable {
        StackOverFlowErrorApp stackLenght = new StackOverFlowErrorApp();

        try {
            stackLenght.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack lenght : " + stackLenght.stackLenght);
            throw e;
        }
    }

}

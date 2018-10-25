package com.server.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CYX
 */
public class HeapOOMErrorApp {

    public static void main(String[] args) {

        //测试-堆溢出
        testHeapOOM();

    }
    
    /**
     * 测试-堆溢出
     * <p>
     * VM Args : -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
     * <p>
     * 首先限定堆内存的大小(最大和最小都限制为20m)
     * 在堆中创建一个List，存储HeapOOMObject。while(true)一直向其中塞入对象，直到其内存溢出
     */
    public static void testHeapOOM() {

        List<HeapOOMObject> heapOOMObjects = new ArrayList<HeapOOMObject>();

        while (true) {
            heapOOMObjects.add(new HeapOOMObject());
        }

    }

    static class HeapOOMObject {
        private static final String param = "";
    }


}

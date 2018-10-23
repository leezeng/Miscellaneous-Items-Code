package com.server;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CYX
 */
public class OutOfMemoryErrorApp {

    public static void main(String[] args) {

        //测试-堆溢出
        testHeapOOM();

    }

    /**
     * 测试-堆溢出
     *
     * VM Args : -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
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

package com.server;

/**
 * 引用计数器算法
 *
 * @author CYX
 */
public class ReferenceCounterAlgorithm {

    Object field = null;

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                ReferenceCounterAlgorithm objectA = new ReferenceCounterAlgorithm();
                ReferenceCounterAlgorithm objectB = new ReferenceCounterAlgorithm();//1

                objectA.field = objectB;
                objectB.field = objectA;    //2

                //do something...
                objectA = null;
                objectB = null;     //3

            }
        });

        thread.start();
        while (true) ;

    }

}

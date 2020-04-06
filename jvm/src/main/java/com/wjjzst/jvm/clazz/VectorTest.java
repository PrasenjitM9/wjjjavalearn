package com.wjjzst.jvm.clazz;

import java.util.Vector;

/**
 * @Author: Wjj
 * @Date: 2020/4/2 12:27 上午
 * @desc:
 */
public class VectorTest {
    private static Vector<Integer> vector = new Vector();

    public static void main(String[] args) {
        while (true) {
            for (int i = 0; i < 10; i++) {
                vector.add(i);
            }
            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        vector.remove(i);
                    }
                }
            });
            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++) {
                        System.out.println(vector.get(i));
                    }
                }
            });
            removeThread.start();
            printThread.start();
            while (Thread.activeCount() > 20) ;
        }
    }
}

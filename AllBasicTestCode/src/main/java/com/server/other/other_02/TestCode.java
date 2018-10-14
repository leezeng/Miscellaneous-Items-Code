package com.server.other.other_02;

/**
 * @author CYX
 * @create 2018-07-08-19:22
 */
public class TestCode {

    public static void main(String[] args) {
        int result = TestCode.getValue(2);
        System.out.println(result);
    }

    public static int getValue(int i) {
        int result = 0;
        switch (i) {
            case 1:
                result = result + i;
            case 2:
                result = result + i * 2;
            case 3:
                result = result + i * 3;
        }
        return result;
    }

}

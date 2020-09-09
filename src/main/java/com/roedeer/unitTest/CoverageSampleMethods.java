package com.roedeer.unitTest;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 5/23/2019 5:25 PM
 **/
public class CoverageSampleMethods {

    public Boolean testMethod(int a, int b, int c) {
        boolean result = false;
        if (a == 1 && b == 2 || c == 3) {
            result = true;
        }
        return result;
    }

}

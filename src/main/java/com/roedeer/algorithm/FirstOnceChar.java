package com.roedeer.algorithm;

import java.util.LinkedHashMap;

/**
 * @Description 第一个只出现一次的字符
 * @Author Roedeer
 * @Date 9/26/2019 6:51 AM
 **/
public class FirstOnceChar {

    public static void main(String[] args) {
        FirstOnceChar firstOnceChar = new FirstOnceChar();
        System.out.println(firstOnceChar.firstNotRepeatChar("abaccdeff"));
    }

    public Character firstNotRepeatChar(String str) {
        if (str == null)
            return null;
        char[] strChar = str.toCharArray();
        LinkedHashMap<Character, Integer> hash = new LinkedHashMap<Character, Integer>();
        for (char item : strChar) {
            if (hash.containsKey(item))
                hash.put(item, hash.get(item) + 1);
            else
                hash.put(item, 1);
        }
        for (char key : hash.keySet()) {
            if (hash.get(key) == 1)
                return key;
        }
        return null;
    }

}

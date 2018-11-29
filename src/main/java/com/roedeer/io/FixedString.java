package com.roedeer.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by U6071369 on 8/10/2018.
 */
public class FixedString {

    /**
     * 写出从字符串开头开始的size的码元,过少则0补
     * @param s
     * @param size
     * @param out
     * @throws IOException
     */
    public static void writeFixedString (String s, int size, DataOutput out) throws IOException {
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if (i < s.length())
                ch = s.charAt(i);
            out.writeChar(ch);
        }
    }

    /**
     * 从输入流中读入字符,直到读入size个码元，或者直到遇到具有0值得字符值，然后跳过输入字段中剩余的0值
     * @param size
     * @param in
     * @return
     * @throws IOException
     */
    public static String readFixedString (int size, DataInput in) throws IOException {
        StringBuilder sb = new StringBuilder(size);
        int i = 0;
        boolean more = true;
        while (more && i < size) {
            char ch = in.readChar();
            i++;
            if (ch == 0) more = false;
            else sb.append(ch);
        }
        in.skipBytes(2 * (size - i));
        return sb.toString();
    }

    private static boolean isNumeric(String permid) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(permid).matches();
    }


    public static void main(String[] args) {
//        System.out.println( ! isNumeric("215"));
        long a = 1;
        if (a != 2) {
            System.out.println("a");
        } else {
            System.out.println("b");
        }
    }
}

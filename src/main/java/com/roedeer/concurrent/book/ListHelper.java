package com.roedeer.concurrent.book;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description 若没有则添加
 * @Author Roedeer
 * @Date 1/24/2019 3:14 PM
 **/
public class ListHelper<E> {
    public List<E> list = Collections.synchronizedList(new ArrayList<E>());

    public boolean putIfAbsent(E x) {
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent)
                list.add(x);
            return absent;
        }
    }
}

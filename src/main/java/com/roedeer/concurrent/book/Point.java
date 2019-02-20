package com.roedeer.concurrent.book;


import javax.annotation.concurrent.Immutable;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 1/24/2019 2:13 PM
 **/
@Immutable
public class Point {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

package com.roedeer.concurrent.book;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 1/24/2019 1:51 PM
 **/
@NotThreadSafe
public class MutablePoint {
    public int x, y;

    public MutablePoint() {
        x = 0;y = 0;
    }

    public MutablePoint(MutablePoint point) {
        this.x = point.x;
        this.y = point.y;
    }
}

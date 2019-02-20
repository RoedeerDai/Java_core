package com.roedeer.concurrent.book;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 基于监视器模式的车辆追踪
 *              虽然MutablePoint不是线程安全的,但是追踪器类是线程安全的
 *              包含的map和可变的point对象都未曾发布
 *              这种实现方式是通过在返回客户代码之前复制可变的数据来维持线程安全的,在车辆容器非常大的情况下将极大地降低性能
 *              由于每次调用getLocation就要复制数据,因此将出现一种错误情况：
 *              虽然车辆的实际位置发生了变化,但返回的信息却保持不变,这种情况的好坏,取决于需求
 *              满足一致性,但是不满足实时更新
 * @Author Roedeer
 * @Date 1/24/2019 1:53 PM
 **/
@ThreadSafe
public class MonitorVehicleTracker {

    @GuardedBy("this")
    private final Map<String, MutablePoint> locations;

    public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
        this.locations = deepCopy(locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy(locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint loc = locations.get(id);
        return loc == null ? null : new MutablePoint(loc);
    }

    public synchronized void setLocations(String id, int x, int y) {
        MutablePoint loc = locations.get(id);
        if (loc == null)
            throw new IllegalArgumentException("No such ID : " + id);
        loc.x = x;
        loc.y = y;
    }

    private static Map<String,MutablePoint> deepCopy(Map<String, MutablePoint> locations) {
        Map<String, MutablePoint> result = new HashMap<String, MutablePoint>();
        for (String id : locations.keySet()) {
            result.put(id, new MutablePoint(locations.get(id)));
        }
        return Collections.unmodifiableMap(result);
    }
}

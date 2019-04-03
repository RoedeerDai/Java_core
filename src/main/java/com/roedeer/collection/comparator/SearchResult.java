package com.roedeer.collection.comparator;

/**
 * @Description 自身实现相关度和浏览数的比较
 *              如果需要比较最近订单recentOrders的时候,又不能更高SearchResult的代码时,就需要一个Comparator类
 * @Author Roedeer
 * @Date 3/4/2019 5:46 PM
 **/
public class SearchResult implements Comparable<SearchResult> {
    int relativeRatio;
    long count;
    int recentOrders;

    public SearchResult(int relativeRatio, long count) {
        this.relativeRatio = relativeRatio;
        this.count = count;
    }

    @Override
    public int compareTo(SearchResult o) {
        // 先比较相关度
        if (this.relativeRatio != o.relativeRatio) {
            return this.relativeRatio > o.relativeRatio ? 1 : -1;
        }
        // 相关度相等时再比较浏览数
        if (this.count != o.count) {
            return this.count > o.count ? 1 : -1;
        }
        return 0;
    }

    public void setRecentOrders(int recentOrders) {
        this.recentOrders = recentOrders;
    }
}

package com.roedeer.collection.comparator;

import java.util.Comparator;
import java.util.Objects;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 3/4/2019 5:51 PM
 **/
public class SearchResultComparator implements Comparator<SearchResult> {
    @Override
    public int compare(SearchResult o1, SearchResult o2) {
        //相关度是第一排序准则,更高者排前面
        if (o1.relativeRatio != o2.relativeRatio) {
            return o1.relativeRatio > o2.relativeRatio ? 1 : -1;
        }

        // 相关度一样,则最近订单数多者排前
        if (o1.recentOrders != o2.recentOrders) {
            return o1.recentOrders > o2.recentOrders ? 1 : -1;
        }

        // 如果相关度和最近订单数都一样,则浏览数多者排前
        if (o1.count != o2.count) {
            return o1.count > o2.count ? 1 : -1;
        }
        return 0;
    }
}

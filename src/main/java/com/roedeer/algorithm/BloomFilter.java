package com.roedeer.algorithm;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 3/6/2019 1:41 PM
 **/
public class BloomFilter {

    /**
     * 布隆过滤器用于字符串去重复，比如网络爬虫抓取时URL去重、邮件提供商反垃圾黑名单Email地址去重
     *
     * 布隆过滤器只占哈希表的1/8或1/4的空间复杂度，就能解决同样的问题，但是有一定的误判，而且不能删除已有元素。元素越多，误报率越大，但是不会漏报
     * 对于还需要删除的布隆过滤器，还有Counter Bloom Filter，这个是布隆过滤器的变体，可以删除元素
     *
     *
     * 布隆过滤器需要的是一个位数组(和位图类似)和K个映射函数(和Hash表类似)，在初始状态时，对于长度为m的位数组array，它的所有位被置0
     * 对于有n个元素的集合S={S1,S2...Sn},通过k个映射函数{f1,f2,......fk}，将集合S中的每个元素Sj(1<=j<=n)映射为K个值{g1,g2...gk}
     * 然后再将位数组array中相对应的array[g1],array[g2]......array[gk]置为1
     *
     * 如果要查找某个元素item是否在S中，则通过映射函数{f1,f2,...fk}得到k个值{g1,g2...gk}，然后再判断array[g1],array[g2]...array[gk]是否都为1
     * 若全为1，则item在S中，否则item不在S中。这个就是布隆过滤器的实现原理
     *
     */

    private static final int DEFAULT_SIZE = 2 << 24;
    private static final int[] seeds = new int[] { 5, 7, 11, 13, 31, 37, 61 };
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    private SimpleHash[] func = new SimpleHash[seeds.length];

    public BloomFilter() {
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    public void add(String value) {
        for (SimpleHash f : func) {
            bits.set(f.hash(value), true);
        }
    }

    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (SimpleHash f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    // 内部类，simpleHash
    public static class SimpleHash {
        private int cap;
        private int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(String value) {
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                result = seed * result + value.charAt(i);
            }
            return (cap - 1) & result;
        }
    }

    public static void main(String[] args) {
        BloomFilter bf = new BloomFilter();
        List<String> strs = new ArrayList<String>();
        strs.add("123456");
        strs.add("hello word");
        strs.add("transDocId");
        strs.add("123456");
        strs.add("transDocId");
        strs.add("hello word");
        strs.add("test");
        for (int i=0;i<strs.size();i++) {
            String s = strs.get(i);
            boolean bl = bf.contains(s);
            if(bl){
                System.out.println(i+","+s);
            }else{
                bf.add(s);
            }
        }
    }


}

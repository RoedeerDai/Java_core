package com.roedeer.practice.enumeration;

import org.junit.Test;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 4/10/2019 3:20 PM
 **/
public class EnumTest {

    @Test
    public void test() {
        System.out.println(FeedStatus.FileStatus.FEED_FILE_STATUS_PROCESSED.getStatus());
    }

}

package com.roedeer.concurrent.demo.boundBuffer;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.concurrent.ThreadSafe;

import java.util.concurrent.Executors;

import static org.junit.Assert.fail;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 2/24/2019 9:08 AM
 **/
public class TestBoundBuffer {

    @Test
    public void testIsEmptyWhenConstructed() {
        BoundBuffer<Integer> bb = new BoundBuffer<Integer>(10);
        Assert.assertTrue(bb.isEmpty());
        Assert.assertFalse(bb.isFull());
    }

    @Test
    public void testIsFullAfterPuts() throws InterruptedException {
        BoundBuffer<Integer> bb = new BoundBuffer<Integer>(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }
        Assert.assertTrue(bb.isFull());
        Assert.assertFalse(bb.isEmpty());
    }

    /**
     * 测试阻塞操作
     * 如果take方法成功,表示测试失败,执行测试的线程启动获取线程,等待一段时间然后中断
     * 如果take方法阻塞,那么将抛出InterruptedException,捕获异常为测试成功,take线程退出
     *   然后主测试线程将尝试和take线程join(合并),通过调用Thread.isAlive来验证join方法是否成功返回
     *   如果take线程能响应中断,那么join能很快的完成
     */
    @Test
    public void testTakeBlocksWhenEmpty() {
        final BoundBuffer<Integer> bb = new BoundBuffer<Integer>(10);
        Thread take = new Thread() {
            public void run() {
                try {
                    int unused = bb.take();
                    fail();
                } catch (InterruptedException success) {}
            }
        };
        try {
            take.start();
            Thread.sleep(1000);
            take.interrupt();
            take.join(1000);
            Assert.assertFalse(take.isAlive());
        } catch (Exception unexpected) {
            fail();
        }
    }

}

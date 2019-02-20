package com.roedeer.concurrent.pool;



import org.w3c.dom.Node;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @Description 并行递归树,每次迭代不需要来自后续递归迭代的结果,可以采用并行
 * @Author Roedeer
 * @Date 1/25/2019 10:21 PM
 **/
public class ParallelRecursive {


    static class Node<T> {
        T compute() {
            return null;
        }
        List<Node<T>> getChildren() {
            return null;
        }
    }

    /**
     * 串行递归转换为并行递归
     * @param nodes
     * @param results
     * @param <T>
     */
    public<T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
        for (Node<T> node : nodes) {
            results.add(node.compute());
            sequentialRecursive(node.getChildren(), results);
        }
    }

    public<T> void parallelRecursive(final Executor executor, List<Node<T>> nodes, final Collection<T> results) {
        for (final Node<T> node : nodes) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    results.add(node.compute());
                }
            });
            parallelRecursive(executor, node.getChildren(), results);
        }
    }

    /**
     * 等待通过并行方式计算的结果
     */
    public<T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Queue<T> resultQueue = new ConcurrentLinkedDeque<T>();
        parallelRecursive(executorService, nodes, resultQueue);
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return resultQueue;
    }
}

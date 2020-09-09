package com.roedeer.unitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 5/23/2019 5:27 PM
 **/
public class TestDemo {

    /**
     * 行覆盖测试,所有代码行都执行,但是c==3的条件根本没执行,覆盖强度不高
     */
    @Test
    @DisplayName("line coverage sample test")
    void testLineCoverageSample() {
        CoverageSampleMethods coverageSampleMethods = new CoverageSampleMethods();
        Assertions.assertTrue(coverageSampleMethods.testMethod(1,2,0));
    }

    /**
     * 参数化测试,条件判定覆盖 运行两次,把a==1和b==2的真假条件都覆盖到了
     * testConditionDecisionCoverageFalse c==3的假条件覆盖
     * 分支覆盖只是简单覆盖真假,属于条件判定覆盖的子集
     */
    @ParameterizedTest
    @DisplayName("Condition Decision coverage sample test result true")
    @CsvSource({
            "0, 2, 3",
            "1, 0, 3"
    })
    void testConditionDecisionCoverageTrue(int a, int b, int c) {
        CoverageSampleMethods coverageSampleMethods = new CoverageSampleMethods();
        Assertions.assertTrue(coverageSampleMethods.testMethod(a, b, c));
    }
    @Test
    @DisplayName("Condition Decision coverage sample test result false")
    void testConditionDecisionCoverageFalse() {
        CoverageSampleMethods coverageSampleMethods = new CoverageSampleMethods();
        Assertions.assertFalse(coverageSampleMethods.testMethod(0,0,0));
    }

    /**
     * 条件组合覆盖:所有条件的组合情况至少出现一次,那就是2的n次方,8种组合
     */
    @ParameterizedTest
    @DisplayName("Multiple Condition coverage sample test result true")
    @CsvSource({
            "1, 2, 3",
            "1, 2, 0",
            "1, 0, 3",
            "0, 2, 3",
            "0, 0, 3"
    })
    void testMultipleConditionCoverageTrue(int a, int b, int c) {
        CoverageSampleMethods coverageSampleMethods = new CoverageSampleMethods();
        Assertions.assertTrue(coverageSampleMethods.testMethod(a, b, c));
    }
    @ParameterizedTest
    @DisplayName("Multiple Condition coverage sample test result false")
    @CsvSource({
            "1, 0, 0",
            "0, 0, 0",
            "0, 2, 0"
    })
    void testMultipleConditionCoverageFalse(int a, int b, int c) {
        CoverageSampleMethods coverageSampleMethods = new CoverageSampleMethods();
        Assertions.assertFalse(coverageSampleMethods.testMethod(a, b, c));
    }


    /**
     * 路径覆盖测试, || 前者为true,不再计算后者  && 前者为false,不再计算后者
     * 5种情况
     */
    @ParameterizedTest
    @DisplayName("Path coverage sample test result true")
    @CsvSource({
            "1, 2, 0",
            "1, 0, 3",
            "0, 0, 3"
    })
    void testPathCoverageTrue(int a, int b, int c) {
        CoverageSampleMethods coverageSampleMethods = new CoverageSampleMethods();
        Assertions.assertTrue(coverageSampleMethods.testMethod(a, b, c));
    }
    @ParameterizedTest
    @DisplayName("Path coverage sample test result false")
    @CsvSource({
            "1, 0, 0",
            "0, 0, 0"
    })
    void testPathCoverageFalse(int a, int b, int c) {
        CoverageSampleMethods coverageSampleMethods = new CoverageSampleMethods();
        Assertions.assertFalse(coverageSampleMethods.testMethod(a, b, c));
    }


}

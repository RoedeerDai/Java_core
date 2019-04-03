package com.roedeer.jdk8;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description TODO
 * @Author Roedeer
 * @Date 3/8/2019 11:58 AM
 **/
@Slf4j
public class Start {

    /**
     * java8接口新增static和default方法,static变量,后两个可以继承,还有函数接口
     * java8的lambda表达式
     * Optional解决空值异常
     * Stream API,函数式编程
     * Date/Time API
     * base64编码的支持
     * 并行数组
     * java工具：jdeps--展示包层级和类层级的java依赖关系
     */


    @Test
    public void testOptional() {
        Optional<String> fullName = Optional.ofNullable(null);
        System.out.println("Full Name is Set?" + fullName.isPresent());
        System.out.println("Full Name:" + fullName.orElseGet( () -> "[NONE]"));
        System.out.println(fullName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));
    }

    private enum Status {
        OPEN, CLOSED
    };

    /**
     * Task类有一个分数(或伪复杂度)的概念,另外还有两种状态,OPEN或者CLOSED
     */
    private static final class Task {
        private final Status status;
        private final Integer points;

        Task (final Status status, final Integer points) {
            this.status = status;
            this.points = points;
        }

        public Status getStatus() {
            return status;
        }

        public Integer getPoints() {
            return points;
        }

        @Override
        public String toString() {
            return String.format("[%s, %d]", status, points);
        }
    }

    @Test
    public void testStream() {
        final Collection<Task> tasks = Arrays.asList(
                new Task(Status.OPEN, 5),
                new Task(Status.OPEN, 13),
                new Task(Status.CLOSED, 8));
        //计算这个task集合中有多少个OPEN状态的点,使用sum()
        /**
         * 首先，tasks集合被转换成steam表示；其次，在steam上的filter操作会过滤掉所有CLOSED的task；
         * 第三，mapToInt操作基于每个task实例的Task::getPoints方法将task流转换成Integer集合；
         * 最后，通过sum方法计算总和，得出最后的结果。
         * stream之上的操作可分为中间操作和晚期操作,中间操作会创建一个新的Stream然后将原stream中符合条件的元素放入
         * 晚期操作基本是对stream进行遍历
         */
        final long totalPointsOfOpenTasks = tasks
                .stream()
                .filter( task -> task.getStatus() == Status.OPEN)
                .mapToInt( Task::getPoints )
                .sum();
        System.out.println("OPEN TASKS number:" + totalPointsOfOpenTasks);

        //stream支持并行处理(parallel processing),计算所有task的点数之和
        final double totalPoints = tasks
                .stream()
                .parallel()
                .map(task -> task.getPoints())
                .reduce(0, Integer::sum);
        System.out.println("Total points : " + totalPoints);

        //对一个集合根据某些条件进行分组
        final Map<Status, List<Task>> map = tasks
                .stream()
                .collect(Collectors.groupingBy(Task::getStatus));
        System.out.println(map);

        //如何计算集合中每个任务的点数在集合中所占的比重
        final Collection<String> result = tasks
                .stream()
                .mapToInt(Task::getPoints)
                .asLongStream()
                .mapToDouble(points -> points / totalPoints)
                .boxed()
                .mapToLong(weigth -> (long)(weigth * 100))
                .mapToObj(percentage -> percentage + "%")
                .collect(Collectors.toList());
        System.out.println(result);

        //stream读取文件
        String pathname = Thread.currentThread().getContextClassLoader().getResource("log4j.properties").getPath();
        final Path path = new File(pathname).toPath();
        try(Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.onClose(() -> System.out.println("Done")).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Test
    public void testDateTime() {
        //Clock中没有包含当地时区,所以时间需要加上北京东八区的八个小时
        //ISO-8601国际标准要在日期和时间的组合表示之间加上大写字母T,需要注意
        final Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());  //代替System.currentTimeMillis()
        System.out.println(clock.millis());   //代替TimeZone.getDefault()

        /**
         * LocalDate和LocalTime类。LocalDate仅仅包含ISO-8601日历系统中的日期部分；
         * LocalTime则仅仅包含该日历系统中的时间部分。这两个类的对象都可以使用Clock对象构建得到
         */
        final LocalDate date = LocalDate.now();
        final LocalDate dateFromClock = LocalDate.now( clock );

        System.out.println("LocalDate:" + date );
        System.out.println("LocalDate from clock " + dateFromClock );

        // Get the local date and local time
        final LocalTime time = LocalTime.now();
        final LocalTime timeFromClock = LocalTime.now( clock );

        System.out.println("LocalTime:" + time );
        System.out.println("LocalTimeFromClock:" + timeFromClock );

        //LocalDateTime类包含了LocalDate和LocalTime的信息，但是不包含ISO-8601日历系统中的时区信息
        final LocalDateTime localDateTime = LocalDateTime.now();
        final LocalDateTime dateTimeFromClock = LocalDateTime.now(clock);
        System.out.println("LocalDateTime:" + localDateTime);
        System.out.println("LocalDateTimeFromClock:" + dateTimeFromClock);

        //如果你需要特定时区的data/time信息，则可以使用ZoneDateTime，它保存有ISO-8601日期系统的日期和时间，而且有时区信息
        // Get the zoned date/time
        final ZonedDateTime zonedDatetime = ZonedDateTime.now();
        final ZonedDateTime zonedDatetimeFromClock = ZonedDateTime.now( clock );
        final ZonedDateTime zonedDatetimeFromZone = ZonedDateTime.now( ZoneId.of( "America/Los_Angeles" ) );

        System.out.println("ZonedDateTime:" + zonedDatetime );
        System.out.println("ZonedDateTime from clock:" + zonedDatetimeFromClock );
        System.out.println("ZonedDateTime from zone" + zonedDatetimeFromZone );

        //Duration类，它持有的时间精确到秒和纳秒。这使得我们可以很容易得计算两个日期之间的不同
        // Get duration between two dates
        final LocalDateTime from = LocalDateTime.of( 2017, Month.APRIL, 16, 0, 0, 0 );
        final LocalDateTime to = LocalDateTime.of( 2018, Month.APRIL, 16, 23, 59, 59 );

        //计算2014年4月16日和2015年4月16日之间的天数和小时数
        final Duration duration = Duration.between( from, to );
        System.out.println( "Duration in days: " + duration.toDays() );
        System.out.println( "Duration in hours: " + duration.toHours() );

    }


    /**
     * 新的Base64API也支持URL和MINE的编码解码。
     * (Base64.getUrlEncoder() / Base64.getUrlDecoder(), Base64.getMimeEncoder() / Base64.getMimeDecoder())
     * BASE64不是用来加密的，是BASE64编码后的字符串，全部都是由标准键盘上面的常规字符组成，这样编码后的字符串在网关之间传递不会产生UNICODE字符串不能识别或者丢失的现象
     * 你再仔细研究下EMAIL就会发现其实EMAIL就是用base64编码过后再发送的。然后接收的时候再还原
     */
    @Test
    public void testBase64() {
        final String text = "Base64 finally in Java 8!";

        final String encoded = Base64
                .getEncoder()
                .encodeToString( text.getBytes( StandardCharsets.UTF_8 ) );
        System.out.println( encoded );

        final String decoded = new String(
                Base64.getDecoder().decode( encoded ),
                StandardCharsets.UTF_8 );
        System.out.println( decoded );
    }

    /**
     * 并行数组
     * 使用parallelSetAll()方法生成20000个随机数，然后使用parallelSort()方法进行排序。这个程序会输出乱序数组和排序数组的前10个元素
     */
    @Test
    public void testParallelArrays() {
        long[] arrayOfLong = new long[20000];
        Arrays.parallelSetAll(arrayOfLong, index -> ThreadLocalRandom.current().nextInt(1000000));
        Arrays.stream(arrayOfLong).limit(10).forEach(i -> System.out.print(i + " "));
        System.out.println();

        Arrays.parallelSort(arrayOfLong);
        Arrays.stream(arrayOfLong).limit(10).forEach(i -> System.out.print(i + " "));
        System.out.println();
    }


    public void testLamada() {

    }


}

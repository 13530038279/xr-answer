package com.xr.question2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试 Java 对IP请求进行限流.
 * @author Administrator
 * @param <T>
 */
public class TestSpeedLimitedThread <T> extends Thread {

	SpeedLimiter<T> limiter = null;
    public TestSpeedLimitedThread(SpeedLimiter<T> limiter)
    {
        this.limiter = limiter;
    }
    public void run()
    {
        long i = 0;
        while(true)
        {
        	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String a = s.format(new Date());
            T tmpResource = limiter.consume();
            System.out.println("thread id: " + currentThread().getId() + " : " +(i+1) + ": 已经获取到资源：" + tmpResource + ":"+a);
            i++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            limiter.returnResource();
        }
    }
    
    
    public static void main(String args[]){
            String resource = "myresource";
            SpeedLimiter<String> test = new SpeedLimiter<String>(2, 1000, resource);
            TestSpeedLimitedThread<String> testThread1 = new TestSpeedLimitedThread<String>(test);
            testThread1.start();
            //TestSpeedLimitedThread<String> testThread2 = new TestSpeedLimitedThread<String>(test);
            //testThread2.start();
            //TestSpeedLimitedThread<String> testThread3 = new TestSpeedLimitedThread<String>(test);
            //testThread3.start();

            try {
                testThread1.join();
                //testThread2.join();
                //testThread3.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    
}


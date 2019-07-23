package com.zzq.config.quartz;

import org.springframework.context.annotation.Configuration;

/**
 * @author maxwell
 * @Title: zhangzq
 * @ProjectName quartz-control
 * @Description: TODO
 * @date 2019/7/23 10:30
 * @email: bestzijia@gmail.com
 * @github: https://github.com/winterme/
 * @csdn: https://blog.csdn.net/yali_aini
 */
@Configuration
public class JobGetAll {

    public void getAll(){
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println( "======>任务二" );
    }

    public void insert(){
        System.out.println("存储成功============>任务一");
    }

}
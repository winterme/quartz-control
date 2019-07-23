package com.zzq.config.quartz;

import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 配置类
 */
@Configuration
public class QuartzConfig {

    // 定义方法，做什么
    @Bean(name = "job1")
    public MethodInvokingJobDetailFactoryBean job2(JobGetAll jobGetAll){
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        factoryBean.setConcurrent(true);
        factoryBean.setGroup("任务一组");
        // 使用哪个对象
        factoryBean.setTargetObject(jobGetAll);
        // 使用哪个方法
        factoryBean.setTargetMethod("insert");

        return  factoryBean;
    }

    // 定义什么时候做，使用 cron 表达式
    @Bean(name = "cron1")
    public CronTriggerFactoryBean cron1(@Qualifier("job1")MethodInvokingJobDetailFactoryBean job1){
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        // 设置job对象
        factoryBean.setJobDetail( job1.getObject() );
        factoryBean.setGroup("任务一组");
        // 设置执行时间
        factoryBean.setCronExpression("0/5 * * * * ?");
        return  factoryBean;
    }


    // 定义方法，做什么
    @Bean(name = "job2")
    public MethodInvokingJobDetailFactoryBean job1(JobGetAll jobGetAll){
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        factoryBean.setConcurrent(true);
        // 使用哪个对象
        factoryBean.setTargetObject(jobGetAll);
        // 使用哪个方法
        factoryBean.setTargetMethod("getAll");

        return  factoryBean;
    }



    // 定义什么时候做，使用 cron 表达式
    @Bean(name = "cron2")
    public CronTriggerFactoryBean cron2(@Qualifier("job2")MethodInvokingJobDetailFactoryBean job2){
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        // 设置job对象
        factoryBean.setJobDetail( job2.getObject() );
        // 设置执行时间
        factoryBean.setCronExpression("0/10 * * * * ?");
        return  factoryBean;
    }


    // 定义方法，做什么
    @Bean(name = "job3")
    public MethodInvokingJobDetailFactoryBean job3(JobUser jobUser){
        MethodInvokingJobDetailFactoryBean factoryBean = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        factoryBean.setConcurrent(true);
        // 使用哪个对象
        factoryBean.setTargetObject(jobUser);
        // 使用哪个方法
        factoryBean.setTargetMethod("getUser");

        return  factoryBean;
    }

    // 定义什么时候做，使用 cron 表达式
    @Bean(name = "cron3")
    public CronTriggerFactoryBean cron3(@Qualifier("job3")MethodInvokingJobDetailFactoryBean job3){
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        // 获取 job对象
        factoryBean.setJobDetail( job3.getObject() );
        // 设置 时间表达式
        factoryBean.setCronExpression("0/10 * * * * ?");
        return  factoryBean;
    }



    // 定义 任务，传入 triggers
    @Bean(name = "sch")
    public SchedulerFactoryBean scheduler1(Trigger... triggers){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        // 设置 triggers
        factoryBean.setTriggers( triggers );
        // 自动运行
        factoryBean.setAutoStartup(true);

        return factoryBean;
    }

}

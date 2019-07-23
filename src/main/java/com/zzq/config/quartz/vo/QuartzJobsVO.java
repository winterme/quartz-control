package com.zzq.config.quartz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author maxwell
 * @Title: zhangzq
 * @ProjectName quartz-control
 * @Description: TODO
 * @date 2019/7/23 10:38
 * @email: bestzijia@gmail.com
 * @github: https://github.com/winterme/
 * @csdn: https://blog.csdn.net/yali_aini
 */
@ApiModel(value = "定时任务信息类",description = "定时任务信息返回类")
public class QuartzJobsVO {
    @ApiModelProperty(value = "任务名称" , name = "jobDetailName")
    private String jobDetailName;
    @ApiModelProperty(value = "任务执行时间cron表达式" , name = "jobCronExpression")
    private String jobCronExpression;
    private String timeZone;
    @ApiModelProperty(value = "任务所在组" , name = "groupName")
    private String groupName;

    public String getJobDetailName() {
        return jobDetailName;
    }

    public void setJobDetailName(String jobDetailName) {
        this.jobDetailName = jobDetailName;
    }

    public String getJobCronExpression() {
        return jobCronExpression;
    }

    public void setJobCronExpression(String jobCronExpression) {
        this.jobCronExpression = jobCronExpression;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}


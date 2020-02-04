package com.zzq.config.quartz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
    @ApiModelProperty(value = "最后执行时间" , name = "finalFireTime")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyyMMddHHmmss")
    @DateTimeFormat(pattern="yyyyMMddHHmmss")
    private Date finalFireTime;

    public Date getFinalFireTime() {
        return finalFireTime;
    }

    public void setFinalFireTime(Date finalFireTime) {
        this.finalFireTime = finalFireTime;
    }

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


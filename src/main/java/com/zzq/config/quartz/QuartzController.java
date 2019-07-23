package com.zzq.config.quartz;

import com.zzq.config.quartz.vo.QuartzJobsVO;
import io.swagger.annotations.*;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author maxwell
 * @Title: zhangzq
 * @ProjectName quartz-control
 * @Description: TODO
 * @date 2019/7/23 10:33
 * @email: bestzijia@gmail.com
 * @github: https://github.com/winterme/
 * @csdn: https://blog.csdn.net/yali_aini
 */
@Api(value = "quzrtz-control", description = "定时任务控制控制器")
@Controller
public class QuartzController {

    @Autowired
    @Qualifier("sch")
    private Scheduler scheduler;

    @ApiOperation(value = "查询所有的任务", notes = "查询所有的任务，返回json，key=> groupName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/getAllJob", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllJob() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        List<String> jobGroupNames = scheduler.getJobGroupNames();
        for (String groupName : jobGroupNames) {
            ArrayList<QuartzJobsVO> quartzJobsVOList = new ArrayList<>();
            //组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
            GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
            //获取所有的triggerKey
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
            for (TriggerKey triggerKey : triggerKeySet) {
                //通过triggerKey在scheduler中获取trigger对象
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                //获取trigger拥有的Job
                JobKey jobKey = trigger.getJobKey();
                JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                //组装页面需要显示的数据
                QuartzJobsVO quartzJobsVO = new QuartzJobsVO();
                quartzJobsVO.setGroupName(groupName);
                quartzJobsVO.setJobDetailName(jobDetail.getName());
                quartzJobsVO.setJobCronExpression(trigger.getCronExpression());
                quartzJobsVO.setTimeZone(trigger.getTimeZone().getID());
                quartzJobsVOList.add(quartzJobsVO);
            }
            map.put(groupName, quartzJobsVOList);
        }

        return map;
    }

    @ApiOperation(value = "查询所有的正在执行的任务", notes = "查询所有的正在执行的任务，返回json，key=> groupName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @RequestMapping(value = "/getExecutionJobs" , method = RequestMethod.GET)
    @ResponseBody
    public Object getExecutionJobs() throws SchedulerException {
        HashMap<String, ArrayList<QuartzJobsVO>> map = new HashMap<>();
        ArrayList<QuartzJobsVO> quartzJobsVOList = new ArrayList<>();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext executionContext : executingJobs) {
            JobDetailImpl jobDetail = (JobDetailImpl) executionContext.getJobDetail();
            QuartzJobsVO vo = new QuartzJobsVO();
            vo.setJobDetailName( jobDetail.getName() );
            vo.setGroupName( jobDetail.getGroup() );
            vo.setTimeZone( ((CronTrigger) executionContext.getTrigger()).getTimeZone().getID() );
            vo.setJobCronExpression( ((CronTrigger) executionContext.getTrigger()).getCronExpression() );

            quartzJobsVOList.add(vo);
        }

        for (QuartzJobsVO vo : quartzJobsVOList) {
            if( map.keySet().contains(vo.getGroupName()) ){
                map.get(vo.getGroupName()).add(vo);
            }else{
                ArrayList<QuartzJobsVO> data = new ArrayList<>();
                data.add(vo);
                map.put(vo.getGroupName(), data);
            }
        }

        return map;
    }

    @ApiOperation(value = "暂停定时任务", notes = "传入定时任务名字，进行暂停定时任务，定时任务名字可以从  上面那个查询所有的方法中获取")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "jobname", value = "jobname", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "groupName", value = "groupName", required = true, dataType = "String")
    })
    @RequestMapping(value = "/pause", method = RequestMethod.GET)
    @ResponseBody
    public String pause(String jobname , String groupName) throws Exception {

        JobKey key = new JobKey(jobname , groupName);
        scheduler.pauseJob(key);

        return "pause->" + groupName + "->" + jobname;
    }

    @ApiOperation(value = "启动定时任务", notes = "传入定时任务名字，进行启动定时任务，定时任务名字可以从  上面那个查询所有的方法中获取")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "jobname", value = "jobName", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "groupName", value = "groupName", required = true, dataType = "String")
    })
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    @ResponseBody
    public String start(String jobname , String groupName) throws Exception {

        JobKey key = new JobKey(jobname , groupName);
        scheduler.resumeJob(key);

        return "start->" + groupName + "->" + jobname;
    }

    @ApiOperation(value = "动态修改定时任务的执行时间", notes = "传入定时任务名字，和cron 表达式")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "jobname", value = "jobName", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "cron", value = "cronExp", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "groupName", value = "groupName", required = true, dataType = "String")
    })
    @RequestMapping(value = "/trigger", method = RequestMethod.GET)
    @ResponseBody
    public String trigger(String jobname, String groupName ,String cron) throws Exception {
        // 获取任务
        JobKey jobKey = new JobKey(jobname , groupName);
        // 获取 jobDetail
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        // 生成 trigger
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        // 删除任务，不删除会报错。报任务已存在
        scheduler.deleteJob(jobKey);
        // 启动任务
        scheduler.scheduleJob(jobDetail, trigger);

        return "trigger->" + groupName + "->" + jobname + "[cron]->" + cron;
    }

}

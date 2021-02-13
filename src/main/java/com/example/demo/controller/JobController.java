package com.example.demo.controller;
import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.QuartzBean;
import com.example.demo.service.JobService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;
    @Autowired
    private JobService jobService;
    @RequestMapping("list")
    public String list() {
    	return "/JobManager.html";
    }

    /**
     * 获取任务列表
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "queryjob")
    @ResponseBody
    public Map<String, Object> queryjob( @RequestParam(value="pageNum",required=true)Integer pageNum, @RequestParam(value="pageSize",required=true)Integer pageSize) {
		PageInfo<QuartzBean> jobAndTrigger = jobService.listQuartzBean(pageNum, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("JobAndTrigger", jobAndTrigger);
		map.put("number", jobAndTrigger.getTotal()); 
		return map;
    }

    /**
     * 新增任务
     *
     * @param quartz
     * @return
     */
    @RequestMapping("addjob")
    @ResponseBody
    public String addjob(QuartzBean quartz) throws Exception {
        if (quartz.getOldJobGroup() != null) {
            JobKey key = new JobKey(quartz.getOldJobName(), quartz.getOldJobGroup());
            scheduler.deleteJob(key);
        }
        //获取.class
        Class cls = Class.forName(quartz.getJobClassName());
        cls.newInstance();
        //创建jobdetail
        JobDetail job = JobBuilder.newJob(cls).withIdentity(quartz.getJobName(),
                quartz.getJobGroup())
                //设置参数
                //.usingJobData("aa", "ceshi")
                //描述
                .withDescription(quartz.getDescription())
                .build();
        // 使用cron表达式
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger" + quartz.getJobName(), quartz.getJobGroup())
                .startNow()
                .withSchedule(cronScheduleBuilder)
                .build();
        //交由Scheduler安排触发
        scheduler.scheduleJob(job, trigger);
        return "保存成功！";
    }

    /**
     * 立即执行
     *
     * @param quartz
     * @return
     */
    @RequestMapping(value = "/zhixing")
    @ResponseBody
    public String trigger(QuartzBean quartz) throws Exception {
        JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
        scheduler.triggerJob(key);
        return "执行成功！";
    }

    /**
     * 暂停任务
     *
     * @param quartz
     * @return
     */
    @RequestMapping("pause")
    @ResponseBody
    public String pause(QuartzBean quartz) throws Exception {
        JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
        scheduler.pauseJob(key);
        return "暂停成功！";
    }

    /**
     * 从暂停中恢复过来
     *
     * @param quartz
     * @return
     */
    @RequestMapping("resume")
    @ResponseBody
    public String resume(QuartzBean quartz) throws Exception {
        JobKey key = new JobKey(quartz.getJobName(), quartz.getJobGroup());
        scheduler.resumeJob(key);
        return "恢复成功！";
    }

    /**
     * 删除任务
     *
     * @param quartz
     * @return
     */
    @RequestMapping("remove")
    @ResponseBody
    public String remove(QuartzBean quartz) throws Exception {
        TriggerKey triggerKey = TriggerKey.triggerKey(quartz.getJobName(), quartz.getJobGroup());
        // 停止触发器
        scheduler.pauseTrigger(triggerKey);
        // 移除触发器
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(JobKey.jobKey(quartz.getJobName(), quartz.getJobGroup()));
        System.out.println("removeJob:" + JobKey.jobKey(quartz.getJobName()));
        return "删除成功！";
    }
}
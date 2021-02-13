package com.example.demo.entity;

public class QuartzBean {
    private String jobName;
    private String jobGroup;
    private String jobClassName;
    private String description;
    private String triggerName;
    private String triggerGroup;
    private String oldJobGroup;
    private String oldJobName;
	private String cronExpression;
    
    public String getOldJobGroup() {
		return oldJobGroup;
	}
	public void setOldJobGroup(String oldJobGroup) {
		this.oldJobGroup = oldJobGroup;
	}
	public String getOldJobName() {
		return oldJobName;
	}
	public void setOldJobName(String oldJobName) {
		this.oldJobName = oldJobName;
	}
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroup() {
		return triggerGroup;
	}
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}
}
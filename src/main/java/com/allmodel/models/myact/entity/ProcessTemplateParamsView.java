package com.allmodel.models.myact.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name ="process_template_params" )
public class ProcessTemplateParamsView {

    @Id
    @Column(name = "id" )
    private Long id;
    /**
     * 流程模板标识
     */
    @Column(name = "process_uuid_index" )
    private String processUuidIndex;

    /**
     * 流程任务序号
     */
    @Column(name = "process_task_index" )
    private String processTaskIndex;

    /**
     * 当前任务参数
     */
    @Column(name = "task_index_parameters" )
    private String taskIndexParameters;

    /**
     * 任务名称
     */
    @Column(name = "task_name" )
    private String taskName;

    /**
     * 是否为判断语句
     */
    @Column(name = "is_judge" )
    private String isJudge;

    /**
     * 失败回调号
     */
    @Column(name = "process_jump_back" )
    private String processJumpBack;

    /**
     * 部门
     */
    @Column(name = "sector" )
    private String sector;

    /**
     * 扩展功能
     */
    @Column(name = "function" )
    private String function;

    public String getProcessUuidIndex() {
        return processUuidIndex;
    }

    public void setProcessUuidIndex(String processUuidIndex) {
        this.processUuidIndex = processUuidIndex;
    }

    public String getProcessTaskIndex() {
        return processTaskIndex;
    }

    public void setProcessTaskIndex(String processTaskIndex) {
        this.processTaskIndex = processTaskIndex;
    }

    public String getTaskIndexParameters() {
        return taskIndexParameters;
    }

    public void setTaskIndexParameters(String taskIndexParameters) {
        this.taskIndexParameters = taskIndexParameters;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getIsJudge() {
        return isJudge;
    }

    public void setIsJudge(String isJudge) {
        this.isJudge = isJudge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessJumpBack() {
        return processJumpBack;
    }

    public void setProcessJumpBack(String processJumpBack) {
        this.processJumpBack = processJumpBack;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}

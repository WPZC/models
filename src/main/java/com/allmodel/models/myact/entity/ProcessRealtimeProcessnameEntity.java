package com.allmodel.models.myact.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description
 * @Author  Wqy
 * @Date 2019-09-29
 */

@Entity
@Table( name ="process_realtime_processname" )
public class ProcessRealtimeProcessnameEntity  {


    @Id
    @Column(name = "id" )
    private Long id;

    /**
     * 流程模板唯一标识
     */
    @Column(name = "process_uuid" )
    private String processUuid;

    /**
     * 实时流程唯一标识
     */
    @Column(name = "task_uuid" )
    private String taskUuid;

    /**
     * 任务序号
     */
    @Column(name = "task_index" )
    private String taskIndex;

    /**
     * 任务名称
     */
    @Column(name = "task_name" )
    private String taskName;

    /**
     * 是否是判断
     */
    @Column(name = "is_judge" )
    private String isJudge;

    /**
     * 是否处理
     */
    @Column(name = "is_processing" )
    private String isProcessing;

    /**
     * 用户唯一标识(审批人)
     */
    @Column(name = "user_id" )
    private String userId;

    /**
     * 处理时间
     */
    @Column(name = "processing_date" )
    private Date processingDate;

    /**
     * 任务字段数据
     */
    @Column(name = "task_msg" )
    private String taskMsg;

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
     * 部门人
     */
    @Column(name = "sector_people" )
    private String sectorPeople;

    /**
     * 流程名称
     */
    @Column(name = "process_name" )
    private String processName;

    /**
     * 失败返回意见
     */
    @Column(name = "opinions_failure" )
    private String opinionsFailure;

    /**
     * 扩展功能
     */
    @Column(name = "function" )
    private String function;

    /**
     * 打回人
     */
    @Column(name = "back_people" )
    private String backPeople;

    /**
     * 附件地址
     */
    @Column(name = "attachment" )
    private String attachment;

    /**
     * 流程状态 0 完成 1 未完成 2 被打回 3 拒绝
     */
    @Transient
    private String processState;

    public String getOpinionsFailure() {
        return opinionsFailure;
    }

    public void setOpinionsFailure(String opinionsFailure) {
        this.opinionsFailure = opinionsFailure;
    }

    public String getProcessState() {
        return processState;
    }

    public void setProcessState(String processState) {
        this.processState = processState;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessUuid() {
        return this.processUuid;
    }

    public void setProcessUuid(String processUuid) {
        this.processUuid = processUuid;
    }

    public String getTaskIndex() {
        return this.taskIndex;
    }

    public void setTaskIndex(String taskIndex) {
        this.taskIndex = taskIndex;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getIsJudge() {
        return this.isJudge;
    }

    public void setIsJudge(String isJudge) {
        this.isJudge = isJudge;
    }

    public String getIsProcessing() {
        return this.isProcessing;
    }

    public void setIsProcessing(String isProcessing) {
        this.isProcessing = isProcessing;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getProcessingDate() {
        return this.processingDate;
    }

    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    public String getTaskMsg() {
        return this.taskMsg;
    }

    public void setTaskMsg(String taskMsg) {
        this.taskMsg = taskMsg;
    }

    public String getTaskUuid() {
        return taskUuid;
    }

    public void setTaskUuid(String taskUuid) {
        this.taskUuid = taskUuid;
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

    public String getSectorPeople() {
        return sectorPeople;
    }

    public void setSectorPeople(String sectorPeople) {
        this.sectorPeople = sectorPeople;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getBackPeople() {
        return backPeople;
    }

    public void setBackPeople(String backPeople) {
        this.backPeople = backPeople;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}

package com.allmodel.models.myact.service;



import com.allmodel.models.myact.entity.ProcessRealtimeProcessnameEntity;
import com.allmodel.models.myact.entity.ProcessTemplateParamsView;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RealTimeProcessTaskService {


    /**
     * 选择使用模板
     * @param uuid
     * @return
     */
    String choiceTemplate(String uuid);

    /**
     * 选择完模板，填写第一步所需要的东西，然后发起流程
     * @param userid 用户唯一标识
     * @param processUuid 流程唯一标识
     * @param dataJson 提交的数据，字段与字段表中的对应
     * @return
     */
    String startProcess(String userid, String processUuid, String dataJson, String sectorPeople, String sector, String taskIndex, String fileUrl);


    /**
     * 根据userid查询该用户是否有需要处理的流程
     * @param userid
     * @return
     */
    List<ProcessRealtimeProcessnameEntity> findBySectorPeople(String userid);

    /**
     * 根据taskUuid和taskIndex查询上一条记录的参数内容
     * @param taskUuid
     * @param taskIndex
     * @return
     */
    ProcessRealtimeProcessnameEntity findByTaskUuidAndIndex(String taskUuid, String taskIndex);

    /**
     * 审批第一步
     * 查询数据库该步骤有没有传往下一级的参数
     * @param processUuid
     * @return
     */
    ProcessTemplateParamsView approvalPassed1(String processUuid, String taskIndex);

    /**
     * 审批第二步
     * 添加参数完成该次审批
     * @param processUuid
     * @return
     */
    String approvalPassed2(String processUuid, String taskUuid, String taskIndex, String params, String sectorPeople, String sector, String fileUrl);


    /**
     * 审批未通过第一步
     * 选择打回人
     * @param processUuid
     * @return
     */
    List<ProcessRealtimeProcessnameEntity> auditFailed1(String processUuid, String taskUuid, String taskIndex);

    /**
     * 审批未通过第二步
     * 查询数据库该步骤有没有传往下一级的参数
     * @param processUuid
     * @return
     */
    ProcessTemplateParamsView auditFailed2(String processUuid, String taskIndex);

    /**
     * 审批未通过第三步
     * 查询数据库该步骤有没有传往下一级的参数
     * @param processUuid
     * @return
     */
    String auditFailed3(String processUuid, String taskUuid, String taskIndex, String params, String sectorPeople, String sector, String state, String backIndex);

    /**
     * 文件上传
     * @param file
     * @return
     */
    String uploadFile(MultipartFile file);

    /**
     * 获取完结流程
     * @param userId
     * @return
     */
    List<ProcessRealtimeProcessnameEntity> getOverProcess(String userId);

    /**
     * 获取已办流程
     * @param userId
     * @return
     */
    List<ProcessRealtimeProcessnameEntity> getDoneProcess(String userId);
}

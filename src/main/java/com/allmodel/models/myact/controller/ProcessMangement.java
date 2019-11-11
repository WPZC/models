package com.allmodel.models.myact.controller;


import com.allmodel.models.myact.entity.ProcessParamsEntity;
import com.allmodel.models.myact.entity.ProcessRealtimeProcessnameEntity;
import com.allmodel.models.myact.entity.ProcessTemplateNameEntity;
import com.allmodel.models.myact.entity.ProcessTemplateParamsView;
import com.allmodel.models.myact.entity.view.OutView;
import com.allmodel.models.myact.service.ProcessParamsService;
import com.allmodel.models.myact.service.ProcessTemplateService;
import com.allmodel.models.myact.service.RealTimeProcessTaskService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 流程管理
 * @Author WQY
 * @Date 2019/10/8 10:06
 * @Version 1.0
 */
@Controller
@RequestMapping("/pm")
public class ProcessMangement {


    @Autowired
    private ProcessTemplateService processTemplateService;
    @Autowired
    private RealTimeProcessTaskService realTimeProcessTaskService;
    @Autowired
    private ProcessParamsService processParamsService;

    private Gson gson = new Gson();

    /**
     * 获取流程模板名称和uuid
     * @return
     */
    @RequestMapping("/getProTemNames")
    @ResponseBody
    public String getProTemNames(){
        OutView outView = new OutView();
        List<ProcessTemplateNameEntity> rs;
        try {
            rs = processTemplateService.getProTemNames();
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
        }
        return gson.toJson(outView);
    }

    /**
     * 选择流程模板
     * @return
     */
    @RequestMapping("/chiocTemplate")
    @ResponseBody
    public String chiocTemplate(String uuid){
        OutView outView = new OutView();
        ProcessParamsEntity rs;
        try {
            rs = processParamsService.findByUuid(uuid);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
        }
        return gson.toJson(outView);
    }

    /**
     * 启动流程
     * @param uuid
     * @param params
     * @param userid
     * @return
     */
    @RequestMapping("/startProcess")
    @ResponseBody
    public String startProcess(String uuid,String params,String userid,String sectorPeople,String sector,String taskIndex,String fileUrl){

        OutView outView = new OutView();
        try {
            realTimeProcessTaskService.startProcess(userid,uuid,params,sectorPeople,sector,taskIndex,fileUrl);
            outView.setState(0);
            outView.setMsg("");
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
        }
        return gson.toJson(outView);
    }



    /**
     * 获取发起人的首次部门名称编号
     * @param uuid
     * @return
     */
    @RequestMapping("/getSectorPeople")
    @ResponseBody
    public String getSectorPeople(String uuid){

        OutView outView = new OutView();

        try {
            ProcessTemplateParamsView rs = processTemplateService.getSectorPeople(uuid);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
        }
        return gson.toJson(outView);
    }

    /**
     * 获取流程信息
     * @param userid
     * @return
     */
    @RequestMapping("/findBySectorPeople")
    @ResponseBody
    public String findBySectorPeople(String userid){

        OutView outView = new OutView();
        List<ProcessRealtimeProcessnameEntity> rs;
        try {
            rs = realTimeProcessTaskService.findBySectorPeople(userid);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
            e.printStackTrace();
        }
        return gson.toJson(outView);
    }


    /**
     * 根据uuid和序号查询任务参数
     * @param taskUuid
     * @param taskIndex
     * @return
     */
    @RequestMapping("/findByTaskUuidAndIndex")
    @ResponseBody
    public String findByTaskUuidAndIndex(String taskUuid,String taskIndex){

        OutView outView = new OutView();
        ProcessRealtimeProcessnameEntity rs;
        try {
            rs = realTimeProcessTaskService.findByTaskUuidAndIndex(taskUuid,taskIndex);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
        }
        return gson.toJson(outView);
    }


    /**
     * 审批通过管理1
     * @param processUuid
     * @param taskIndex 任务序号，审批的上一级
     * @return
     */
    @RequestMapping("/approvalPassed1")
    @ResponseBody
    public String approvalPassed1(String processUuid,String taskIndex){

        OutView outView = new OutView();
        ProcessTemplateParamsView rs;
        try {
            rs = realTimeProcessTaskService.approvalPassed1(processUuid,taskIndex);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
        }
        return gson.toJson(outView);
    }

    /**
     * 审批通过管理2
     * @param processUuid
     * @param taskIndex
     * @return
     */
    @RequestMapping("/approvalPassed2")
    @ResponseBody
    public String approvalPassed2(String processUuid,String taskUuid,String taskIndex,String params,String sectorPeople,String sector,String fileUrl){

        OutView outView = new OutView();
        String rs;
        try {
            rs = realTimeProcessTaskService.approvalPassed2(processUuid,taskUuid,taskIndex,params,sectorPeople,sector,fileUrl);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
            e.printStackTrace();
        }
        return gson.toJson(outView);
    }

    /**
     * 审批未通过管理1
     * @param processUuid
     * @param taskIndex
     * @return
     */
    @RequestMapping("/auditFailed1")
    @ResponseBody
    public String auditFailed1(String processUuid,String taskUuid, String taskIndex){

        OutView outView = new OutView();
        List<ProcessRealtimeProcessnameEntity> rs;
        try {
            rs = realTimeProcessTaskService.auditFailed1(processUuid,taskUuid,taskIndex);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
            e.printStackTrace();
        }
        return gson.toJson(outView);
    }

    /**
     * 审批未通过管理2
     * @param processUuid
     * @param taskIndex
     * @return
     */
    @RequestMapping("/auditFailed2")
    @ResponseBody
    public String auditFailed1(String processUuid,String taskIndex){

        OutView outView = new OutView();
        ProcessTemplateParamsView rs;
        try {
            rs = realTimeProcessTaskService.auditFailed2(processUuid,taskIndex);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
            e.printStackTrace();
        }
        return gson.toJson(outView);
    }

    /**
     * 审批未通过管理3
     * @param processUuid
     * @param taskIndex
     * @return
     */
    @RequestMapping("/auditFailed3")
    @ResponseBody
    public String auditFailed2(String processUuid,String taskUuid,String taskIndex,String params,String sectorPeople,String sector,String state,String backIndex){

        OutView outView = new OutView();
        String rs;
        try {
            rs = realTimeProcessTaskService.auditFailed3(processUuid,taskUuid,taskIndex,params,sectorPeople,sector,state,backIndex);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
            e.printStackTrace();
        }
        return gson.toJson(outView);
    }



    /**
     * 上传附件
     * @return
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public String uploadFile(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request){

        OutView outView = new OutView();
        String rs ;
        try {
            rs = realTimeProcessTaskService.uploadFile(file);
            outView.setState(0);
            outView.setMsg(rs);
        } catch (Exception e) {
            outView.setState(1);
            outView.setMsg("出现异常");
            e.printStackTrace();
        }

        return gson.toJson(outView);
    }

    /**
     * 获取完结流程
     * @return
     */
    @RequestMapping("/getOverProcess")
    @ResponseBody
    public String getOverProcess(String userId){

        OutView outView = new OutView();
        List<ProcessRealtimeProcessnameEntity> rs ;
        try {
            rs = realTimeProcessTaskService.getOverProcess(userId);
            outView.setState(0);
            outView.setMsg(rs);
        } catch (Exception e) {
            outView.setState(1);
            outView.setMsg("出现异常");
            e.printStackTrace();
        }

        return gson.toJson(outView);
    }


    /**
     * 获取已办流程
     * @return
     */
    @RequestMapping("/getDoneProcess")
    @ResponseBody
    public String getDoneProcess(String userId){

        OutView outView = new OutView();
        List<ProcessRealtimeProcessnameEntity> rs ;
        try {
            rs = realTimeProcessTaskService.getDoneProcess(userId);
            outView.setState(0);
            outView.setMsg(rs);
        } catch (Exception e) {
            outView.setState(1);
            outView.setMsg("出现异常");
            e.printStackTrace();
        }

        return gson.toJson(outView);
    }


}

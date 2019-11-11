package com.allmodel.models.myact.controller;


import com.allmodel.models.authority.dao.User_Jpa;
import com.allmodel.models.authority.entity.UserEntity;
import com.allmodel.models.myact.entity.ProcessRealtimeProcessnameEntity;
import com.allmodel.models.myact.entity.RealTimeUserView;
import com.allmodel.models.myact.entity.RealtimeProcessTaskEntity;
import com.allmodel.models.myact.service.MineProcessService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Description
 * @Author: zhuhaoyu
 * @Date: 2019/10/15 17:12
 */
@Controller
@RequestMapping("/mine")
public class MineProcessController {

    @Autowired
    private MineProcessService mineProcessService;
    @Autowired
    private User_Jpa userJpa;


    private Gson gson = new Gson();
    public static Map<Long,String> map_userName = new HashMap<>();
    /**
     * 当前用户查询我的申请列表
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/getAllProcessByUserId")
    @ResponseBody
    public String getAllProcessByUserId(Long userId,@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "size", defaultValue = "10") int size){
        List<ProcessRealtimeProcessnameEntity> list = new ArrayList<>();
        try {
            List<UserEntity> userList = userJpa.findAll();
            for (int i = 0;i<userList.size();i++){
                map_userName.put(userList.get(i).getId(),userList.get(i).getUsername());
            }

            list = mineProcessService.getAllProcessByUserId(userId,page,size);
            for (int i = 0;i<list.size();i++){
                if (list.get(i).getSector().equals("000")){
                    list.get(i).setProcessState("1,流程审批完成");
                } else if (list.get(i).getIsProcessing().equals("1")){
                    list.get(i).setProcessState("0,流程审批中");
                } else if ((list.get(i).getIsProcessing().equals("2"))){
                    list.get(i).setProcessState("2,流程审批被打回");
                } else {
                    list.get(i).setProcessState("3,流程审批被拒绝");
                }
            }
        }catch (Exception e){
            System.out.println("当前用户查询我的申请列表异常");
            return "异常";
        }
        return gson.toJson(list);
    }

    /**
     * 查看流程的详细信息
     * @param taskUuid
     * @param state
     * @return
     */
    @RequestMapping("/getProcessByUUID")
    @ResponseBody
    public String getProcessByUUID(String taskUuid,Long state){
        List list = new ArrayList<>();
        List<RealTimeUserView> list_success = new ArrayList<>();
        List<RealtimeProcessTaskEntity> list_error = new ArrayList<>();
        List<RealtimeProcessTaskEntity> list1 = new ArrayList<>();
        try {
        // 0完成 1未完成 2被打回 3拒绝
            if (state == 0L || state == 1L){
                list_success = mineProcessService.getSuccessByUUID(taskUuid);
                list.add(list_success);
            } else if (state == 2L){
                list_success = mineProcessService.getSuccessByUUID(taskUuid);
                String lastSectorPeople = "00";
                // 不返回到发起人
                if (list_success.size() > 0){
                    lastSectorPeople = list_success.get(list_success.size()-1).getSectorPeople();
                }
                list_error = mineProcessService.getErrorByUUID(taskUuid,lastSectorPeople);
                list.add(list_success);
                list.add(list_error);
            } else {
                // 查出第一条、状态为3的最后一条 和最后一步提交的用户id
                list1 = mineProcessService.getOneAndLast(taskUuid);
                // 只为赋值用户名
                System.out.println(map_userName.get(Long.parseLong(list1.get(0).getUserId())));
                list1.get(0).setRealName(map_userName.get(Long.parseLong(list1.get(0).getUserId())));
                list1.get(1).setRealName(map_userName.get(Long.parseLong(list1.get(2).getSectorPeople())));
                list1.remove(2);
                list.add(list1);
            }

        }catch (Exception e){
            e.printStackTrace();
             System.out.println("异常");
        }
        return gson.toJson(list);
    }

    /**
     * 发起人修改流程信息
     * @param real
     * @return
     */
    @RequestMapping("/updateProcess")
    @ResponseBody
    public String updateProcess(RealtimeProcessTaskEntity real){
        real.setProcessingDate(new Date());
        try {
            // 将该流程其他信息（除发起人的信息）置为空
            mineProcessService.updateOterInfoIsNull(real.getTaskUuid());
            mineProcessService.updateProcessBytaskIndex(real.getTaskUuid(),
                    real.getProcessingDate(),real.getTaskMsg(),real.getSector(),real.getSectorPeople(),real.getAttachment());
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("修改流程信息异常");
            return "0";
        }
        return "1";
    }

    /**
     * 查看流程的动向
     * @param taskUuid
     * @return
     */
    @RequestMapping("/getRealProcessByUUID")
    @ResponseBody
    public String getRealProcessByUUID(String taskUuid){
        List<RealtimeProcessTaskEntity> list = new ArrayList<>();
        try {
            list = mineProcessService.getRealProcessByUUID(taskUuid);
        } catch (Exception e){
            System.out.println("查询流程动向接口异常");
            e.printStackTrace();
        }
        return gson.toJson(list);
    }
}

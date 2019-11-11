package com.allmodel.models.myact.service.impl;


import com.allmodel.models.authority.dao.User_Jpa;
import com.allmodel.models.authority.entity.UserEntity;
import com.allmodel.models.myact.dao.*;
import com.allmodel.models.myact.entity.*;
import com.allmodel.models.myact.service.MineProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description
 * @Author: zhuhaoyu
 * @Date: 2019/10/15 17:17
 */
@Service
public class MineProcessServiceImpl implements MineProcessService {

    @Autowired
    private RealTimeProcessTaskJpa realTimeProcessTaskJpa;
    @Autowired
    private RealTimeUserViewJpa realTimeUserViewJpa;
    @Autowired
    private ProcessRealtimeProcessnameJpa processRealtimeProcessnameJpa;
    @Autowired
    private User_Jpa userJpa;

    @Override
    public List<ProcessRealtimeProcessnameEntity> getAllProcessByUserId(Long userId, Integer page, Integer size) {
        return processRealtimeProcessnameJpa.getAllProcessByUserId(userId, page, size);
    }

    @Override
    public List<RealTimeUserView> getSuccessByUUID(String taskUuid) {
        List<RealTimeUserView> list = realTimeUserViewJpa.findByTaskUuidAndIsJudgeAndIsProcessing(taskUuid, "1", "1");
        for (int i = 0; i < list.size(); i++) {
            UserEntity user = userJpa.findByUserId(Long.parseLong(list.get(i).getUserId()));
            list.get(i).setUserName(user.getUsername());
        }
        return list;
    }

    @Override
    public List<RealtimeProcessTaskEntity> getErrorByUUID(String taskUuid, String lastSectorPeople) {
        List<RealtimeProcessTaskEntity> list = realTimeProcessTaskJpa.findByTaskUuidAndIsJudgeAndIsProcessing(taskUuid, "1", "2");
        List<Long> longList = new ArrayList<>();
        if (lastSectorPeople.equals("00")){
            lastSectorPeople = list.get(0).getUserId();
        }
        longList.add(Long.parseLong(lastSectorPeople));
        longList.add(Long.parseLong(list.get(1).getBackPeople()));
        List<UserEntity> user = userJpa.findByIdIn(longList);
        list.get(0).setRealName(user.get(0).getUsername());
        list.get(1).setRealName(user.get(1).getUsername());
        return list;
    }

    @Override
    public List<RealtimeProcessTaskEntity> getProcessByUUID(String taskUuid) {
        List<RealtimeProcessTaskEntity> list = new ArrayList<>();
        list.add(realTimeProcessTaskJpa.getLastOne(taskUuid));
        return list;
    }

    @Override
    public List<RealtimeProcessTaskEntity> getOneAndLast(String taskUuid) {
        return realTimeProcessTaskJpa.getOneAndLast(taskUuid);
    }

    @Override
    public int updateOterInfoIsNull(String uuid) {
        return realTimeProcessTaskJpa.updateOterInfoIsNull(uuid);
    }

    @Override
    public int updateProcessBytaskIndex(String uuid, Date date, String taskMsg,String sector, String sectorPeople,String attachment) {
        realTimeProcessTaskJpa.updateProcessBytaskIndex2(uuid, "1", date, taskMsg,attachment);
        realTimeProcessTaskJpa.updateProcessBytaskIndex2(uuid, "2", date, taskMsg,attachment);
        return realTimeProcessTaskJpa.updateProcessBytaskIndex3(uuid, "3", date, taskMsg,sector, sectorPeople,attachment);
    }

    @Override
    public List<RealtimeProcessTaskEntity> getRealProcessByUUID(String taskUuid) {
        List<RealtimeProcessTaskEntity> list =  realTimeProcessTaskJpa.findByTaskUuidAndIsJudge(taskUuid, "1");
        Map<Long,String> map_userName = new HashMap<>();
        Map<String,String> map_deptName = new HashMap<>();
        List<UserEntity> userList = userJpa.findAll();
        for (int i = 0;i<userList.size();i++){
            map_userName.put(userList.get(i).getId(),userList.get(i).getUsername());
            map_deptName.put(userList.get(i).getOrganizationNum(),userList.get(i).getOrganizationName());
        }
        for (int i = 0;i<list.size();i++){
            // 为用户名赋值
            list.get(i).setUserId(map_userName.get(Long.parseLong(list.get(i).getUserId())));
            // 部门名称
            if (i == 0 ){
                list.get(i).setSectorName(map_deptName.get(Long.parseLong(list.get(i).getSector())));
                list.get(i).setSectorPeopleName("");
            }else {
                // 获取的部门id 为上一级提交的部门id
                list.get(i).setSectorName(map_deptName.get(Long.parseLong(list.get(i-1).getSector())));
                // 判断上一级的提交人是否为空 并且当前级审批是否审批完成
                if (list.get(i-1).getSectorPeople() != null && list.get(i).getIsProcessing().equals("1")){
                    list.get(i).setSectorPeopleName(map_userName.get(Long.parseLong(list.get(i-1).getSectorPeople())));
                }
            }
        }
        return list;

    }

}

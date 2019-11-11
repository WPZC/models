package com.allmodel.models.myact.service;



import com.allmodel.models.myact.entity.ProcessRealtimeProcessnameEntity;
import com.allmodel.models.myact.entity.RealTimeUserView;
import com.allmodel.models.myact.entity.RealtimeProcessTaskEntity;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author: zhuhaoyu
 * @Date: 2019/10/15 17:15
 */
public interface MineProcessService {

    List<ProcessRealtimeProcessnameEntity> getAllProcessByUserId(Long userId, Integer page, Integer size);

    List<RealTimeUserView> getSuccessByUUID(String taskUuid);
    List<RealtimeProcessTaskEntity> getErrorByUUID(String taskUuid, String lastSectorPeople);

    List<RealtimeProcessTaskEntity> getProcessByUUID(String taskUuid);

    List<RealtimeProcessTaskEntity> getOneAndLast(String taskUuid);

    /**
     * 将该流程其他信息（除发起人的信息）置为空
     */
    int updateOterInfoIsNull(String uuid);

    int updateProcessBytaskIndex(String uuid, Date date, String taskMsg, String sector, String sectorPeople, String attachment);


    List<RealtimeProcessTaskEntity> getRealProcessByUUID(String taskUuid);
}

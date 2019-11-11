package com.allmodel.models.myact.dao;

import com.allmodel.models.myact.entity.RealtimeProcessTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface RealTimeProcessTaskJpa extends JpaRepository<RealtimeProcessTaskEntity,Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM `realtime_process_task` where id IN (SELECT id+1 FROM `realtime_process_task` where sector_people = ?1) OR sector_people =?1")
    List<RealtimeProcessTaskEntity> findAllByUserId(String userid);

    List<RealtimeProcessTaskEntity> findBySectorPeople(String userid);

    /**
     * 根据用户查询uuid
     * @param sectorPeople
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT task_uuid FROM `realtime_process_task` WHERE sector_people = ?1 GROUP BY task_uuid;")
    List<String> findBySectorPeopleList(String sectorPeople);


    /**
     * 根据用户查询未审批完的流程
     * @param sectorPeople
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM `realtime_process_task` where task_uuid IN (SELECT task_uuid FROM `realtime_process_task` WHERE sector_people = ?1 GROUP BY task_uuid) AND is_processing=0 GROUP BY task_uuid")
    List<String> findBySectorPeopleIsProcessList(String sectorPeople);


    /**
     * 根据用户查询未审批的完整流程
     * @param sectorPeople
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM `realtime_process_task` where task_uuid IN (SELECT task_uuid FROM `realtime_process_task` where task_uuid IN (SELECT task_uuid FROM `realtime_process_task` WHERE sector_people = ?1 GROUP BY task_uuid) AND is_processing=0 GROUP BY task_uuid)")
    List<String> findBySectorPeopleIsProcess0List(String sectorPeople);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE realtime_process_task SET is_processing = '2',processing_date=?3,back_people=?4 WHERE task_uuid = ?1 AND task_index IN (?2);")
    int updateByTaskUuidAndTaskIndexIn(String taskUuid, List<String> indexs, Date date, String backPeople);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE realtime_process_task SET sector = '000',opinions_failure = ?2,is_processing=3 WHERE task_uuid = ?1 AND task_index IN(SELECT a.task_index FROM (SELECT task_index,task_uuid FROM `realtime_process_task`" +
            " WHERE task_index!=0 AND task_uuid = ?1 ORDER BY id DESC) a GROUP BY task_uuid)")
    int updateBySector(String taskUuid, String msg);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE realtime_process_task SET is_processing = '3',processing_date=?2, WHERE task_uuid = ?1 AND task_index IN (?3)")
    int updateByTaskUuid(String taskUuid, Date date, List<String> indexs);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE realtime_process_task SET is_processing = '2',opinions_failure=?3,processing_date=?4,back_people=?5 WHERE task_uuid = ?1 AND task_index IN (?2);")
    int updateByTaskUuidAndTaskIndexIn(String taskUuid, List<String> indexs, String params, Date date, String backPeople);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE realtime_process_task SET is_processing = '3',opinions_failure=?2,processing_date=?3 WHERE task_uuid = ?1 AND task_index IN (?4)")
    int updateByTaskUuid(String taskUuid, String params, Date date, List<String> indexs);

    /**
     * 根据用户id查属于用户的流程
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM process_realtime_processname WHERE id IN" +
            "(SELECT MAX(id) FROM process_realtime_processname " +
            "WHERE user_id = ?1 AND is_processing !=0 GROUP BY task_uuid) LIMIT ?2,?3")
    List<RealtimeProcessTaskEntity> getAllProcessByUserId(Long userId, Integer page, Integer size);


    /**
     * 根据uuid，isJudge、isProcessing不等于1 查询
     * @param taskUuid
     * @param isJudge
     * @param isProcessing
     * @return
     */
    @Query(nativeQuery = true,value = "(select * from realtime_process_task where task_uuid=?1 and is_judge=?2 and is_processing =?3 GROUP BY id LIMIT 1) " +
            "UNION " +
            "(select * from realtime_process_task where task_uuid=?1 and is_judge=?2 and is_processing = ?3 GROUP BY id DESC LIMIT 1) ")
    List<RealtimeProcessTaskEntity> findByTaskUuidAndIsJudgeAndIsProcessing(String taskUuid, String isJudge, String isProcessing);


    @Query(nativeQuery = true,value = "SELECT * FROM process_realtime_processname WHERE id =(SELECT MAX(id) FROM process_realtime_processname " +
            "            WHERE task_uuid = ?1 AND is_processing !=0)")
    RealtimeProcessTaskEntity getLastOne(String taskUuid);

    @Query(nativeQuery = true,value = "(select * FROM realtime_process_task WHERE task_uuid = ?1 ORDER BY id LIMIT 1) " +
            "UNION " +
            "(SELECT * FROM realtime_process_task WHERE id =(SELECT MAX(id) FROM process_realtime_processname " +
            "            WHERE task_uuid = ?1 AND is_processing !=0)) " +
            "UNION " +
            "(SELECT * FROM realtime_process_task WHERE task_uuid = ?1 and sector_people != '' GROUP BY id DESC LIMIT 1)")
    List<RealtimeProcessTaskEntity> getOneAndLast(String taskUuid);


    /**
     * 将该流程其他信息（除发起人的信息）置为空
     * @param uuid
     * @return
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE realtime_process_task SET opinions_failure = NULL ," +
            " back_people = NULL, task_msg = NULL, is_processing = 0 WHERE task_uuid = ?1 AND task_index >3 ")
    int updateOterInfoIsNull(String uuid);

    /**
     * 修改流程模板信息字段
     * @param uuid
     * @param taskIndex
     * @param date
     * @param taskMsg
     * @return
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE realtime_process_task SET is_processing = '1', processing_date = ?3, task_msg  = ?4, attachment = ?5 " +
            "where task_index = ?2 AND task_uuid = ?1 ")
    int updateProcessBytaskIndex2(String uuid, String taskIndex, Date date, String taskMsg, String attachment);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "UPDATE realtime_process_task SET is_processing = '1', processing_date = ?3, task_msg  = ?4, sector = ?5,sector_people = ?6, attachment = ?7" +
            " where task_index = ?2 AND task_uuid = ?1 ")
    int updateProcessBytaskIndex3(String uuid, String taskIndex, Date date, String taskMsg, String sector, String sectorPeople, String attachment);


    /**
     * 根据uuid、isJudge查询
     * @param taskUuid
     * @param isJudge
     * @return
     */
    List<RealtimeProcessTaskEntity> findByTaskUuidAndIsJudge(String taskUuid, String isJudge);
}

package com.allmodel.models.myact.dao;

import com.allmodel.models.myact.entity.ProcessRealtimeProcessnameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author WQY
 * @Date 2019/10/11 17:38
 * @Version 1.0
 */
public interface ProcessRealtimeProcessnameJpa extends JpaRepository<ProcessRealtimeProcessnameEntity,Integer> {


    @Query(nativeQuery = true,value = "SELECT * FROM `process_realtime_processname` where id IN (SELECT id+1 FROM `realtime_process_task` where sector_people = ?1) OR sector_people =?1")
    List<ProcessRealtimeProcessnameEntity> findAllByUserId(String userid);

    List<ProcessRealtimeProcessnameEntity> findBySectorPeople(String userid);


    /**
     * 根据用户查询未审批完的流程
     * @param sectorPeople
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM process_realtime_processname where task_uuid IN (SELECT task_uuid FROM process_realtime_processname WHERE sector_people = ?1 GROUP BY task_uuid) AND is_processing=0 GROUP BY task_uuid")
    List<ProcessRealtimeProcessnameEntity> findBySectorPeopleIsProcessList(String sectorPeople);


    /**
     * 根据用户查询未审批的完整流程
     * @param sectorPeople
     * @return
     */
    //@Query(nativeQuery = true,value = "SELECT * FROM process_realtime_processname where task_uuid IN (SELECT task_uuid FROM process_realtime_processname where task_uuid IN (SELECT task_uuid FROM process_realtime_processname WHERE sector_people = ?1 GROUP BY task_uuid) AND (is_processing=0 OR is_processing=2) GROUP BY task_uuid)")
    @Query(nativeQuery = true,value = "SELECT * FROM process_realtime_processname WHERE task_uuid IN(SELECT a.task_uuid FROM(SELECT * FROM" +
            " (SELECT * FROM process_realtime_processname WHERE task_uuid IN(SELECT task_uuid FROM `process_realtime_processname` WHERE sector_people = ?1) " +
            "AND task_index!='0' ORDER BY id DESC) r GROUP BY task_uuid HAVING sector!='000') a) AND task_index!='0';")
    List<ProcessRealtimeProcessnameEntity> findBySectorPeopleIsProcess0List(String sectorPeople);

    /**
     * 根据taskUuid和taskIndex查询
     * @param taskUuid
     * @param taskIndex
     * @return
     */
    ProcessRealtimeProcessnameEntity findByTaskUuidAndTaskIndex(String taskUuid, String taskIndex);

    /**
     * 根据taskuuid和index查询对应的审批信息
     * @return
     */
    List<ProcessRealtimeProcessnameEntity> findByTaskUuidAndTaskIndexIn(String taskUuid, List<String> taskIndexs);


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
    List<ProcessRealtimeProcessnameEntity> getAllProcessByUserId(Long userId, Integer page, Integer size);


    /**
     * 根据用户id查属于用户的流程
     * @param taskUuid
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM `process_realtime_processname` WHERE (is_processing=1 AND is_judge = 1 AND task_index !=1 AND task_uuid = ?1) or (task_index=2 AND task_uuid = ?1)")
    List<ProcessRealtimeProcessnameEntity> findAuditFailed2(String taskUuid);




    @Query(nativeQuery = true,value = "SELECT * FROM `process_realtime_processname` WHERE is_processing=1 AND task_index !=1 AND task_uuid = ?1")
    List<ProcessRealtimeProcessnameEntity> findOverProcess(String taskUuid);

    @Query(nativeQuery = true,value = "SELECT * FROM (SELECT * FROM process_realtime_processname d" +
            " WHERE task_uuid IN(SELECT task_uuid FROM `process_realtime_processname` WHERE sector_people = ?1)" +
            " AND task_index!=0 ORDER BY id DESC) a WHERE sector = '000' GROUP BY a.task_uuid")
    List<ProcessRealtimeProcessnameEntity> getOverProcess(String userId);

    @Query(nativeQuery = true,value = "SELECT * FROM `process_realtime_processname` WHERE sector = '000' AND user_id = ?1")
    List<ProcessRealtimeProcessnameEntity> getOverProcess2(String userId);

    @Query(nativeQuery = true,value = "SELECT * FROM process_realtime_processname WHERE task_uuid" +
            " IN(SELECT a.task_uuid FROM(SELECT * FROM (SELECT * FROM realtime_process_task" +
            " WHERE task_uuid IN(SELECT task_uuid FROM `process_realtime_processname` WHERE sector_people = ?1)" +
            " AND task_index!='0' ORDER BY id DESC) r GROUP BY task_uuid HAVING sector!='000') a) AND task_index!='0';")
    List<ProcessRealtimeProcessnameEntity> getDoneProcess(String userId);



}

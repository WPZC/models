package com.allmodel.models.myact.dao;

import com.allmodel.models.myact.entity.ProcessTemplateParamsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessTemplateParamsJpa extends JpaRepository<ProcessTemplateParamsView,Integer> {


    List<ProcessTemplateParamsView> findByProcessUuidIndex(String uuid);

    /**
     * 根据uuid和taskindex查询
     * @param uuid
     * @param taskindexs
     * @return
     */
    List<ProcessTemplateParamsView> findByProcessUuidIndexAndProcessTaskIndexIn(String uuid, List<String> taskindexs);

    /**
     * 根据uuid和taskindex查询
     * @param uuid
     * @param taskindex
     * @return
     */
    ProcessTemplateParamsView findByProcessUuidIndexAndProcessTaskIndex(String uuid, String taskindex);

    @Query(nativeQuery = true,value = "SELECT * FROM process_template_params WHERE process_uuid_index = ?1 AND is_judge !=0 ORDER BY id LIMIT 0,1;")
    ProcessTemplateParamsView findByUuidAndSector(String uuid);

}

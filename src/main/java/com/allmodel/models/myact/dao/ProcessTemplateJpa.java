package com.allmodel.models.myact.dao;

import com.allmodel.models.myact.entity.ProcessTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcessTemplateJpa extends JpaRepository<ProcessTemplateEntity,Integer> {

    List<ProcessTemplateEntity> findByProcessUuid(String uuid);

    @Query(nativeQuery = true,value = "SELECT * FROM process_template WHERE process_uuid = ?1 AND is_judge !=0 ORDER BY id LIMIT 0,1;")
    ProcessTemplateEntity findByUuidAndSector(String uuid);

}

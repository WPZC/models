package com.allmodel.models.myact.dao;



import com.allmodel.models.myact.entity.ProcessParamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessParamsJpa extends JpaRepository<ProcessParamsEntity,Integer> {


    /**
     * 根据uuid查询
     * @param uuid
     * @return
     */
    List<ProcessParamsEntity> findByProcessUuidIndex(String uuid);
}

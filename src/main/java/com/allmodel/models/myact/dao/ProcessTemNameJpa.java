package com.allmodel.models.myact.dao;


import com.allmodel.models.myact.entity.ProcessTemplateNameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessTemNameJpa extends JpaRepository<ProcessTemplateNameEntity,Integer> {

    /**
     * 根据流程名称查找
     * @param processName
     * @return
     */
    ProcessTemplateNameEntity findByProcessName(String processName);

}

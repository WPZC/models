package com.allmodel.models.myact.service;


import com.allmodel.models.myact.entity.ProcessParamsEntity;

public interface ProcessParamsService {

    /**
     * 根据UUID查询
     * @param uuid
     * @return
     */
    ProcessParamsEntity findByUuid(String uuid);

}

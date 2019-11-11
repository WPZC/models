package com.allmodel.models.myact.service.impl;

import com.allmodel.models.myact.dao.ProcessParamsJpa;
import com.allmodel.models.myact.entity.ProcessParamsEntity;
import com.allmodel.models.myact.service.ProcessParamsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessParamsServiceImpl implements ProcessParamsService {

    @Autowired
    private ProcessParamsJpa processParamsJpa;

    private Gson gson = new Gson();

    @Override
    public ProcessParamsEntity findByUuid(String uuid) {

        List<ProcessParamsEntity> rs = processParamsJpa.findByProcessUuidIndex(uuid);

        ProcessParamsEntity processParamsEntity = new ProcessParamsEntity();
        for (int i = 0,num = rs.size();i<num;i++){
            //如果参数不为空则返回
            if(!(rs.get(i).getTaskIndexParameters().equals("")||rs.get(i).getTaskIndexParameters()==null||rs.get(i).getTaskIndexParameters().equals("[]"))){
                processParamsEntity.setTaskIndexParameters(rs.get(i).getTaskIndexParameters());
                processParamsEntity.setProcessUuidIndex(rs.get(i).getProcessUuidIndex());
                processParamsEntity.setProcessTaskIndex(rs.get(i).getProcessTaskIndex());
                return processParamsEntity;
            }
        }

        return processParamsEntity;
    }
}

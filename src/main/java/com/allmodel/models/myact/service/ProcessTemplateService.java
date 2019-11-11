package com.allmodel.models.myact.service;



import com.allmodel.models.myact.entity.ProcessTemplateNameEntity;
import com.allmodel.models.myact.entity.ProcessTemplateParamsView;

import java.util.List;

public interface ProcessTemplateService {

    String addProcessTemplate(String json, String processName);

    List<ProcessTemplateNameEntity> getProTemNames();

    ProcessTemplateParamsView getSectorPeople(String uuid);
}

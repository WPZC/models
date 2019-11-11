package com.allmodel.models.myact.service.impl;


import com.allmodel.models.myact.dao.ProcessParamsJpa;
import com.allmodel.models.myact.dao.ProcessTemNameJpa;
import com.allmodel.models.myact.dao.ProcessTemplateJpa;
import com.allmodel.models.myact.dao.ProcessTemplateParamsJpa;
import com.allmodel.models.myact.entity.ProcessParamsEntity;
import com.allmodel.models.myact.entity.ProcessTemplateEntity;
import com.allmodel.models.myact.entity.ProcessTemplateNameEntity;
import com.allmodel.models.myact.entity.ProcessTemplateParamsView;
import com.allmodel.models.myact.entity.view.ProcessTemplateView;
import com.allmodel.models.myact.service.ProcessTemplateService;
import com.allmodel.models.myact.utils.ActUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProcessTemplateServiceImpl implements ProcessTemplateService {

    @Autowired
    private ProcessTemplateJpa processTemplateJpa;
    @Autowired
    private ProcessParamsJpa processParamsJpa;
    @Autowired
    private ProcessTemNameJpa processTemNameJpa;
    @Autowired
    private ProcessTemplateParamsJpa processTemplateParamsJpa;


    private final Gson gson = new Gson();

    @Override
    @Transactional
    public String addProcessTemplate(String json,String processName) {


        if (processName.equals("")||processName==null){
            return "流程名称不能为空";
        }

        ProcessTemplateNameEntity templateNameEntity = processTemNameJpa.findByProcessName(processName);

        if(templateNameEntity!=null){
            return "流程已存在";
        }

        //解析
        List<ProcessTemplateView> jsonInstance = gson.fromJson(json,new TypeToken<List<ProcessTemplateView>>(){}.getType());

        //流程模板
        List<ProcessTemplateEntity> processTemplates = new ArrayList<ProcessTemplateEntity>();
        //字段模板
        List<ProcessParamsEntity> processParamss = new ArrayList<ProcessParamsEntity>();

        //生成流程UUID
        String uuid = ActUtils.getUUID();
        //取出数据
        for (int i = 0,num=jsonInstance.size();i<num;i++){
            ProcessTemplateView view = jsonInstance.get(i);
            //创建当前任务记录
            ProcessTemplateEntity pte = new ProcessTemplateEntity();
            pte.setIsJudge(view.getIsJudge());
            pte.setProcessUuid(uuid);
            pte.setTaskIndex(view.getTaskIndex());
            pte.setTaskName(view.getTaskName());
            pte.setProcessJumpBack(view.getProcessJumpBack());
            pte.setSector(view.getSector());
            pte.setFunction(view.getFunction());
            processTemplates.add(pte);

            //生成字段表字段json
//            HashMap<String,String> map = new HashMap<>();
//            for (int y = 0,count = view.getFields().size();y<count;y++){
//                List<String> list = view.getFields().get(y);
//                map.put(list.get(0),list.get(1));
//            }
            //创建字段记录
            ProcessParamsEntity ppe = new ProcessParamsEntity();
            ppe.setProcessTaskIndex(view.getTaskIndex());
            ppe.setProcessUuidIndex(uuid);
            ppe.setTaskIndexParameters(gson.toJson(view.getFields()));
            processParamss.add(ppe);
        }

        try {
            //保存流程模板
            processTemplateJpa.saveAll(processTemplates);
            //保存字段模板
            processParamsJpa.saveAll(processParamss);

            templateNameEntity = new ProcessTemplateNameEntity();
            templateNameEntity.setProcessName(processName);
            templateNameEntity.setProcessCreateDate(new Date());
            templateNameEntity.setProcessUuid(uuid);
            //设置流程模板名称
            processTemNameJpa.save(templateNameEntity);
            return "保存成功";
        }catch (Exception e){
            e.printStackTrace();
            return "添加失败";

        }
    }

    @Override
    public List<ProcessTemplateNameEntity> getProTemNames() {
        return processTemNameJpa.findAll();
    }

    @Override
    public ProcessTemplateParamsView getSectorPeople(String uuid) {

        ProcessTemplateParamsView processTemplateEntity = processTemplateParamsJpa.findByUuidAndSector(uuid);

        return processTemplateEntity;
    }
}

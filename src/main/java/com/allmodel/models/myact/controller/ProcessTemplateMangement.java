package com.allmodel.models.myact.controller;

import com.allmodel.models.myact.entity.view.OutView;
import com.allmodel.models.myact.service.ProcessTemplateService;
import com.allmodel.models.myact.service.RealTimeProcessTaskService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 流程模板管理
 * @Author WQY
 * @Date 2019/10/8 10:06
 * @Version 1.0
 */
@Controller
@RequestMapping("/pt")
@Api(value = "流程模板管理")
public class ProcessTemplateMangement {

    @Autowired
    private ProcessTemplateService processTemplateService;

    private Gson gson = new Gson();

    /**
     * 创建模板
     * @param processArray
     * @param processName
     * @return
     */
    @RequestMapping("/createtemplate")
    @ResponseBody
    @ApiOperation(value = "创建流程模板")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "模板及参数，json格式",name = "processArray",required = true,dataType = "String"),
            @ApiImplicitParam(value = "参数数据，json格式",name = "processName",required = true,dataType = "String"),
    })
    public OutView<String> test(String processArray,String processName){

        OutView outView = new OutView();
        String rs;
        try {
            rs = processTemplateService.addProcessTemplate(processArray,processName);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(0);
            outView.setMsg("出现异常");
            e.printStackTrace();
        }
        return outView;
    }



}

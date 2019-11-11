package com.allmodel.models.myact.controller;

import com.allmodel.models.myact.service.ProcessTemplateService;
import com.allmodel.models.myact.service.RealTimeProcessTaskService;
import com.allmodel.models.myact.utils.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TestController {

    @Autowired
    private ProcessTemplateService processTemplateService;
    @Autowired
    private RealTimeProcessTaskService realTimeProcessTaskService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(String json){
        //processTemplateService.addProcessTemplate(json);
        return "SUCCESS!";
    }

    @RequestMapping("/chiocTemplate")
    @ResponseBody
    public String chiocTemplate(){
        realTimeProcessTaskService.choiceTemplate("2cef1499-182f-4041-88b5-9917c21f6844");
        return "SUCCESS!";
    }

    @RequestMapping("/testhttp")
    @ResponseBody
    public String testhttp(){
        String rs = HttpRequest.sendPost("http://192.168.1.99:8199/api/getDeptName","");
        System.out.println(rs);
        return "SUCCESS!";
    }
}

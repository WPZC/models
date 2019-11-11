package com.allmodel.models.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author WQY
 * @Date 2019/11/5 15:43
 * @Version 1.0
 */
@RestController(value = "/test")
@Api(value = "测试模块接口")
public class Test {

    @ApiOperation(value = "测试接口")
    @RequestMapping("/test2")
    public String getTestMsg(){
        return "测试完成";
    }

}

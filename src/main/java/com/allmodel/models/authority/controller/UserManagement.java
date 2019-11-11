package com.allmodel.models.authority.controller;


import com.allmodel.models.authority.entity.OrganizationEntity;
import com.allmodel.models.authority.entity.UserEntity;
import com.allmodel.models.authority.entity.view.OutView;
import com.allmodel.models.authority.service.UserManagementService;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author WQY
 * @date 2019/9/10 14:57
 */
@Controller
@RequestMapping("/user")
@Api(value = "用户组织机构管理")
public class UserManagement {

    private Gson gson = new Gson();

    @Autowired
    private UserManagementService userManagementService;

    /**
     * 添加用户
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/adduser",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加用户")
    public OutView<String> addUser(@ApiParam(name = "userEntity",value = "传入json格式",required = true) UserEntity userEntity){

        String rs = userManagementService.addUser(userEntity);

        OutView outView = new OutView();

        try {
            outView.setState(0);
            outView.setMsg(rs);
            return outView;
        }catch (Exception e){
            outView.setState(1);
            outView.setMsg(rs);
            return outView;
        }
    }

    /**
     * 获取用户列表
     * @param orgnum
     * @return
     */
    @RequestMapping(value = "/getorglist",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取用户列表")
    @ApiImplicitParam(value = "组织机构编码",name = "orgnum",dataType = "String",required = true)
    public OutView<List<UserEntity>> getOrgList(String orgnum){
        OutView outView = new OutView();
        try {
            List<UserEntity> rs = userManagementService.getuserlist(orgnum+"%");
            outView.setState(0);
            outView.setMsg(rs);
            return outView;
        }catch (Exception e){
            outView.setState(1);
            outView.setMsg("获取失败");
            return outView;
        }
    }


    /**
     * 删除用户
     * @param username
     * @return
     */
    @RequestMapping(value = "/deleteuser",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "当前用户名称",name = "username",dataType = "String"),
            @ApiImplicitParam(value = "被删除用户名称",name = "deleteUsername",dataType = "String")
    })
    public OutView<String> deleteUser(String username,String deleteUsername){

        OutView outView = new OutView();

        //判断是否是当前用户
        if(deleteUsername.equals(username)){
            outView.setState(0);
            outView.setMsg("请勿删除自己");
            return outView;
        }

        String rs = "";
        try {
            rs=userManagementService.deleteUser(deleteUsername);
            outView.setState(0);
            outView.setMsg(rs);
        }catch (Exception e){
            outView.setState(1);
            outView.setMsg(rs);
            e.printStackTrace();
        }

        return outView;
    }



    /**
     * 获取组织机构列表
     * @return
     */
    @RequestMapping(value = "/organizationlist",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "获取组织机构列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名称",name = "username",dataType = "String",required = false),
            @ApiImplicitParam(value = "组织机构代码",name = "orgCode",dataType = "String",required = true)
    })
    public OutView<List<HashMap<String,Object>>> organizationList(String username,String orgCode){

        OutView outView = new OutView();

        try {
            List<HashMap<String,Object>> map = userManagementService.findByOrganizationList(orgCode);
            outView.setState(0);
            outView.setMsg(map);
        }catch (Exception e){
            outView.setState(1);
            outView.setMsg("获取组织机构列表失败");
            e.printStackTrace();
        }
        return outView;
    }


    /**
     * 获取平铺组织机构列表
     * @return
     */
    @RequestMapping(value = "/getorglistpp",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取平铺组织机构列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名名称",name = "username"),
            @ApiImplicitParam(value = "组织机构代码",name = "orgCode",required = true)
    })
    public OutView<List<OrganizationEntity>> getorglistpp(String username,String orgCode){

        OutView outView = new OutView();

        try {
            List<OrganizationEntity> ycOrganizationEntities = userManagementService.getorglist(orgCode);
            outView.setState(0);
            outView.setMsg(ycOrganizationEntities);
        }catch (Exception e){
            outView.setState(1);
            outView.setMsg("获取组织机构列表失败");
            e.printStackTrace();
        }
        return outView;
    }

    /**
     * 添加组织机构
     * @return
     */
    @RequestMapping(value = "/addorganization",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加组织机构")
    public OutView<String> addOrganization(@ApiParam(name = "organizationInput",value = "传入json结构") OrganizationEntity organizationInput){

        OutView outView = new OutView();
        String rs = null;
        try {
            rs = userManagementService.addOrganization(organizationInput);
            outView.setState(0);
            outView.setMsg(rs);
        } catch (Exception e) {
            outView.setState(1);
            outView.setMsg("出现异常");
        }

        return outView;
    }


    /**
     * 删除组织机构
     * @param OrgNum
     * @param userOrgNum
     * @return
     */
    @RequestMapping(value = "/deleteorg",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除组织机构")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "被删除组织机构代码",name = "OrgNum",required = true,dataType = "String"),
            @ApiImplicitParam(value = "被删除组织机构代码",name = "userOrgNum",required = true,dataType = "String")
    })
    public OutView<String> deleteOrg(String OrgNum,String userOrgNum){

        OutView outView = new OutView();
        if(OrgNum.length()==userOrgNum.length()){
            outView.setState(0);
            outView.setMsg("操作失败");
            return outView;
        }
        String rs = "";
        try {
            rs = userManagementService.deleteOrg(OrgNum+"%");
            outView.setState(0);
            outView.setMsg(rs);
        } catch (Exception e) {
            outView.setState(0);
            outView.setMsg(rs);
            e.printStackTrace();
        }
        return outView;
    }


}

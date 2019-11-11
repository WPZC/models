package com.allmodel.models.authority.service;

import com.allmodel.models.authority.entity.OrganizationEntity;
import com.allmodel.models.authority.entity.UserEntity;

import java.util.HashMap;
import java.util.List;

/**
 * @author WQY
 * @date 2019/9/10 15:14
 */
public interface UserManagementService {


    /**
     * 添加用户
     * @param userEntity
     * @return
     */
    String addUser(UserEntity userEntity);

    /**
     * 获取用户列表
     * @param orgnum
     * @return
     */
    List<UserEntity> getuserlist(String orgnum);

    /**
     * 获取平铺组织机构列表
     * @param orgnum
     * @return
     */
    List<OrganizationEntity> getorglist(String orgnum);



    /**
     * 删除用户
     * @param username
     * @return
     */
    String deleteUser(String username);

    /**
     * 获取组织机构信息
     * @return
     */
    List<HashMap<String,Object>> findByOrganizationList(String orgNum);


    /**
     * 添加组织机构
     * @param organizationEntity
     * @return
     */
    String addOrganization(OrganizationEntity organizationEntity) throws Exception;

    /**
     * 删除组织机构
     * @param orgNum
     * @return
     */
    String deleteOrg(String orgNum) throws Exception;

}

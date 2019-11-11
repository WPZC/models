package com.allmodel.models.authority.dao;

import com.allmodel.models.authority.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author WQY
 * @date 2019/9/9 15:28
 */
public interface User_Jpa extends JpaRepository<UserEntity,Integer> {

    UserEntity findByUsernameAndPassword(String username, String password);

    UserEntity findByUsername(String username);

    /**
     * 删除用户
     * @param username
     * @return
     */
    //注解用于提交事务，若没有带上这句，会报事务异常提示。
    @Transactional
    //自动清除实体里保存的数据。
    @Modifying(clearAutomatically = true)
    int deleteByUsername(String username);


    /**
     * 按组织机构删除用户
     * @param organization_num
     * @return
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "DELETE FROM user WHERE organization_num LIKE ?1")
    int deleteLikeOrganizationName(String organization_num);


    /**
     * 根据组织机构查询对应用户列表
     * @param organization_num
     * @return
     */
    @Query(nativeQuery = true,value = "SELECT * FROM user WHERE organization_num LIKE ?1 ")
    List<UserEntity> findByOrganizationNumList(String organization_num);

    @Query(value = "select a from UserEntity a where a.id = ?1")
    UserEntity findByUserId(long parseLong);

    List<UserEntity> findByIdIn(List<Long> longList);
}

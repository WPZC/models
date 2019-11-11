package com.allmodel.models.authority.dao;

import com.allmodel.models.authority.entity.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author WQY
 * @date 2019/9/10 16:11
 */
public interface Organization_Jpa extends JpaRepository<OrganizationEntity,Integer> {

    @Query(nativeQuery = true,value = "select * from organization where  organization_serial_num like ?1")
    List<OrganizationEntity> findLikeOrganizationName(String organization_serial_num);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,value = "DELETE FROM organization WHERE organization_serial_num LIKE ?1")
    int deleteLikeOrganizationName(String organization_num);
}

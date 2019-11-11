package com.allmodel.models.myact.dao;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @Author WQY
 * @Date 2019/10/26 16:28
 * @Version 1.0
 */
@Component
public class ProcessingFunctionJpa {


    @PersistenceContext
    EntityManager entityManager;

    /**
     *
     * @param sql
     * @return
     */
    public Object processFunction(String sql){

        Query query = entityManager.createNativeQuery(sql);
        Object rs = query.getSingleResult();
        System.out.println(rs);
        return rs;
    }

    /**
     * @param sql
     * @return
     */
    public Object processFunctionList(String sql){

        Query query = entityManager.createNativeQuery(sql);
        Object rs = query.getResultList();
        System.out.println(rs);
        return rs;
    }

    /**
     * @param sql
     * @return
     */
    public int processFunctionUpdateOrDelete(String sql){

        Query query = entityManager.createNativeQuery(sql);
        int rs  = query.executeUpdate();
        System.out.println(rs);
        return rs;
    }


}

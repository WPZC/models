package com.allmodel.models.test;

import com.allmodel.models.authority.AuthorityModel;

/**
 * @Author WQY
 * @Date 2019/11/6 9:52
 * @Version 1.0
 */
public class Test1 {

    public static void main(String[] args){

        AuthorityModel model = new AuthorityModel();

        model.setModelType("权限1");

        System.out.println(model.getModelType());


    }

}

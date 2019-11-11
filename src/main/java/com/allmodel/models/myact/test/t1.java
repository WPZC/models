package com.allmodel.models.myact.test;


import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class t1 {

    private static Gson gson = new Gson();


    public static void main(String[] args){
        String params = "[{\"时间\":\"time\",\"报销部门\":\"bumen\",\"摘要\":\"zhaiyao\",\"报销项目\":\"pij\",\"备注\":\"msg\",\"报销人\":\"name\",\"金额\":\"math\"},{\"time\":\"2019/10/15\",\"bumen\":\"技术\",\"zhaiyao\":\"250x\",\"pij\":\"买材料\",\"msg\":\"笔记本00\",\"name\":\"郜某某\",\"math\":\"50000\"}]";

        float f = 0.1234f;
        double d = 0.1234;

        List<LinkedTreeMap> list = gsonToList(params, LinkedTreeMap.class);


        System.out.println(list.get(1).get("time"));
        System.out.println(gsonToList(params, HashMap.class));

    }


    /**
     * json字符串转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        if (gson != null) {
            // 根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

}

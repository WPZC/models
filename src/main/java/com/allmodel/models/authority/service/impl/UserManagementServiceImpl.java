package com.allmodel.models.authority.service.impl;

import com.allmodel.models.authority.dao.Organization_Jpa;
import com.allmodel.models.authority.dao.User_Jpa;
import com.allmodel.models.authority.entity.OrganizationEntity;
import com.allmodel.models.authority.entity.UserEntity;
import com.allmodel.models.authority.service.UserManagementService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author WQY
 * @Date 2019/11/5 9:27
 * @Version 1.0
 */
@Service
public class UserManagementServiceImpl implements UserManagementService {


    @Autowired
    private User_Jpa user_jpa;
    @Autowired
    private Organization_Jpa organization_jpa;


    @Override
    @Transactional
    public String addUser(UserEntity userEntity) {
        UserEntity entity = user_jpa.findByUsername(userEntity.getUsername());

        if(entity==null){
            userEntity.setSavetime(new Date());
            user_jpa.save(userEntity);
            return "添加成功";
        }else{
            return "用户名已存在";
        }
    }

    @Override
    public List<UserEntity> getuserlist(String orgnum) {

        List<UserEntity> list = user_jpa.findByOrganizationNumList(orgnum);

        return list;
    }

    @Override
    public List<OrganizationEntity> getorglist(String orgnum) {


        List<OrganizationEntity> organizationEntities = organization_jpa.findLikeOrganizationName(orgnum+"0%");

        return organizationEntities;
    }

    @Override
    @Transactional
    public String deleteUser(String username) {
        int rs = user_jpa.deleteByUsername(username);

        if(rs==1){
            return "删除成功";
        }else{
            return "删除失败";
        }
    }

    List<HashMap<String,Object>> rsList = new ArrayList<HashMap<String, Object>>();
    private List<HashMap<String,Object>> outRs = null;

    @Override
    public List<HashMap<String, Object>> findByOrganizationList(String orgNum) {

        List<OrganizationEntity> organizationEntities = organization_jpa.findAll();
        LinkedHashMap<String,Object> map = getOrganizationListGson(organizationEntities).get("1000");
        rsList = new ArrayList<HashMap<String, Object>>();
        HashMap<String,Object> rs = new HashMap<String, Object>();
        outRs = new ArrayList<HashMap<String, Object>>();
        //调用递归
        dg2(map,orgNum);
        return outRs;
    }

    private LinkedHashMap<String,LinkedHashMap<String,Object>> getOrganizationListGson(List<OrganizationEntity> organizationEntities){

        //查看最大梯度等级
        int maxTd = 0;
        int serialnum = 4;
        for (int i = 0,num=organizationEntities.size();i<num;i++){
            if(organizationEntities.get(i).getOrganizationSerialNum().length()>serialnum){
                serialnum = organizationEntities.get(i).getOrganizationSerialNum().length();
            }
        }

        //分出梯度
        maxTd = serialnum/4;
        TreeMap<Integer,List<OrganizationEntity>> map = new TreeMap<Integer, List<OrganizationEntity>>();
        for (int i = 1;i<=maxTd;i++){
            List<OrganizationEntity> listtd = new ArrayList<OrganizationEntity>();
            for (int y = 0,num=organizationEntities.size();y<num;y++){
                if(organizationEntities.get(y).getOrganizationSerialNum().length()==i*4){
                    listtd.add(organizationEntities.get(y));
                }
            }
            map.put(i,listtd);
        }
        //对梯度进行链表关联，分出主干和树杈
        //利用对象指针和堆栈特性
        List<OrganizationEntity> entityList = null;
        List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
        LinkedHashMap<String,Object> linkedHashMap = new LinkedHashMap<String, Object>();
        LinkedHashMap<String,LinkedHashMap<String,Object>> treemap = new LinkedHashMap<String, LinkedHashMap<String,Object>>();
        for (Integer key:map.keySet()){
            entityList = map.get(key);
            for (int i = 0;i<entityList.size();i++){
                String serialNum = entityList.get(i).getOrganizationSerialNum();
                String ornName = entityList.get(i).getOrganizationName();
                if(linkedHashMap.size()==0){
                    linkedHashMap.put("id",serialNum);
                    linkedHashMap.put("label",ornName);
                    treemap.put(serialNum,linkedHashMap);
                }else{
                    linkedHashMap = new LinkedHashMap<String, Object>();
                    linkedHashMap.put("id",serialNum);
                    linkedHashMap.put("label",ornName);
                    System.out.println(serialNum.substring(0,serialNum.length()-4));

                    if(treemap.get(serialNum.substring(0,serialNum.length()-4))!=null){

                        treemap.get(serialNum.substring(0,serialNum.length()-4)).
                                put(serialNum,linkedHashMap);
                        treemap.put(serialNum,linkedHashMap);

                    }else{
                        treemap.put(serialNum,linkedHashMap);
                    }

                }
            }
        }
        return treemap;
    }


    private HashMap<String,Object> dg2(LinkedHashMap<String,Object> map,String orgNum){

        //返回值
        HashMap<String,Object> rsout = new HashMap<String, Object>();
        //存放每一级的list
        List<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        //存放当前递归曾的id和label，最底层的时候只有这个，然后从倒数第二层开始往dg2map中添加children
        //并将上一级的list添加到children里
        HashMap<String,Object> dg2Map = new HashMap<String, Object>();
        //从最上级开始遍历
        for (String key:map.keySet()){
            if(key.contains("1000")){
                //调用递归，在最后一级开始往会返回数据
                HashMap<String,Object> dg2Map2 = dg2((LinkedHashMap<String,Object>)map.get(key),orgNum);
                //由于当前层可能会有多个兄弟，所以要判断children是否为空
                if(dg2Map.get("children")==null){
                    list.add((HashMap<String,String>)dg2Map2.get("dg2Map"));
                    dg2Map.put("children",list);
                }else{
                    //不为null则说明已经存在直接把儿子添加进来就行
                    ((List<HashMap<String,String>>)dg2Map.get("children")).add((HashMap<String,String>)dg2Map2.get("dg2Map"));
                }
            }else{
                //为每层添加标识
                dg2Map.put(key,(String) map.get(key));
            }
        }
        //接受orgNum，根据orgNum返回对应的组织机构
        if(dg2Map.get("id").equals(orgNum)){
            outRs.add(dg2Map);
        }
        System.out.println(new Gson().toJson(dg2Map));
        rsout.put("list",list);
        rsout.put("dg2Map",dg2Map);

        return rsout;
    }

    @Override
    @Transactional
    public String addOrganization(OrganizationEntity organizationEntity) throws Exception {



        List<OrganizationEntity> organizationEntities = organization_jpa.findAll();

        //判断组织机构名称是否存在
        for (int i = 0,num = organizationEntities.size();i<num;i++){
            if (organizationEntities.get(i).getOrganizationName().equals(organizationEntity.getOrganizationName())){
                return "组织机构名称已存在";
            }
        }

        //获取住址机构分级
        LinkedHashMap<String,LinkedHashMap<String,Object>> linkedHashMap = getOrganizationListGson(organizationEntities);

        //获取到本账号权限下的所有设备
        LinkedHashMap<String,Object> rsNum = linkedHashMap.get(organizationEntity.getOrganizationSerialNum());

        int max = 0;
        int now = 0;
        for (String key:rsNum.keySet()){
            if(key.contains("1000")){
                if(max<Integer.parseInt(key.substring(key.length()-4,key.length()))){
                    max = Integer.parseInt(key.substring(key.length()-4,key.length()));
                }
            }
        }
        max++;
        String serinum = max+"";
        for (int i = 0,num = serinum.length();i<4-num;i++){
            serinum ="0" + serinum ;
        }

        OrganizationEntity saveOrg = new OrganizationEntity();
        saveOrg.setOrganizationName(organizationEntity.getOrganizationName());
        saveOrg.setOrganizationSerialNum(organizationEntity.getOrganizationSerialNum()+serinum);
        saveOrg.setContactPersonName(organizationEntity.getContactPersonName());
        saveOrg.setContactPersonPhone(organizationEntity.getContactPersonPhone());
        saveOrg.setSupervisionType(organizationEntity.getSupervisionType());
        OrganizationEntity rs = organization_jpa.save(saveOrg);

        if(rs==null){
            return "添加失败";
        }else{
            return "添加成功";
        }

    }

    @Override
    @Transactional
    public String deleteOrg(String orgNum) throws Exception {
        int rs = 0;
        try {
            //先删除用户
            rs=user_jpa.deleteLikeOrganizationName(orgNum);
        }catch (Exception e){
            return "清除组织机构下用户失败";
        }
        /**
         * 此处写删除该组织机构下的所有信息数据
         */
        try {
            //最后删除组织机构
            rs=organization_jpa.deleteLikeOrganizationName(orgNum);
        }catch (Exception e){
            return "清除组织机构失败";
        }

        return "删除成功";
    }

}

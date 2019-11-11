package com.allmodel.models.myact.service.impl;


import com.allmodel.models.authority.dao.User_Jpa;
import com.allmodel.models.authority.entity.UserEntity;
import com.allmodel.models.myact.dao.*;
import com.allmodel.models.myact.entity.ProcessRealtimeProcessnameEntity;
import com.allmodel.models.myact.entity.ProcessTemplateParamsView;
import com.allmodel.models.myact.entity.RealtimeProcessTaskEntity;
import com.allmodel.models.myact.service.RealTimeProcessTaskService;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class RealTimeProcessTaskServiceImpl implements RealTimeProcessTaskService {

    @Autowired
    private ProcessTemplateParamsJpa templateParamsJpa;
    @Autowired
    private RealTimeProcessTaskJpa realTimeProcessTaskJpa;
    @Autowired
    private ProcessRealtimeProcessnameJpa processRealtimeProcessnameJpa;
    @Autowired
    private User_Jpa userJpa;
    @Autowired
    private ProcessingFunctionJpa processingFunctionJpa;


    private Gson gson = new Gson();




    @Override
    public String choiceTemplate(String uuid) {

        //根据UUID查询对应模板
        List<ProcessTemplateParamsView> paramsEntities = templateParamsJpa.findByProcessUuidIndex(uuid);

        System.out.println(gson.toJson(paramsEntities));

        return null;
    }

    @Override
    public String startProcess(String userid,String processUuid,String dataJson,String sectorPeople,String sector,String taskIndex,String fileUrl) {
        //根据UUID查询对应模板
        List<ProcessTemplateParamsView> paramsEntities = templateParamsJpa.findByProcessUuidIndex(processUuid);

        List<RealtimeProcessTaskEntity> realtimeProcessTasks = new ArrayList<RealtimeProcessTaskEntity>();
        //生成改流程的唯一标识
        UUID uuid = UUID.randomUUID();
        //记录什么时候开始是未执行任务
        String judge = "%0";
        final int count;
        //遍历paramsEntities，并将对应数据set到realtimeProcessTasks中，流程开始
        //dataJson为初始第一次数据，也就是paramsEntities中第一次task_index_params不为空的时候
        for (int i = 0,num=paramsEntities.size();i<num;i++){
            RealtimeProcessTaskEntity taskEntity = new RealtimeProcessTaskEntity();
            taskEntity.setIsJudge(paramsEntities.get(i).getIsJudge());
            taskEntity.setTaskIndex(paramsEntities.get(i).getProcessTaskIndex());
            taskEntity.setProcessingDate(new Date());
            taskEntity.setProcessUuid(paramsEntities.get(i).getProcessUuidIndex());
            taskEntity.setTaskUuid(uuid.toString());
            taskEntity.setProcessJumpBack(paramsEntities.get(i).getProcessJumpBack());
            taskEntity.setUserId(userid);
            taskEntity.setFunction(paramsEntities.get(i).getFunction());

            //taskEntity.setProcessJumpBack(paramsEntities.get(i));
            //因为是开始启动流程
            //随意要知道用户是在哪一步填写的信息
            //填写信息之前的都是已经走过去的了
            if(judge.equals("%0")){
                taskEntity.setIsProcessing("1");
                taskEntity.setTaskMsg(dataJson);
                taskEntity.setAttachment(fileUrl);
            }else{
                taskEntity.setIsProcessing("0");
            }
            taskEntity.setTaskName(paramsEntities.get(i).getTaskName());
            //判断什么时候到一个判断语句
            if(paramsEntities.get(i).getIsJudge().equals("1")&&judge.equals("%0")){
                judge = paramsEntities.get(i).getProcessTaskIndex();
                taskEntity.setSector(sector);
                taskEntity.setSectorPeople(sectorPeople);
            }else{
                taskEntity.setSector(paramsEntities.get(i).getSector());
            }

            realtimeProcessTasks.add(taskEntity);
        }
        realTimeProcessTaskJpa.saveAll(realtimeProcessTasks);

        return null;
    }

    @Override
    public List<ProcessRealtimeProcessnameEntity> findBySectorPeople(String userid) {

        List<ProcessRealtimeProcessnameEntity> list = processRealtimeProcessnameJpa.findBySectorPeopleIsProcess0List(userid);

        HashMap<String,List<ProcessRealtimeProcessnameEntity>> processMap = new HashMap<String, List<ProcessRealtimeProcessnameEntity>>();

        //将list中的数据按task_uuid存入map便于后面跳出循环
        for (int i = 0,num = list.size();i<num;i++){
            ProcessRealtimeProcessnameEntity entity = list.get(i);
            if(processMap.get(entity.getTaskUuid())==null){
                List<ProcessRealtimeProcessnameEntity> prs = new ArrayList<ProcessRealtimeProcessnameEntity>();
                prs.add(entity);
                processMap.put(entity.getTaskUuid(),prs);
            }else{
                processMap.get(entity.getTaskUuid()).add(entity);
            }
        }

        List<ProcessRealtimeProcessnameEntity> rs = new ArrayList<ProcessRealtimeProcessnameEntity>();

        //取出第一个未做处理的流程子项添加到返回数组
        for (String key:processMap.keySet()){
            List<ProcessRealtimeProcessnameEntity> pres = processMap.get(key);
            preout:for (int i = 0,num = pres.size();i<num;i++){
                ProcessRealtimeProcessnameEntity realentity = pres.get(i);
                //如果流程中含有3则是拒绝的流程
                if(realentity.getIsProcessing().equals("3")){
                    break preout;
                }
                //取出未处理或者被打回的
                if(realentity.getIsProcessing().equals("0")){
                    rs.add(pres.get(i-1));
                    rs.add(realentity);
                    rs.add(pres.get(i+1));
                    break preout;
                }else if(realentity.getIsProcessing().equals("2")){
                    rs.add(pres.get(i-1));
                    rs.add(realentity);
                    rs.add(pres.get(i+1));
                    break preout;
                }
            }
        }

        HashMap<String,List<ProcessRealtimeProcessnameEntity>> map1 = new HashMap<String, List<ProcessRealtimeProcessnameEntity>>();

        for (int i = 0,num = rs.size();i<num;i++){
            ProcessRealtimeProcessnameEntity entity = rs.get(i);
            if(map1.get(entity.getTaskUuid())==null){
                List<ProcessRealtimeProcessnameEntity> prs = new ArrayList<ProcessRealtimeProcessnameEntity>();
                prs.add(entity);
                map1.put(entity.getTaskUuid(),prs);
            }else{
                map1.get(entity.getTaskUuid()).add(entity);
            }
        }

        List<ProcessRealtimeProcessnameEntity> outRs = new ArrayList<ProcessRealtimeProcessnameEntity>();
        for (String key:map1.keySet()){
            List<ProcessRealtimeProcessnameEntity> pres = map1.get(key);
            //等于2为打回流程
            if (pres.size()==2) {
//                if(pres.get(1).getSectorPeople().equals(userid)){
//                    outRs.add(pres.get(1));
//                }
            }else if(pres.size()==3){//等于3为正常未审批流程
                if(Integer.parseInt(pres.get(pres.size()-1).getTaskIndex())>3){
                    if(pres.get(0).getSectorPeople().equals(userid)){
                        pres.get(2).setTaskName(pres.get(1).getTaskName());
                        outRs.add(pres.get(2));
                    }
                }else{
                    if(pres.get(2).getSectorPeople().equals(userid)){
                        outRs.add(pres.get(2));
                    }
                }

            }
        }

        for (int i = 0,num = outRs.size();i<num;i++){
            //根据用户ID查询用户
            UserEntity user = userJpa.findByUserId(Long.parseLong(outRs.get(i).getUserId()));
            outRs.get(i).setUserId(user.getUsername());
        }
        return outRs;
    }

    @Override
    public ProcessRealtimeProcessnameEntity findByTaskUuidAndIndex(String taskUuid, String taskIndex) {

        ProcessRealtimeProcessnameEntity pr = processRealtimeProcessnameJpa.findByTaskUuidAndTaskIndex(taskUuid,Integer.parseInt(taskIndex)-2+"");

        if(pr.getTaskMsg()==null||pr.getTaskMsg().equals("")){
            pr = processRealtimeProcessnameJpa.findByTaskUuidAndTaskIndex(taskUuid,Integer.parseInt(taskIndex)+"");
        }
        return pr;
    }

    @Override
    public ProcessTemplateParamsView approvalPassed1(String processUuid,String taskIndex) {

        List<String> indexs = new ArrayList<>();
        indexs.add((Integer.parseInt(taskIndex)-1)+"");
        indexs.add((Integer.parseInt(taskIndex))+"");
        indexs.add((Integer.parseInt(taskIndex)+1)+"");

        List<ProcessTemplateParamsView> view = templateParamsJpa.findByProcessUuidIndexAndProcessTaskIndexIn(processUuid,indexs);

        ProcessTemplateParamsView rs;
        if(view.size()!=3){
            rs = view.get(0);

            rs.setSector("000");
        }else{
            rs = view.get(0);

            rs.setSector(view.get(1).getSector());
        }



        return rs;
    }

    @Override
    @Transactional
    public String approvalPassed2(String processUuid, String taskUuid, String taskIndex, String params, String sectorPeople, String sector,String fileUrl) {

        //获取改taskindex下有关的数据
        List<String> indexs = new ArrayList<>();
        indexs.add((Integer.parseInt(taskIndex)-2)+"");
        indexs.add((Integer.parseInt(taskIndex)-1)+"");
        indexs.add((Integer.parseInt(taskIndex))+"");
        //查询符合条件的该次审批
        List<ProcessRealtimeProcessnameEntity> list = processRealtimeProcessnameJpa.findByTaskUuidAndTaskIndexIn(taskUuid,indexs);

        //查询对应的流程模板
        List<ProcessTemplateParamsView> view = templateParamsJpa.findByProcessUuidIndexAndProcessTaskIndexIn(processUuid,indexs);

        //将view种的数据存入map
        HashMap<String,ProcessTemplateParamsView> map = new HashMap<String, ProcessTemplateParamsView>();
        for (int i = 0,num=view.size();i<num;i++){
            map.put(view.get(i).getProcessTaskIndex(),view.get(i));
        }

        List<RealtimeProcessTaskEntity> rs = new ArrayList<RealtimeProcessTaskEntity>();

        for(int i = 1,num=list.size();i<num;i++){
            ProcessRealtimeProcessnameEntity entity = list.get(i);

            RealtimeProcessTaskEntity realEntity = new RealtimeProcessTaskEntity();
            realEntity.setId(entity.getId());
            realEntity.setProcessJumpBack(entity.getProcessJumpBack());
            //已处理
            realEntity.setIsProcessing("1");
            realEntity.setTaskUuid(entity.getTaskUuid());
            realEntity.setUserId(entity.getUserId());
            realEntity.setTaskName(entity.getTaskName());
            realEntity.setProcessUuid(entity.getProcessUuid());
            realEntity.setTaskIndex(entity.getTaskIndex());
            realEntity.setProcessingDate(new Date());
            realEntity.setIsJudge(entity.getIsJudge());
            if(fileUrl == null||fileUrl.equals("")){
                realEntity.setAttachment(entity.getAttachment());
            }else{
                realEntity.setAttachment(fileUrl);
            }
            //判断参数是否为null
            //为null说明没有参数则调用之前的参数
            if(params==null||params.equals("")||params.equals("{}")){
                realEntity.setTaskMsg(list.get(0).getTaskMsg());
            }else{
                realEntity.setTaskMsg(params);
            }
            //为0的是非判断语句，所以只有审批通过执行
            if(entity.getIsJudge().equals("0")){
                realEntity.setSector("0");
            }else{
                realEntity.setSector(sector);
                realEntity.setSectorPeople(sectorPeople);
            }
            rs.add(realEntity);
            //处理function种含有sql 的
            if(map.get(list.get(i).getTaskIndex()).getFunction()!=null){
                processingFunction(map.get(list.get(i).getTaskIndex()).getFunction(),params);
            }
        }
        realTimeProcessTaskJpa.saveAll(rs);

        return "";
    }

    /**
     * 处理含有sql的流程
     * @param sql
     * @param params
     */
    private void processingFunction(String sql,String params) {

        params = "[{\"时间\":\"time\",\"报销部门\":\"bumen\",\"摘要\":\"zhaiyao\",\"报销项目\":\"pij\",\"备注\":\"msg\",\"报销人\":\"name\",\"金额\":\"math\"},{\"time\":\"2019/10/15\",\"bumen\":\"9d0a04a7-0f01-4a1a-8376-b7fe1b041e66\",\"zhaiyao\":\"5\",\"pij\":\"买材料\",\"msg\":\"笔记本00\",\"name\":\"郜某某\",\"math\":\"50000\"}]";

        //解析sql部分
        List<LinkedTreeMap> list = gsonToList(params, LinkedTreeMap.class);
        LinkedTreeMap<String,String> map = list.get(1);
        for (Object key:map.keySet()){
            if(sql.contains("{"+key+"}")){
                sql = sql.replace("{"+key+"}","'"+map.get(key)+"'");
            }
        }
        //执行sql部分
        String[] strs = sql.split("&&&");
        String type = strs[1].toUpperCase();
        Object rs = null;
        if(type.equals("LIST")){
            rs = processingFunctionJpa.processFunctionList(strs[0]);
        }else if(type.equals("ONEPERSION")){
            rs = processingFunctionJpa.processFunction(strs[0]);
        }else if(type.equals("UPDATE")){
            rs = processingFunctionJpa.processFunctionUpdateOrDelete(strs[0]);
        }else if(type.equals("DELETE")){
            rs = processingFunctionJpa.processFunctionUpdateOrDelete(strs[0]);
        }
        System.out.println(gson.toJson(rs));
        System.out.println(sql);

    }

    /**
     * json字符串转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        if (gson != null) {
            // 根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    @Override
    public List<ProcessRealtimeProcessnameEntity> auditFailed1(String processUuid,String taskUuid, String taskIndex) {

        //打回1，选择打回人
        List<ProcessRealtimeProcessnameEntity> view = processRealtimeProcessnameJpa.findOverProcess(taskUuid);

        for (int i = 0,num = view.size();i<num;i=i+2){

            if(i == 0||i==num-1){
                continue;
            }else{
                view.get(i).setSectorPeople(view.get(i-1).getSectorPeople());
            }
        }
        List<ProcessRealtimeProcessnameEntity> rs = new ArrayList<ProcessRealtimeProcessnameEntity>();

        for (int i = 0,num = view.size();i<num;i++){
            if(view.get(i).getIsJudge().equals("0")){
                rs.add(view.get(i));
            }
        }

        for (int i = 0,num = rs.size();i<num;i++){

            if(rs.get(i).getSectorPeople()==null){
                //根据用户ID查询用户
                UserEntity user = userJpa.findByUserId(Long.parseLong(rs.get(i).getUserId()));
                rs.get(i).setUserId(user.getUsername());
                rs.get(i).setSector(user.getOrganizationName());
            }else{
                //根据用户ID查询用户
                UserEntity user = userJpa.findByUserId(Long.parseLong(rs.get(i).getSectorPeople()));
                rs.get(i).setUserId(user.getUsername());
                rs.get(i).setSector(user.getOrganizationName());
            }
        }

        return rs;
    }

    @Override
    public ProcessTemplateParamsView auditFailed2(String processUuid, String taskIndex) {

        //因为要查询当前步骤的下一步判断所以要taskIndex+1
        ProcessTemplateParamsView view = templateParamsJpa.findByProcessUuidIndexAndProcessTaskIndex(processUuid,(Integer.parseInt(taskIndex))+"");

        return view;
    }

    @Override
    @Transactional
    public String auditFailed3(String processUuid, String taskUuid, String taskIndex, String params, String sectorPeople, String sector,String state,String backIndex) {

        List<String> indexs = new ArrayList<>();
        indexs.add((Integer.parseInt(taskIndex)-1)+"");
        indexs.add((Integer.parseInt(taskIndex))+"");
        //查询符合条件的该次审批
        List<ProcessRealtimeProcessnameEntity> list = processRealtimeProcessnameJpa.findByTaskUuidAndTaskIndexIn(taskUuid,indexs);

        List<RealtimeProcessTaskEntity> rs = new ArrayList<RealtimeProcessTaskEntity>();

        for(int i = 0,num=list.size();i<num;i++){
            ProcessRealtimeProcessnameEntity entity = list.get(i);
            //查询是否是判断语句
            if(entity.getIsJudge().equals("1")){

                //如果是判断则查看是否有回跳
                //日过有回跳则修改回跳之前的所有状态
                if(!state.equals("0")){
                    //获取回调序号
                    String index = backIndex;
                    //禁止回到第一步
                    if(index.equals("1")){
                        index = "2";
                    }
                    //修改当taskUuid到index之间的处理状态，修改为2未通过
                    List<String> indexs2 = new ArrayList<String>();
                    for (int y=Integer.parseInt(index);y<(Integer.parseInt(taskIndex)+1);y++){
                        indexs2.add(y+"");
                    }

                    //打回逻辑处理
                    if(params==null||params.equals("")||params.equals("[]")){
                        //修改数据库
                        realTimeProcessTaskJpa.updateByTaskUuidAndTaskIndexIn(taskUuid,indexs2,new Date(),sectorPeople);
                    }else{
                        //修改数据库
                        realTimeProcessTaskJpa.updateByTaskUuidAndTaskIndexIn(taskUuid,indexs2,params,new Date(),sectorPeople);
                    }

                }else{

                    List<String> indexs2 = new ArrayList<String>();
                    for (int y=2;y<(Integer.parseInt(taskIndex)+1);y++){
                        indexs2.add(y+"");
                    }

                    if(params==null||params.equals("")||params.equals("[]")){
                        //没有则流程结束,3为流程结束且未通过
                        realTimeProcessTaskJpa.updateByTaskUuid(taskUuid,new Date(),indexs2);
                        realTimeProcessTaskJpa.updateBySector(taskUuid,params);
                    }else{
                        //没有则流程结束,3为流程结束且未通过
                        realTimeProcessTaskJpa.updateByTaskUuid(taskUuid,params,new Date(),indexs2);
                        realTimeProcessTaskJpa.updateBySector(taskUuid,params);
                    }

                }

                break;
            }
        }
        return "";
    }

    @Value("${fileurl.savefile}")
    private String savefile;

    @Value("${fileurl.getfile}")
    private String getfile;

    @Override
    public String uploadFile(MultipartFile file) {

        System.out.println(savefile);
        String fileName = "";
        String filePath = "";
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }else{
            fileName = file.getOriginalFilename();  // 文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
            filePath = savefile; // 上传后的路径
            fileName = UUID.randomUUID() + suffixName; // 新文件名
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return getfile + fileName;
    }

    @Override
    public List<ProcessRealtimeProcessnameEntity> getOverProcess(String userId) {

        List<ProcessRealtimeProcessnameEntity> rs = processRealtimeProcessnameJpa.getOverProcess(userId);
        if(rs == null){
           return  processRealtimeProcessnameJpa.getOverProcess2(userId);
        }else{
            List<ProcessRealtimeProcessnameEntity> rs2 = processRealtimeProcessnameJpa.getOverProcess2(userId);
            if(rs2!=null&&rs2.size()!=0){
                for (int i = 0,num = rs2.size();i<num;i++){
                    rs.add(rs2.get(i));
                }
            }
        }
        return rs;
    }

    @Override
    public List<ProcessRealtimeProcessnameEntity> getDoneProcess(String userId) {

        List<ProcessRealtimeProcessnameEntity> mRs = processRealtimeProcessnameJpa.getDoneProcess(userId);

        HashMap<String,List<ProcessRealtimeProcessnameEntity>> processMap = new HashMap<String, List<ProcessRealtimeProcessnameEntity>>();

        //将list中的数据按task_uuid存入map便于后面跳出循环
        for (int i = 0,num = mRs.size();i<num;i++){
            ProcessRealtimeProcessnameEntity entity = mRs.get(i);
            if(processMap.get(entity.getTaskUuid())==null){
                List<ProcessRealtimeProcessnameEntity> prs = new ArrayList<ProcessRealtimeProcessnameEntity>();
                prs.add(entity);
                processMap.put(entity.getTaskUuid(),prs);
            }else{
                processMap.get(entity.getTaskUuid()).add(entity);
            }
        }

        List<ProcessRealtimeProcessnameEntity> outRs = new ArrayList<ProcessRealtimeProcessnameEntity>();

        for (String key:processMap.keySet()){
            List<ProcessRealtimeProcessnameEntity> list = processMap.get(key);
            for (int i = 0,num=list.size();i<num;i++){
                ProcessRealtimeProcessnameEntity entity = list.get(i);
                //先对下一步的流程序号进行判断，如果是0则表示流程结束
                //如果当前行的处理人等于传入用户的id则判断他的下一步是否是1，是1则将当前步添加入返回结果数组
                if(entity.getSectorPeople()!=null&&entity.getSectorPeople().equals(userId)){
                    if(list.get(i+1).getIsProcessing().equals("1")||list.get(i+1).getIsProcessing().equals("2")){
                        outRs.add(entity);
                    }

                }
            }
        }
        return outRs;
    }
}

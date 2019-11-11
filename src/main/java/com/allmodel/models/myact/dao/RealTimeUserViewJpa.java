package com.allmodel.models.myact.dao;



import com.allmodel.models.myact.entity.RealTimeUserView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RealTimeUserViewJpa extends JpaRepository<RealTimeUserView,Integer> {

    /**
     * 根据uuid、isJudge、isProcessing 查询
     * @param taskUuid
     * @param isJudge
     * @param isProcessing
     * @return
     */
    List<RealTimeUserView> findByTaskUuidAndIsJudgeAndIsProcessing(String taskUuid, String isJudge, String isProcessing);

    /**
     * 根据uuid，isJudge、isProcessing不等于1 查询
     * @param taskUuid
     * @param isJudge
     * @param isProcessing
     * @return
     */
    List<RealTimeUserView> findByTaskUuidAndIsJudgeAndIsProcessingNot(String taskUuid, String isJudge, String isProcessing);
}

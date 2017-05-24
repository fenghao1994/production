package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;

/**
 * Created by KY on 2016/11/24.
 */

public interface ViewProductI extends TempViewI{
    void onFeedingNumSucceed(RespFeedNum data);
    void touliaoSucceed(String message);
    void touliaoFailed(String message);
    void onMachineOperationSucceed(RespMachineOperation data);
    void onMachineOperationFailed(String message);
    void getBatchByInBox(RespBatchByInBox data);
    void getBatchBySendMaterial(RespBatchByInBox data);
    void getroceduresByBatch(RespBatchByInBox data);
    void onFailed(int who ,String message);
    void onNextSucceed(String message);
    void onChangeTool(String message);
    void onUpdateStatusSucceed(String message);
    void onProductListSucceed(RespProductList data);
    void onCreateProductSucceed(String message);
    void onInBoxSucceed(String message);
    void onInBoxDetailSucceed(RespInBoxDetail data);
    void onMachiningCountByProcedure(RespProductionCount data);
}

package com.qdxy.app.lhjh.api;

import com.lf.tempcore.tempResponse.TempResponse;
import com.qdxy.app.lhjh.activities.alarm.RespAlarmList;
import com.qdxy.app.lhjh.activities.arrangeProcedure.RespArrangeProList;
import com.qdxy.app.lhjh.activities.check.RespCheckPiCiList;
import com.qdxy.app.lhjh.activities.check.RespCheckPrdDetail;
import com.qdxy.app.lhjh.activities.check.RespCheckPrdList;
import com.qdxy.app.lhjh.activities.count.RespCountList;
import com.qdxy.app.lhjh.activities.count.RespCountProductionList;
import com.qdxy.app.lhjh.activities.exceptions.RespExceptionJudgement;
import com.qdxy.app.lhjh.activities.exceptions.RespExceptionJudgementList;
import com.qdxy.app.lhjh.activities.exceptions.RespExpList;
import com.qdxy.app.lhjh.activities.exceptions.RespProblemDetail;
import com.qdxy.app.lhjh.activities.home.RespHomeMenuGroup;
import com.qdxy.app.lhjh.activities.login.RespLogin;
import com.qdxy.app.lhjh.activities.management.RespManagePiCi;
import com.qdxy.app.lhjh.activities.messageCenter.RespMessage;
import com.qdxy.app.lhjh.activities.messageCenter.RespMessageDetail;
import com.qdxy.app.lhjh.activities.onOffLine.RespProcedureList;
import com.qdxy.app.lhjh.activities.product.RespBatchByInBox;
import com.qdxy.app.lhjh.activities.product.RespFeedNum;
import com.qdxy.app.lhjh.activities.product.RespInBoxDetail;
import com.qdxy.app.lhjh.activities.product.RespJiaGong2;
import com.qdxy.app.lhjh.activities.product.RespMachineOperation;
import com.qdxy.app.lhjh.activities.product.RespProcedure;
import com.qdxy.app.lhjh.activities.product.RespProductBox;
import com.qdxy.app.lhjh.activities.product.RespProductList;
import com.qdxy.app.lhjh.activities.product.RespProduction2;
import com.qdxy.app.lhjh.activities.product.RespProductionCount;
import com.qdxy.app.lhjh.activities.product.RespProductionList;
import com.qdxy.app.lhjh.activities.product.RespSendMatrialList;
import com.qdxy.app.lhjh.activities.qualityCheck.RespQualityDetail;
import com.qdxy.app.lhjh.activities.storageRoom.RequestBodyMaterial;
import com.qdxy.app.lhjh.activities.storageRoom.RespApprovePrdList;
import com.qdxy.app.lhjh.activities.storageRoom.RespMaterialDetail;
import com.qdxy.app.lhjh.activities.storageRoom.RespStorageDetail;
import com.qdxy.app.lhjh.activities.storageRoom.RespStorageItem;
import com.qdxy.app.lhjh.activities.storageRoom.RespStorageList;
import com.qdxy.app.lhjh.bean.RespPermission;
import com.qdxy.app.lhjh.bean.RespProductLines;
import com.qdxy.app.lhjh.bean.RespProductModel;
import com.qdxy.app.lhjh.bean.RespSelector;
import com.qdxy.app.lhjh.config.AppConfig;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by KY on 2016/11/2.
 */

public interface TempAPI {
    /**
     * 用户登录
     *
     * @param museUserName
     * @param musePwd
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "user/login")
    Call<RespLogin> userLogin(@Field("userName") String museUserName, @Field("password") String musePwd);

    /**
     * 用户退出登录
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "user/logout")
    Call<TempResponse> userLogout(@Field("id") String id);

    /**
     * 获取权限
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "user/permission")
    Call<RespPermission> requestPermission();

    /**
     * 首页
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "user/menu")
    Call<RespHomeMenuGroup> requestHomeMenu();

    /**
     * 获取生产线
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + AppConfig.DEBUG_API + "productionLine/list")
    Call<RespProductLines> requestProductedLines();

    /**
     * 获取生产线所有
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + AppConfig.DEBUG_API + "productionline/list/allByUser")
    Call<RespProductLines> requestProductedLinesAll();

    /**
     * 获取产品型号
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "productType/list")
    Call<RespProductModel> requestProductedModel();

    /**
     * 获取批次管理列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + AppConfig.DEBUG_API + "productionBatch/list")
    Call<RespManagePiCi> requestManagePiCiList(@Query("active") String active, @Query("page") String page);


    /**
     * 新增批次
     *
     * @param productionLineId
     * @param productTypeId
     * @param batch
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + AppConfig.DEBUG_API + "productionBatch/create")
    Call<TempResponse> createProductionBatch(@Field("productionLineId") String productionLineId, @Field("productTypeId") String productTypeId, @Field("batch") String batch);

    /**
     * 修改批次状态
     *
     * @param status            0未启用,1启用，2停用
     * @param productionBatchId 批次id
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + AppConfig.DEBUG_API + "productionBatch/update")
    Call<TempResponse> updateProductionBatchType(@Field("status") String status, @Field("productionBatchId") String productionBatchId);

    /**
     * 创建工件(投料)
     *
     * @param ProductBatchId 批次id
     * @param ProductCode    产品号
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "machiningParts/create")
    Call<TempResponse> createProduction(@Field("ProductBatchId") String ProductBatchId, @Field("ProductCode") String ProductCode);

    /**
     * 提交工件(进入下一工序)
     *
     * @param id              工件id
     * @param operationType   自检时需要设置为1 普通0，14送检
     * @param remark          提交时日志描述
     * @param selfCheckResult （operationType为1有效）自检状态:0正常,1异常
     * @return errorCode -1为需要自检,1为需要更换刀具
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "machiningParts/next")
    Call<TempResponse> machiningParts_next(@Field("id") String id, @Field("operationType") String operationType, @Field("remark") String remark, @Field("selfCheckResult") String selfCheckResult, @Field("IsCheckProcedure") boolean IsCheckProcedure);

    /**
     * 获取按工序分组的工件列表
     *
     * @param status 工件状态,默认加工中
     * @param lineId 生产线id
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getByProcedure")
    Call<RespProductionList> getProductionByProcedure(@Query("status") String status, @Query("lineId") String lineId);

    /**
     * 获取自己在加工里能做的操作
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getMachineOperation")
    Call<RespMachineOperation> getMachineOperation();

    /**
     * 获取自己能投料的批次列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getBatchBySendMaterial")
    Call<RespBatchByInBox> getBatchBySendMaterial();

    /**
     * 新的获取工件列表接口
     *
     * @param batchId     批次id
     * @param procedureId 工序id
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getList")
    Call<RespProduction2> getList(@Query("batchId") int batchId, @Query("procedureId") int procedureId, @Query("page") int page);

    /**
     * 获取当前投料的序号
     *
     * @param batchId 批次id
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getBySendMaterialNumber")
    Call<RespFeedNum> getBySendMaterialNumber(@Query("batchId") int batchId);

    /**
     * 获取已激活的批次列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "productionBatch/listByMachining")
    Call<RespBatchByInBox> getlistByMachining();

    /**
     * 获取工序
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "procedure/proceduresByBatch")
    Call<RespBatchByInBox> getroceduresByBatch();

    /**
     * 获取自己能装箱的批次列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getBatchByInBox")
    Call<RespBatchByInBox> getBatchByInBox();

    /**
     * 获取已投料列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getBySendMaterial")
    Call<RespSendMatrialList> getBySendMaterial(@Query("page") String page);

    /**
     * 3获取按工序分组的工件列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getByProcedure")
    Call<RespJiaGong2> getByProcedure(@Query("page") int page, @Query("searchKey") String searchKey);

    /**
     * 3获取按工序分组的工件列表(产品下线)
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getByProcedure")
    Call<RespProcedure> getByProcedure(@Query("status") String status, @Query("lineId") String lineId, @Query("page") String page);

    /**
     * 工件上线
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getByBatch")
    Call<RespProcedure> getByBatch(@Query("status") String status, @Query("lineId") String lineId, @Query("page") String page);

    /**
     * 安排工序列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "procedure/procedurePageList")
    Call<RespArrangeProList> procedurePageList(@Query("page") int page);

    /**
     * 单个或批量更新工件状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "machiningParts/updateStatus")
    Call<TempResponse> updateStatus(@Field("ids[]") String[] ids, @Field("status") String status, @Field("operationType") String operationType, @Field("procedureNumber") String procedureNumber, @Field("remark") String remark);

    /**
     * 单个或批量更新工件状态
     *
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "machiningParts/updateStatus")
    Call<TempResponse> updateStatus(@Field("id") String id, @Field("status") String status, @Field("operationType") String operationType, @Field("procedureId") String procedureNumber, @Field("remark") String remark);


    /**
     * 获取产品箱列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "productBox/list")
    Call<RespProductBox> getProductBoxList(@Query("batchId") String batchId, @Query("status") String status, @Query("page") String page);

    /**
     * 获取未装箱的产品列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "product/list")
    Call<RespProductList> getProductList(@Query("boxId") String batchId, @Query("size") String size);

    /**
     * 装箱
     *
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "product/inBox")
    Call<TempResponse> requestInBox(@Field("productBoxId") String ProductBoxId, @Field("ids[]") String[] ids);

    /**
     * 创建产品箱
     *
     * @param name           产品箱名称
     * @param productBatchId 批次id
     * @param Size           容量
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "productBox/create")
    Call<TempResponse> requestcCreateProductBox(@Field("name") String name, @Field("ProductBatchId") String productBatchId, @Field("size") String Size);

    /**
     * 更新产品箱状态
     *
     * @param id
     * @param status 1-装箱完成
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "productBox/status")
    Call<TempResponse> upDateProductBox(@Field("id") String id, @Field("status") String status);

    /**
     * 获取产品箱
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "productBox/get")
    Call<RespInBoxDetail> requestProductBox(@Query("id") String id);

    /**
     * 获取产品入库详情
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "productBox/apply/get")
    Call<RespMaterialDetail> productBoxApplyDetail(@Query("id") int id);

    /**
     * 获取产品管理列表（申请，审批，确认）
     *
     * @param type     type:0入库申请 1审批 2确认
     * @param isHandle
     * @return
     */
    @GET(AppConfig.BASE_URL + "productBox/apply/list")
    Call<RespApprovePrdList> requestProductApplyList(@Query("type") int type, @Query("isHandle") boolean isHandle, @Query("page") int page);

    /**
     * 获取抽检情况
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "randomCheck/list")
    Call<RespCheckPiCiList> requestRandomCheckList();

    /**
     * 按批次获取抽检列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "randomCheck/list/batch")
    Call<RespCheckPrdList> requestRandomCheckPrdList(@Query("batchCode") String batchCode, @Query("isCheck") boolean isCheck, @Query("page") String page);

    /**
     * 送检列表
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/qualityCheck/list")
    Call<RespExpList> qualityCheckList(@Query("isCheck") boolean isCheck, @Query("qualityCheck") int qualityCheck, @Query("page") int page);

    /**
     * 按抽检任务获取详情
     *
     * @param id 抽检任务id
     * @return
     */
    @GET(AppConfig.BASE_URL + "randomCheck/task")
    Call<RespCheckPrdDetail> requestRandomCheckDetail(@Query("id") String id);

    /**
     * 质量检测处理详情
     *
     * @param id 抽检任务id
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/qualityCheck/handle")
    Call<RespQualityDetail> qualityCheckDetail(@Query("id") int id, @Query("procedureId") int procedureId, @Query("qualityCheck") int qualityCheck);

    /**
     * 创建抽检记录
     *
     * @param RandomCheckTaskId   抽检任务id
     * @param Result              抽检结果
     * @param ResponsiblePersonId 责任人id
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "randomCheck/createRecord")
    Call<TempResponse> createRecord(@Field("RandomCheckTaskId") String RandomCheckTaskId, @Field("Result") String Result, @Field("ResponsiblePersonId") String ResponsiblePersonId);


    /**
     * 异常判断列表
     *
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "workingTimeoutProblem/list")
    Call<RespExceptionJudgementList> requestProblemList(@Query("isJudge") boolean isJudge, @Query("page") int page);

    /**
     * 上下班
     *
     * @param workStatus 0-上班 1-下班
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "user/workStatus")
    Call<TempResponse> requestWorkStatus(@Field("workStatus") String workStatus);

    /**
     * 异常判断详情
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "workingTimeoutProblem/handle")
    Call<RespExceptionJudgement> requestProblemGet(@Query("id") String id);

    /**
     * 放工上线获取工序列表
     *
     * @param machiningPartId 工序号
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/beforeProcedure")
    Call<RespProcedureList> beforeProcedure(@Query("machiningPartId") String machiningPartId);

    /**
     * 无异常判断
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "test/problem/judge")
    Call<TempResponse> requestNoProblem(@Field("id") String id);

    /**
     * 报警管理列表
     *
     * @param lineId
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "procedure/lineprocedures")
    Call<RespAlarmList> lineprocedures(@Query("lineId") String lineId, @Query("page") int page);

    /**
     * 停止报警
     *
     * @param ids
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "procedure/stopalarm")
    Call<TempResponse> stopalarm(@Field("procedureIds[]") String[] ids);

    /**
     * 停止报警
     *
     * @param ids
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "procedure/startalarm")
    Call<TempResponse> startpalarm(@Field("procedureIds[]") String[] ids);

    /**
     * 安排工序
     *
     * @param userId
     * @param ids
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "procedure/setprocdurewithuser")
    Call<TempResponse> setprocdurewithuser(@Field("userId") String userId, @Field("procedureIds[]") String[] ids);

    /**
     * 取消工序安排
     *
     * @param userId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "procedure/clear")
    Call<TempResponse> procedureClear(@Field("userId") String userId);

    /**
     * 提交异常判断
     *
     * @param id             异常类型id
     * @param handleType     异常判断类型40-无异常41-工件异常42-设备异常 43-刀具异常
     * @param prodcutionCode 产品号
     * @param deviceToolId   刀具id
     * @param deviceCode     设备号
     * @param description    描述内容
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "workingTimeoutProblem/handleSubmit")
    Call<TempResponse> handProblem(@Field("id") String id, @Field("handleType") String handleType, @Field("prodcutionCode") String prodcutionCode, @Field("deviceToolId") String deviceToolId, @Field("deviceCode") String deviceCode, @Field("description") String description);

    /**
     * 质量检测提交
     *
     * @param MachiningPartsId
     * @param ProcedureId
     * @param Result
     * @param Remark
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "machiningParts/qualityCheck/handleSubmit")
    Call<TempResponse> handleQualityCheck(@Field("MachiningPartsId") int MachiningPartsId, @Field("ProcedureId") int ProcedureId, @Field("Result") boolean Result, @Field("Remark") String Remark, @Field("isNext") boolean isNext);

    /**
     * 通过生产线获取工序列表
     *
     * @param userId
     * @param lineId （创建工序安排id传0）
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "procedure/userprocedures")
    Call<RespAlarmList> userprocedures(@Query("userId") String userId, @Query("lineId") String lineId, @Query("page") int page);

    /**
     * 获取设备列表
     *
     * @param searchKey
     * @return
     */
    @GET(AppConfig.BASE_URL + "device/list/search")
    Call<RespSelector> userprocedures(@Query("lineId") int LineId, @Query("searchKey") String searchKey, @Query("page") String page, @Query("size") String size);

    /**
     * 工件列表
     *
     * @param searchKey
     * @param page
     * @param size
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/list/search")
    Call<RespSelector> requestGongjianList(@Query("lineId") int LineId, @Query("searchKey") String searchKey, @Query("page") String page, @Query("size") String size);

    /**
     * 获取工件列表
     *
     * @param machiningPartsId
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getMachineResponsible")
    Call<RespSelector> createRecord(@Query("machiningPartsId") String machiningPartsId);

    /**
     * 获取人员信息
     *
     * @param searchKey
     * @param page
     * @param size
     * @return
     */
    @GET(AppConfig.BASE_URL + "user/list/search")
    Call<RespSelector> userstarch(@Query("lineId") int LineId, @Query("searchKey") String searchKey, @Query("page") String page, @Query("size") String size);

    /**
     * 刀具列表
     *
     * @param deviceId
     * @param page
     * @param size
     * @return
     */
    @GET(AppConfig.BASE_URL + "deviceTool/list")
    Call<RespSelector> deviceToolList(@Query("deviceId") int deviceId, @Query("page") String page, @Query("size") String size);

    /**
     * 加工异常报警
     *
     * @param procedureId
     * @param description
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "workingTimeoutProblem/create")
    Call<TempResponse> workingTimeoutProblemCreate(@Field("procedureId") int procedureId, @Field("description") String description);

    /**
     * 获取加工统计数据
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningParts/getMachiningCountByProcedure")
    Call<RespProductionCount> getMachiningCountByProcedure();

    /**
     * 刀具异常列表
     *
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "deviceToolProblem/list")
    Call<RespExpList> deviceToolProblemList(@Query("isHandled") boolean isJudge, @Query("page") int page);

    /**
     * 设备异常列表
     *
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "deviceProblem/list")
    Call<RespExpList> deviceProblemList(@Query("isHandled") boolean isJudge, @Query("page") int page);

    /**
     * 加工异常列表
     *
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningPartProblem/list")
    Call<RespExpList> machiningPartProblemList(@Query("isHandled") boolean isJudge, @Query("page") int page);

    /**
     * 刀具异常详情
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "deviceToolProblem/handle")
    Call<RespProblemDetail> deviceToolProblemDetail(@Query("id") int id);

    /**
     * 设备异常详情
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "deviceProblem/handle")
    Call<RespProblemDetail> deviceProblemDetail(@Query("id") int id);

    /**
     * 工件异常详情
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "machiningPartProblem/handle")
    Call<RespProblemDetail> machiningPartProblemDetail(@Query("id") int id);

    /**
     * 刀具异常处理
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "deviceToolProblem/handleSubmit")
    Call<TempResponse> deviceToolProblemHandleSubmit(@Field("id") int id, @Field("HandleType") int HandleType, @Field("ResponsiblePersonId") int ResponsiblePersonId, @Field("Remark") String Remark);

    /**
     * 设备异常处理
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "deviceProblem/handleSubmit")
    Call<TempResponse> deviceProblemHandleSubmit(@Field("id") int id, @Field("HandleType") int HandleType, @Field("ResponsiblePersonId") int ResponsiblePersonId, @Field("Remark") String Remark);

    /**
     * 工件异常处理
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "machiningPartProblem/handleSubmit")
    Call<TempResponse> machiningPartProblemHandleSubmit(@Field("id") int id, @Field("HandleType") int HandleType, @Field("ResponsiblePersonId") int ResponsiblePersonId, @Field("Remark") String Remark);

    /**
     * 消息中心未读列表
     *
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "systemMessage/unread")
    Call<RespMessage> systemMessageUnread(@Query("page") int page);

    /**
     * 消息中心详情
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "systemMessage/message")
    Call<RespMessageDetail> systemMessageDetail(@Query("id") int id);

    /**
     * 消息中心已读列表
     *
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "systemMessage/readed")
    Call<RespMessage> systemMessageReaded(@Query("page") int page);

    /**
     * 获取设备下刀具列表
     *
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "deviceTool/isGrantedList")
    Call<RespSelector> deviceToolList(@Query("deviceId") int deviceId, @Query("page") int page);

    /**
     * 工件异常处理
     *
     * @param ids
     * @param IsNormalChange
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "deviceTool/changeDeviceTool")
    Call<TempResponse> changeDeviceTool(@Field("ids[]") String[] ids, @Field("IsNormalChange") boolean IsNormalChange);

    /**
     * 获取毛坯管理列表
     *
     * @param type     0入库申请 1抽检 2审批
     * @param isHandle 是否处理
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "roughcast/apply/list")
    Call<RespStorageList> applyList(@Query("type") int type, @Query("isHandle") boolean isHandle, @Query("page") int page);

    /**
     * 获取毛坯领用管理列表
     *
     * @param type     0申请 1发货
     * @param isHandle 是否处理
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "roughcast/order/list")
    Call<RespStorageList> orderList(@Query("type") int type, @Query("isHandle") boolean isHandle, @Query("page") int page);

    /**
     * 获取辅料领用列表
     *
     * @param type     0领用申请 1审批 2发货
     * @param isHandle 是否处理
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "auxiliary/order/list")
    Call<RespStorageList> orderList_auxiliary(@Query("type") int type, @Query("isHandle") boolean isHandle, @Query("page") int page);

    /**
     * 产品清点列表
     *
     * @param isAll
     * @param status 1未清点 2已清点
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "productBox/list")
    Call<RespStorageList> productBoxList(@Query("isAll") boolean isAll, @Query("status") int status, @Query("page") int page);

    /**
     * 获取刀具领用管理列表
     *
     * @param type     0领用申请 1审批 2发货
     * @param isHandle 是否处理
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "deviceTool/order/list")
    Call<RespStorageList> orderList_deviceTool(@Query("type") int type, @Query("isHandle") boolean isHandle, @Query("page") int page);

    /**
     * 获取毛坯管理详情
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "roughcast/apply/get")
    Call<RespStorageDetail> applyGet(@Query("id") int id);

    /**
     * 获取毛坯领用详情
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "roughcast/order/get")
    Call<RespStorageDetail> orderGet(@Query("id") int id);

    /**
     * 获取毛坯领用详情
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "auxiliary/order/get")
    Call<RespMaterialDetail> orderGet_auxiliary(@Query("id") int id);

    /**
     * 获取毛坯领用详情
     *
     * @param id
     * @return
     */
    @GET(AppConfig.BASE_URL + "deviceTool/order/get")
    Call<RespMaterialDetail> orderGet_deviceTool(@Query("id") int id);

    /**
     * 辅料类型
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "auxiliary/type")
    Call<RespStorageItem> getAuxiliaryType();

    /**
     * 刀具类型
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "deviceTool/type")
    Call<RespStorageItem> getDeviceToolType();

    /**
     * 毛坯类型
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "roughcast/type")
    Call<RespStorageItem> getRoughcastType();

    /**
     * 获取厂家
     *
     * @return
     */
    @GET(AppConfig.BASE_URL + "roughcast/factory")
    Call<RespStorageItem> getRoughcastFactory();

    /**
     * 创建毛坯入库申请
     *
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "roughcast/apply/create")
    Call<TempResponse> applyCreate(@Field("manufactorId") int manufactorId, @Field("modelId") int modelId, @Field("count") int count);

    /**
     * 创建毛坯入库申请
     *
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "roughcast/order/create")
    Call<TempResponse> orderCreate(@Field("manufactorId") int manufactorId, @Field("modelId") int modelId, @Field("count") int count, @Field("productionLineId") int productionLineId, @Field("remark") String remark);

    /**
     * 创建辅料领用
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(AppConfig.BASE_URL + "deviceTool/order/create")
    Call<TempResponse> orderCreate_deviceTool(@Body RequestBodyMaterial body);

    /**
     * 创建辅料领用
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(AppConfig.BASE_URL + "auxiliary/order/create")
    Call<TempResponse> orderCreate_roughcast(@Body RequestBodyMaterial body);

    /**
     * 取消毛坯入库申请
     *
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "roughcast/apply/cancel")
    Call<TempResponse> applyCancel(@Field("id") int id);

    /**
     * 毛坯入库审批
     *
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "roughcast/apply/approve")
    Call<TempResponse> applyApprove(@Field("id") int id, @Field("status") boolean status);

    /**
     * 辅料申请审批
     *
     * @param id
     * @param status 2为通过
     * @param remark
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "auxiliary/order/approve")
    Call<TempResponse> orderApprove_auxiliary(@Field("id") int id, @Field("status") int status, @Field("remark") String remark);

    /**
     * 产品入库审批
     *
     * @param id
     * @param status 2为通过 1拒绝
     * @param remark
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "productBox/apply/approve")
    Call<TempResponse> applyApprove_productBox(@Field("id") int id, @Field("status") int status, @Field("remark") String remark);

    /**
     * 产品确认审批
     *
     * @param id
     * @param status 3为确认入库
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "productBox/apply/status")
    Call<TempResponse> applyStatus_productBox(@Field("id") int id, @Field("status") int status);

    /**
     * 刀具申请审批
     *
     * @param id
     * @param status 2为通过
     * @param remark
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "deviceTool/order/approve")
    Call<TempResponse> orderApprove_deviceTool(@Field("id") int id, @Field("status") int status, @Field("remark") String remark);

    /**
     * 毛坯发货
     *
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "roughcast/order/send")
    Call<TempResponse> orderSend(@Field("id") int id, @Field("count") int count);

    /**
     * 修改毛坯领用状态
     *
     * @param id
     * @param status 2完成  3取消
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "roughcast/order/status")
    Call<TempResponse> orderStatus(@Field("id") int id, @Field("status") int status);

    /**
     * 修改辅料领用状态
     *
     * @param id
     * @param status:3发货 4完成 5取消
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "auxiliary/order/status")
    Call<TempResponse> orderStatus_auxiliary(@Field("id") int id, @Field("status") int status);

    /**
     * 修改刀具领用状态
     *
     * @param id
     * @param status:3发货 4完成 5取消
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "deviceTool/order/status")
    Call<TempResponse> orderStatus_deviceTool(@Field("id") int id, @Field("status") int status);

    /**
     * 清点产品箱
     *
     * @param id
     * @param status:2清点产品箱
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "productBox/status")
    Call<TempResponse> productBoxStatus(@Field("id") String id, @Field("status") int status);

    /**
     * 创建产品入库清点
     *
     * @param items 箱子ids
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "productBox/apply/create")
    Call<TempResponse> applyCreate_productBox(@Field("items[]") String[] items);

    /**
     * 提交毛坯抽检
     *
     * @return
     */
    @FormUrlEncoded
    @POST(AppConfig.BASE_URL + "roughcast/apply/randomCheck")
    Call<TempResponse> applyRandomCheck(@Field("id") int id, @Field("unqualifiedCount") int unqualifiedCount);

    /**
     * 生产统计中获取分管列表的人
     * @param page
     * @return
     */
    @GET(AppConfig.BASE_URL + "user/list/manager")
    Call<RespCountList> getListManager(@Query("page") int page);

    /**
     * 获取生产统计
     * @param startTime
     * @param endTime
     * @param userId
     * @return
     */
    @GET(AppConfig.BASE_URL + "/machiningParts/getMachiningCountTotal")
    Call<RespCountProductionList> getMachiningCountTotal(@Query("begin") String startTime, @Query("end") String endTime, @Query("userid") int userId);
}

package com.qdxy.app.lhjh.activities.qualityCheck;

import com.lf.tempcore.tempResponse.TempResponse;

/**
 * Created by mac on 2017/1/16.
 */

public class RespQualityDetail extends TempResponse {


    /**
     * data : {"machiningPartsId":1192,"procedureId":1013,"result":true,"remark":"还可以","isResult":true,"resultUserName":"普通工人01","resultTime":"2017-01-16 16:00:40","productCode":"WD1205161229013","creationTime":"2017-01-16 15:48:41","creationUserName":"普通工人01","productLineName":"测试生产线02","procedureName":"line2工序1"}
     * errorCode : null
     * errorMsg : null
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * machiningPartsId : 1192
         * procedureId : 1013
         * result : true
         * remark : 还可以
         * isResult : true
         * resultUserName : 普通工人01
         * resultTime : 2017-01-16 16:00:40
         * productCode : WD1205161229013
         * creationTime : 2017-01-16 15:48:41
         * creationUserName : 普通工人01
         * productLineName : 测试生产线02
         * procedureName : line2工序1
         */

        private int machiningPartsId;
        private int procedureId;
        private boolean result;
        private String remark;
        private boolean isResult;
        private String resultUserName;
        private String resultTime;
        private String productCode;
        private String creationTime;
        private String creationUserName;
        private String productLineName;
        private String procedureName;

        public int getMachiningPartsId() {
            return machiningPartsId;
        }

        public void setMachiningPartsId(int machiningPartsId) {
            this.machiningPartsId = machiningPartsId;
        }

        public int getProcedureId() {
            return procedureId;
        }

        public void setProcedureId(int procedureId) {
            this.procedureId = procedureId;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public boolean isIsResult() {
            return isResult;
        }

        public void setIsResult(boolean isResult) {
            this.isResult = isResult;
        }

        public String getResultUserName() {
            return resultUserName;
        }

        public void setResultUserName(String resultUserName) {
            this.resultUserName = resultUserName;
        }

        public String getResultTime() {
            return resultTime;
        }

        public void setResultTime(String resultTime) {
            this.resultTime = resultTime;
        }

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public String getCreationUserName() {
            return creationUserName;
        }

        public void setCreationUserName(String creationUserName) {
            this.creationUserName = creationUserName;
        }

        public String getProductLineName() {
            return productLineName;
        }

        public void setProductLineName(String productLineName) {
            this.productLineName = productLineName;
        }

        public String getProcedureName() {
            return procedureName;
        }

        public void setProcedureName(String procedureName) {
            this.procedureName = procedureName;
        }
    }
}

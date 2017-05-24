package com.qdxy.app.lhjh.activities.exceptions;

import com.lf.tempcore.tempResponse.TempResponse;

/**
 * Created by KY on 2016/12/2.
 */

public class RespExceptionJudgement extends TempResponse{


    /**
     * data : {"id":3,"applicant":"龙飞","creationTime":"2016-12-09 14:43:24","description":"自检异常报警","department":null,"position":null}
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
         * id : 3
         * applicant : 龙飞
         * creationTime : 2016-12-09 14:43:24
         * description : 自检异常报警
         * department : null
         * position : null
         */

        private int id;
        private String applicant;
        private String creationTime;
        private String description;
        private String department;
        private String position;
        private String productionLineName;
        private String handleType;
        private int productionLineId;

        public String getProductionLineName() {
            return productionLineName;
        }

        public void setProductionLineName(String productionLineName) {
            this.productionLineName = productionLineName;
        }

        public String getHandleType() {
            return handleType;
        }

        public void setHandleType(String handleType) {
            this.handleType = handleType;
        }

        public int getProductionLineId() {
            return productionLineId;
        }

        public void setProductionLineId(int productionLineId) {
            this.productionLineId = productionLineId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getApplicant() {
            return applicant;
        }

        public void setApplicant(String applicant) {
            this.applicant = applicant;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
}

package com.qdxy.app.lhjh.activities.exceptions;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/12/13.
 */

public class RespProblemDetail extends TempResponse{
    /**
     * data : {"id":4,"deviceToolName":"细加工刀具","deviceCode":"sb02","descripiton":"rr","creationTime":"2016-12-12 16:03:25","handleResultList":[{"handlerName":"龙飞","remark":"","handleType":"正常"}]}
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
         * id : 4
         * deviceToolName : 细加工刀具
         * deviceCode : sb02
         * descripiton : rr
         * creationTime : 2016-12-12 16:03:25
         * handleResultList : [{"handlerName":"龙飞","remark":"","handleType":"正常"}]
         */

        private int id;
        private String deviceToolName;
        private String productCode;
        private String deviceCode;
        private String description;
        private String procedureName;
        private String creationTime;
        private String productionLineName;
        private String productionLineId;
        private String machiningPartsId;
        private List<HandleResultListBean> handleResultList;
              /*  "productionLineName": "生产线(01)",
                "procedureName": "清洗",
                "productionLineId": 2,
                "description": "一个月",
                "handleResultList": []*/
      /*  "id": 2,
                "deviceCode": "LXSB-01",
                "creationTime": "2016-12-12 15:58:42",
                "productionLineName": "生产线(01)",
                "procedureName": "清洗",
                "productionLineId": 2,
                "description": "一个月",
                "handleResultList"*/

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getProductionLineId() {
            return productionLineId;
        }

        public void setProductionLineId(String productionLineId) {
            this.productionLineId = productionLineId;
        }

        public String getMachiningPartsId() {
            return machiningPartsId;
        }

        public void setMachiningPartsId(String machiningPartsId) {
            this.machiningPartsId = machiningPartsId;
        }

        public String getProductionLineName() {
            return productionLineName;
        }

        public void setProductionLineName(String productionLineName) {
            this.productionLineName = productionLineName;
        }

        public String getProcedureName() {
            return procedureName;
        }

        public void setProcedureName(String procedureName) {
            this.procedureName = procedureName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDeviceToolName() {
            return deviceToolName;
        }

        public void setDeviceToolName(String deviceToolName) {
            this.deviceToolName = deviceToolName;
        }

        public String getDeviceCode() {
            return deviceCode;
        }

        public void setDeviceCode(String deviceCode) {
            this.deviceCode = deviceCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public List<HandleResultListBean> getHandleResultList() {
            return handleResultList;
        }

        public void setHandleResultList(List<HandleResultListBean> handleResultList) {
            this.handleResultList = handleResultList;
        }

        public static class HandleResultListBean {
            /**
             * handlerName : 龙飞
             * remark :
             * handleType : 正常
             */

            private String handlerName;
            private String remark;
            private String responsiblePersonName;
            private String handleType;

            public String getResponsiblePersonName() {
                return responsiblePersonName;
            }

            public void setResponsiblePersonName(String responsiblePersonName) {
                this.responsiblePersonName = responsiblePersonName;
            }

            public String getHandlerName() {
                return handlerName;
            }

            public void setHandlerName(String handlerName) {
                this.handlerName = handlerName;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getHandleType() {
                return handleType;
            }

            public void setHandleType(String handleType) {
                this.handleType = handleType;
            }
        }
    }
}

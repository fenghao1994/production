package com.qdxy.app.lhjh.activities.exceptions;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**异常数据
 * Created by KY on 2016/12/12.
 */

public class RespExpList extends TempResponse{

    /**
     * data : {"datas":[{"id":4,"sender":"龙飞","deviceToolName":"细加工刀具","procedureName":"清洗","description":"rr","creationTime":"2016-12-12 16:03:25"},{"id":5,"sender":"龙飞","deviceToolName":"细加工刀具","procedureName":"清洗","description":"失败","creationTime":"2016-12-12 16:01:08"}],"size":10,"page":1,"totalCount":2,"totalPage":1}
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
         * datas : [{"id":4,"sender":"龙飞","deviceToolName":"细加工刀具","procedureName":"清洗","description":"rr","creationTime":"2016-12-12 16:03:25"},{"id":5,"sender":"龙飞","deviceToolName":"细加工刀具","procedureName":"清洗","description":"失败","creationTime":"2016-12-12 16:01:08"}]
         * size : 10
         * page : 1
         * totalCount : 2
         * totalPage : 1
         */

        private int size;
        private int page;
        private int totalCount;
        private int totalPage;
        private List<DatasBean> datas;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * id : 4
             * sender : 龙飞
             * deviceToolName : 细加工刀具
             * procedureName : 清洗
             * description : rr
             * creationTime : 2016-12-12 16:03:25
             */

            private int id;
            private String sender;
            private String creationUserName;
            private String productLineName;
            private String deviceToolName;
            private int procedureId;
            private String procedureName;
            private String description;
            private String creationTime;
            private String handleTime;
            private String   productionLineName;  //"productionLineName": "生产线(01)",
            private String   deviceCode;//                  "deviceCode": "LXSB-01",
            private String   productCode;//               "productCode": "WD12456456130",
            private String   machiningPartsId;//               "machiningPartsId": 1015,

            public int getProcedureId() {
                return procedureId;
            }

            public void setProcedureId(int procedureId) {
                this.procedureId = procedureId;
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

            public String getHandleTime() {
                return handleTime;
            }

            public void setHandleTime(String handleTime) {
                this.handleTime = handleTime;
            }

            public String getDeviceCode() {
                return deviceCode;
            }

            public void setDeviceCode(String deviceCode) {
                this.deviceCode = deviceCode;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSender() {
                return sender;
            }

            public void setSender(String sender) {
                this.sender = sender;
            }

            public String getDeviceToolName() {
                return deviceToolName;
            }

            public void setDeviceToolName(String deviceToolName) {
                this.deviceToolName = deviceToolName;
            }

            public String getProcedureName() {
                return procedureName;
            }

            public void setProcedureName(String procedureName) {
                this.procedureName = procedureName;
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
        }
    }
}

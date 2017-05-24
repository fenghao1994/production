package com.qdxy.app.lhjh.activities.storageRoom;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by mac on 2017/2/27.
 */

public class RespMaterialDetail extends TempResponse {
    /**
     * data : {"id":8,"lineName":"测试生产线01","orderNum":"A04201702241106001","applicant":"龙飞","sendTime":null,"applyTime":"2017-02-24 11:06:20","finishTime":null,"status":0,"statusString":"待审批","items":[{"model":"辅料1","count":2},{"model":"辅料2","count":1}],"results":[{"approver":"test1","remark":"test1","status":1,"statusString":"test1"}]}
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
         * id : 8
         * lineName : 测试生产线01
         * orderNum : A04201702241106001
         * applicant : 龙飞
         * sendTime : null
         * applyTime : 2017-02-24 11:06:20
         * finishTime : null
         * status : 0
         * statusString : 待审批
         * items : [{"model":"辅料1","count":2},{"model":"辅料2","count":1}]
         * results : [{"approver":"test1","remark":"test1","status":1,"statusString":"test1"}]
         */

        private int id;
        private String lineName;
        private String orderNum;
        private String applicant;
        private String sendTime;
        private String applyTime;
        private String finishTime;
        private int status;
        private String statusString;
        private List<ItemsBean> items;
        private List<ResultsBean> results;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getApplicant() {
            return applicant;
        }

        public void setApplicant(String applicant) {
            this.applicant = applicant;
        }

        public Object getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public Object getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getStatusString() {
            return statusString;
        }

        public void setStatusString(String statusString) {
            this.statusString = statusString;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class ItemsBean {
            /**
             * model : 辅料1
             * count : 2
             */

            private String model;
            private String batchCode;
            private String time;
            private int size;
            private int count;
            private int id;


            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBatchCode() {
                return batchCode;
            }

            public void setBatchCode(String batchCode) {
                this.batchCode = batchCode;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }

        public static class ResultsBean {
            /**
             * approver : test1
             * remark : test1
             * status : 1
             * statusString : test1
             */

            private String approver;
            private String remark;
            private int status;
            private String statusString;

            public String getApprover() {
                return approver;
            }

            public void setApprover(String approver) {
                this.approver = approver;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStatusString() {
                return statusString;
            }

            public void setStatusString(String statusString) {
                this.statusString = statusString;
            }
        }
    }
}

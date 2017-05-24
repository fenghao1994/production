package com.qdxy.app.lhjh.activities.storageRoom;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by mac on 2017/2/20.
 */

public class RespStorageList extends TempResponse {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


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
             * id : 1
             * applicant : admin
             * approver : admin
             * count : 100
             * model : MP01
             * manufactor : 大厂
             * time : 2017-02-17 17:00:01
             * status : 2
             * statusString : 已入库
             */
            private boolean isSeleted;
            private int id;
            private String applicant;
            private String approver;
            private String productTypeCode;//产品类型
            private String name;//箱子编号
            private String batchCode;//批次号
            private int count;
            private int size;
            private String model;
            private String manufactor;
            private String time;
            private String creationTime;
            private int status;
            private String statusString;
            private String lineName;//生产线名称
            private String orderNum;//单号

            public boolean isSeleted() {
                return isSeleted;
            }

            public void setSeleted(boolean seleted) {
                isSeleted = seleted;
            }

            public String getProductTypeCode() {
                return productTypeCode;
            }

            public void setProductTypeCode(String productTypeCode) {
                this.productTypeCode = productTypeCode;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBatchCode() {
                return batchCode;
            }

            public void setBatchCode(String batchCode) {
                this.batchCode = batchCode;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getCreationTime() {
                return creationTime;
            }

            public void setCreationTime(String creationTime) {
                this.creationTime = creationTime;
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

            public String getApprover() {
                return approver;
            }

            public void setApprover(String approver) {
                this.approver = approver;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public String getManufactor() {
                return manufactor;
            }

            public void setManufactor(String manufactor) {
                this.manufactor = manufactor;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
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

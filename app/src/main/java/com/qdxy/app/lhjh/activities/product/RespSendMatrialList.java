package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**已投料列表数据与加工列表数
 * Created by KY on 2016/11/24.
 */

public class RespSendMatrialList extends TempResponse{

    /**
     * data : {"datas":[{"id":8,"productionBatchCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":0,"creationTime":"2016-11-23 11:45:21","remark":""},{"id":7,"productionBatchCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":0,"creationTime":"2016-11-23 11:20:04","remark":""},{"id":5,"productionBatchCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":0,"creationTime":"2016-11-23 11:11:04","remark":""}],"size":10,"page":1,"totalCount":4,"totalPage":1}
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
         * datas : [{"id":8,"productionBatchCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":0,"creationTime":"2016-11-23 11:45:21","remark":""},{"id":7,"productionBatchCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":0,"creationTime":"2016-11-23 11:20:04","remark":""},{"id":5,"productionBatchCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":0,"creationTime":"2016-11-23 11:11:04","remark":""}]
         * size : 10
         * page : 1
         * totalCount : 4
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
             * id : 8
             * productionBatchCode : CPXH11
             * productCode : CPXH1120161121001
             * procedureNumber : 0
             * creationTime : 2016-11-23 11:45:21
             * remark :
             */

            private int id;
            private String productionBatchCode;
            private String productCode;
            private int procedureNumber;
            private String creationTime;
            private String remark;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProductionBatchCode() {
                return productionBatchCode;
            }

            public void setProductionBatchCode(String productionBatchCode) {
                this.productionBatchCode = productionBatchCode;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public int getProcedureNumber() {
                return procedureNumber;
            }

            public void setProcedureNumber(int procedureNumber) {
                this.procedureNumber = procedureNumber;
            }

            public String getCreationTime() {
                return creationTime;
            }

            public void setCreationTime(String creationTime) {
                this.creationTime = creationTime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}

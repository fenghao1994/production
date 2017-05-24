package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by mac on 2017/2/14.
 */

public class RespProduction2 extends TempResponse {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * datas : [{"id":1199,"productCode":"CP0104161219562","productionBatchCode":"CP0104161219","procedureNumber":3,"procedureId":0,"creationTime":"0001-01-01 00:00:00","remark":null},{"id":1198,"productCode":"CP0104161219561","productionBatchCode":"CP0104161219","procedureNumber":3,"procedureId":0,"creationTime":"0001-01-01 00:00:00","remark":null}]
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
             * id : 1199
             * productCode : CP0104161219562
             * productionBatchCode : CP0104161219
             * procedureNumber : 3
             * procedureId : 0
             * creationTime : 0001-01-01 00:00:00
             * remark : null
             */
            private int id;
            private String productCode;
            private String productionBatchCode;
            private int procedureNumber;
            private int procedureId;
            private String creationTime;
            private String remark;
            private boolean selected;

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public String getProductionBatchCode() {
                return productionBatchCode;
            }

            public void setProductionBatchCode(String productionBatchCode) {
                this.productionBatchCode = productionBatchCode;
            }

            public int getProcedureNumber() {
                return procedureNumber;
            }

            public void setProcedureNumber(int procedureNumber) {
                this.procedureNumber = procedureNumber;
            }

            public int getProcedureId() {
                return procedureId;
            }

            public void setProcedureId(int procedureId) {
                this.procedureId = procedureId;
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

package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/11/28.
 */

public class RespProductList extends TempResponse {
    /**
     * data : {"datas":[{"id":2,"productCode":"CPXH1120161121001","productionBatchCode":"CPXH1120161121"}],"size":10,"page":1,"totalCount":1,"totalPage":1}
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
         * datas : [{"id":2,"productCode":"CPXH1120161121001","productionBatchCode":"CPXH1120161121"}]
         * size : 10
         * page : 1
         * totalCount : 1
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
             * id : 2
             * productCode : CPXH1120161121001
             * productionBatchCode : CPXH1120161121
             */
            private boolean isChecked;
            private int id;
            private String productCode;
            private String productionBatchCode;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
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
        }
    }
}

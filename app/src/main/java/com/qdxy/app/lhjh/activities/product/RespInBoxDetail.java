package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/11/29.
 */

public class RespInBoxDetail extends TempResponse{
    /**
     * data : {"id":1,"productTypeCode":"CPXH11","products":[{"id":2,"productCode":"CPXH1120161121001","productionBatchCode":"CPXH1120161121"}],"creationTime":"2016-11-23 17:05:42","creationUserName":"admin"}
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
         * id : 1
         * productTypeCode : CPXH11
         * products : [{"id":2,"productCode":"CPXH1120161121001","productionBatchCode":"CPXH1120161121"}]
         * creationTime : 2016-11-23 17:05:42
         * creationUserName : admin
         */

        private int id;
        private String productTypeCode;
        private String creationTime;
        private String creationUserName;
        private List<ProductsBean> products;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductTypeCode() {
            return productTypeCode;
        }

        public void setProductTypeCode(String productTypeCode) {
            this.productTypeCode = productTypeCode;
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

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class ProductsBean {
            /**
             * id : 2
             * productCode : CPXH1120161121001
             * productionBatchCode : CPXH1120161121
             */

            private int id;
            private String productCode;
            private String productionBatchCode;

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

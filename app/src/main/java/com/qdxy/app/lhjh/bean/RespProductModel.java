package com.qdxy.app.lhjh.bean;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**产品型号bean
 * Created by KY on 2016/11/22.
 */

public class RespProductModel extends TempResponse {

    /**
     * data : [{"productTypeId":1,"productTypeCode":"CPXH11"}]
     * errorCode : null
     * errorMsg : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * productTypeId : 1
         * productTypeCode : CPXH11
         */
        private boolean isChecked;
        private int productTypeId;
        private String productTypeCode;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public int getProductTypeId() {
            return productTypeId;
        }

        public void setProductTypeId(int productTypeId) {
            this.productTypeId = productTypeId;
        }

        public String getProductTypeCode() {
            return productTypeCode;
        }

        public void setProductTypeCode(String productTypeCode) {
            this.productTypeCode = productTypeCode;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "productTypeId=" + productTypeId +
                    ", productTypeCode='" + productTypeCode + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RespProductModel{" +
                "data=" + data +
                '}';
    }
}

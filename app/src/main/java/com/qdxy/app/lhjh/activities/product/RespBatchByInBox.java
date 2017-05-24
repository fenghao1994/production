package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/11/24.
 */

public class RespBatchByInBox extends TempResponse{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private boolean isChecked;
        private int id;
        private int productionLineId;
        private String name;
        private String code;
        private int status;
        private int productTypeId;
        private String productTypeCode;
        private String creationTime;
        private int number;//1为投料工序
        private int type;//0：普通工序 1：加工工序



        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
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

        public int getProductionLineId() {
            return productionLineId;
        }

        public void setProductionLineId(int productionLineId) {
            this.productionLineId = productionLineId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }
    }
}

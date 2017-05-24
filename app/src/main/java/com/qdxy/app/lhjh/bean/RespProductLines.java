package com.qdxy.app.lhjh.bean;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**c
 * Created by KY on 2016/11/22.
 */

public class RespProductLines extends TempResponse{

    /**
     * data : [{"productionLineName":"测试生产线1","productionLineId":1},{"productionLineName":"测试生产线2","productionLineId":2},{"productionLineName":"测试生产线3","productionLineId":3},{"productionLineName":"测试生产线4","productionLineId":4}]
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
         * productionLineName : 测试生产线1
         * productionLineId : 1
         */
        private boolean checked;
        private String name;
        private int id;
        private String code;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String productionLineName) {
            this.name = productionLineName;
        }

        public int getId() {
            return id;
        }

        public void setId(int productionLineId) {
            this.id = productionLineId;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "productionLineName='" + name + '\'' +
                    ", productionLineId=" + id +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RespProductLines{" +
                "data=" + data +
                '}';
    }
}

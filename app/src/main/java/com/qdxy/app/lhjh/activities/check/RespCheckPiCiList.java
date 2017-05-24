package com.qdxy.app.lhjh.activities.check;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**产品抽检批次列表数据
 * Created by KY on 2016/11/30.
 */

public class RespCheckPiCiList extends TempResponse {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String productionBatchCode;
        private int waitCheckCount;
        private double checkPercentage;
        private int checkCount;
        private int needCheckCount;

        public String getProductionBatchCode() {
            return productionBatchCode;
        }

        public void setProductionBatchCode(String productionBatchCode) {
            this.productionBatchCode = productionBatchCode;
        }

        public int getWaitCheckCount() {
            return waitCheckCount;
        }

        public void setWaitCheckCount(int waitCheckCount) {
            this.waitCheckCount = waitCheckCount;
        }

        public double getCheckPercentage() {
            return checkPercentage;
        }

        public void setCheckPercentage(double checkPercentage) {
            this.checkPercentage = checkPercentage;
        }

        public int getCheckCount() {
            return checkCount;
        }

        public void setCheckCount(int checkCount) {
            this.checkCount = checkCount;
        }

        public int getNeedCheckCount() {
            return needCheckCount;
        }

        public void setNeedCheckCount(int needCheckCount) {
            this.needCheckCount = needCheckCount;
        }
    }
}

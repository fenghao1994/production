package com.qdxy.app.lhjh.activities.management;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**批次管理列表bean
 * Created by KY on 2016/11/22.
 */

public class RespManagePiCi extends TempResponse{

    /**
     * data : {"datas":[{"time":"2016-11-22 14:58:47","status":2,"productionBatchCode":"测试批次0","productionBatchId":0},{"time":"2016-11-22 14:58:47","status":2,"productionBatchCode":"测试批次1","productionBatchId":1},{"time":"2016-11-22 14:58:47","status":2,"productionBatchCode":"测试批次2","productionBatchId":2},{"time":"2016-11-22 14:58:47","status":2,"productionBatchCode":"测试批次3","productionBatchId":3},{"time":"2016-11-22 14:58:47","status":2,"productionBatchCode":"测试批次4","productionBatchId":4},{"time":"2016-11-22 14:58:47","status":2,"productionBatchCode":"测试批次5","productionBatchId":5},{"time":"2016-11-22 14:58:47","status":2,"productionBatchCode":"测试批次6","productionBatchId":6},{"time":"2016-11-22 14:58:47","status":2,"productionBatchCode":"测试批次7","productionBatchId":7},{"time":"2016-11-22 14:58:47","status":2,"productionBatchCode":"测试批次8","productionBatchId":8},{"time":"2016-11-22 14:58:47","status":2,"productionBatchCode":"测试批次9","productionBatchId":9}],"size":10,"page":1,"totalCount":100,"totalPage":10}
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
             * time : 2016-11-22 14:58:47
             * status : 2
             * productionBatchCode : 测试批次0
             * productionBatchId : 0
             */

            private String creationTime;
            private int status;
            private String code;
            private String productionLineName;
            private int id;

            public String getProductionLineName() {
                return productionLineName;
            }

            public void setProductionLineName(String productionLineName) {
                this.productionLineName = productionLineName;
            }

            public String getCreationTime() {
                return creationTime;
            }

            public void setCreationTime(String time) {
                this.creationTime = time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String productionBatchCode) {
                this.code = productionBatchCode;
            }

            public int getId() {
                return id;
            }

            public void setId(int productionBatchId) {
                this.id = productionBatchId;
            }
        }
    }
}

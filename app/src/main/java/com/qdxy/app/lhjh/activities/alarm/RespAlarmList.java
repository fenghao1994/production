package com.qdxy.app.lhjh.activities.alarm;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**报警列表数据
 * Created by KY on 2016/12/7.
 */

public class RespAlarmList extends TempResponse{
    /**
     * data : {"datas":[{"id":2,"productionLineId":2,"name":"清洗","number":2,"creationTime":"2016-12-01","isStartedAlarm":true},{"id":1,"productionLineId":2,"name":"粗加工","number":1,"creationTime":"2016-12-01","isStartedAlarm":true}],"size":10,"page":0,"totalCount":2,"totalPage":1}
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
         * datas : [{"id":2,"productionLineId":2,"name":"清洗","number":2,"creationTime":"2016-12-01","isStartedAlarm":true},{"id":1,"productionLineId":2,"name":"粗加工","number":1,"creationTime":"2016-12-01","isStartedAlarm":true}]
         * size : 10
         * page : 0
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
             * id : 2
             * productionLineId : 2
             * name : 清洗
             * number : 2
             * creationTime : 2016-12-01
             * isStartedAlarm : true
             */
            private boolean isChecked;
            private int id;
            private int productionLineId;
            private String name;
            private int number;
            private String creationTime;
            private boolean isStartedAlarm;

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

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getCreationTime() {
                return creationTime;
            }

            public void setCreationTime(String creationTime) {
                this.creationTime = creationTime;
            }

            public boolean isIsStartedAlarm() {
                return isStartedAlarm;
            }

            public void setIsStartedAlarm(boolean isStartedAlarm) {
                this.isStartedAlarm = isStartedAlarm;
            }
        }
    }
}

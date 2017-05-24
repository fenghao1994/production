package com.qdxy.app.lhjh.activities.arrangeProcedure;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/12/7.
 */

public class RespArrangeProList extends TempResponse{
    /**
     * data : {"datas":[{"userName":"DLC123","lineName":"生产线(lf)","procedureName":"粗加工、清洗"},{"userName":"longfei","lineName":"生产线(lf)","procedureName":"粗加工、清洗"}],"size":10,"page":1,"totalCount":2,"totalPage":1}
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
         * datas : [{"userName":"DLC123","lineName":"生产线(lf)","procedureName":"粗加工、清洗"},{"userName":"longfei","lineName":"生产线(lf)","procedureName":"粗加工、清洗"}]
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
             * userName : DLC123
             * lineName : 生产线(lf)
             * procedureName : 粗加工、清洗
             *
             */
            private boolean isChecked;
            private String lineId;
            private String userId;
            private String userName;
            private String lineName;
            private String procedureName;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getLineId() {
                return lineId;
            }

            public void setLineId(String lineId) {
                this.lineId = lineId;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getLineName() {
                return lineName;
            }

            public void setLineName(String lineName) {
                this.lineName = lineName;
            }

            public String getProcedureName() {
                return procedureName;
            }

            public void setProcedureName(String procedureName) {
                this.procedureName = procedureName;
            }
        }
    }
}

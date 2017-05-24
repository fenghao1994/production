package com.qdxy.app.lhjh.activities.exceptions;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/12/2.
 */

public class RespExceptionJudgementList extends TempResponse{


    /**
     * data : {"datas":[{"id":1,"sender":"龙飞","procedureName":"粗加工","creationTime":"2016-12-09 14:17:10","description":"","isJudge":false},{"id":2,"sender":"龙飞","procedureName":"粗加工","creationTime":"2016-12-09 14:26:09","description":"","isJudge":true}],"size":10,"page":1,"totalCount":2,"totalPage":1}
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
         * datas : [{"id":1,"sender":"龙飞","procedureName":"粗加工","creationTime":"2016-12-09 14:17:10","description":"","isJudge":false},{"id":2,"sender":"龙飞","procedureName":"粗加工","creationTime":"2016-12-09 14:26:09","description":"","isJudge":true}]
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
             * id : 1
             * sender : 龙飞
             * procedureName : 粗加工
             * creationTime : 2016-12-09 14:17:10
             * description :
             * isJudge : false
             */

            private int id;
            private String sender;
            private String procedureName;
            private String creationTime;
            private String judgeTime;
            private String description;
            private String productionLineName;
            private boolean isJudge;

            public String getJudgeTime() {
                return judgeTime;
            }

            public void setJudgeTime(String judgeTime) {
                this.judgeTime = judgeTime;
            }

            public String getProductionLineName() {
                return productionLineName;
            }

            public void setProductionLineName(String productionLineName) {
                this.productionLineName = productionLineName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSender() {
                return sender;
            }

            public void setSender(String sender) {
                this.sender = sender;
            }

            public String getProcedureName() {
                return procedureName;
            }

            public void setProcedureName(String procedureName) {
                this.procedureName = procedureName;
            }

            public String getCreationTime() {
                return creationTime;
            }

            public void setCreationTime(String creationTime) {
                this.creationTime = creationTime;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public boolean isIsJudge() {
                return isJudge;
            }

            public void setIsJudge(boolean isJudge) {
                this.isJudge = isJudge;
            }
        }
    }
}

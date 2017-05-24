package com.qdxy.app.lhjh.activities.storageRoom;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by mac on 2017/2/28.
 */

public class RespApprovePrdList extends TempResponse{



    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * datas : [{"id":2,"applicant":"admin","time":"2017-02-27 14:53:10","status":3,"statusString":"已入库"},{"id":1,"applicant":"admin","time":"2017-02-24 14:51:18","status":1,"statusString":"已拒绝"},{"id":3,"applicant":"龙飞","time":"2017-02-28 11:39:52","status":0,"statusString":"待审批"}]
         * size : 10
         * page : 1
         * totalCount : 3
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

            private int id;
            private String applicant;
            private String time;
            private int status;
            private String statusString;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getApplicant() {
                return applicant;
            }

            public void setApplicant(String applicant) {
                this.applicant = applicant;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getStatusString() {
                return statusString;
            }

            public void setStatusString(String statusString) {
                this.statusString = statusString;
            }
        }
    }
}

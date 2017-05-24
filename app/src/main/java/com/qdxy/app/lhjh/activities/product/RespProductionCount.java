package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by mac on 2017/2/15.
 */

public class RespProductionCount extends TempResponse{


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * datas : [{"id":"加工工序01","name":17},{"id":"加工工序02","name":6},{"id":"加工工序03","name":4}]
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
            /**
             * id : 加工工序01
             * name : 17
             */

            private String id;
            private int name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getName() {
                return name;
            }

            public void setName(int name) {
                this.name = name;
            }
        }
    }
}

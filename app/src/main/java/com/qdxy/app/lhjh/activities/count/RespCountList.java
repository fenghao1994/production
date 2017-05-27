package com.qdxy.app.lhjh.activities.count;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by fenghao on 2017/5/25.
 */
//分管的人员实体
public class RespCountList extends TempResponse {


    /**
     * data : {"datas":[{"id":1,"name":"admin"},{"id":15,"name":"何川"},{"id":45,"name":"柳刚"},{"id":53,"name":"李德军"}],"size":10,"page":1,"totalCount":4,"totalPage":1}
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
         * datas : [{"id":1,"name":"admin"},{"id":15,"name":"何川"},{"id":45,"name":"柳刚"},{"id":53,"name":"李德军"}]
         * size : 10
         * page : 1
         * totalCount : 4
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
             * name : admin
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}

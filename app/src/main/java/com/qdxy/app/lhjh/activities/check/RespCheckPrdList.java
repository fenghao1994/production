package com.qdxy.app.lhjh.activities.check;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/11/30.
 */

public class RespCheckPrdList extends TempResponse{


    /**
     * data : {"datas":[{"id":12,"productCode":"CPXH1102061129507","selfCheckList":"admin(工序1)","randomCheckList":""},{"id":11,"productCode":"CPXH1102061129506","selfCheckList":"admin(工序1)、admin(工序1)","randomCheckList":""}],"size":10,"page":1,"totalCount":2,"totalPage":1}
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
         * datas : [{"id":12,"productCode":"CPXH1102061129507","selfCheckList":"admin(工序1)","randomCheckList":""},{"id":11,"productCode":"CPXH1102061129506","selfCheckList":"admin(工序1)、admin(工序1)","randomCheckList":""}]
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
             * id : 12
             * productCode : CPXH1102061129507
             * selfCheckList : admin(工序1)
             * randomCheckList :
             */

            private int id;
            private String productCode;
            private String selfCheckList;
            private String randomCheckList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getProductCode() {
                return productCode;
            }

            public void setProductCode(String productCode) {
                this.productCode = productCode;
            }

            public String getSelfCheckList() {
                return selfCheckList;
            }

            public void setSelfCheckList(String selfCheckList) {
                this.selfCheckList = selfCheckList;
            }

            public String getRandomCheckList() {
                return randomCheckList;
            }

            public void setRandomCheckList(String randomCheckList) {
                this.randomCheckList = randomCheckList;
            }
        }
    }
}

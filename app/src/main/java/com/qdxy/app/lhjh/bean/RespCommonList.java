package com.qdxy.app.lhjh.bean;

import com.lf.tempcore.tempResponse.TempResponse;
import com.qdxy.app.lhjh.activities.product.RespProductionList;

import java.util.List;

/**
 * Created by mac on 2016/12/28.
 */

public class RespCommonList extends TempResponse {
    /**
     * data : {"datas":[{"groupName":"1","list":[{"id":1,"productTypeCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":1,"creationTime":"0001-01-01 00:00:00","remark":null},{"id":5,"productTypeCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":1,"creationTime":"0001-01-01 00:00:00","remark":null}]}],"size":10,"page":1,"totalCount":1,"totalPage":1}
     * errorCode : null
     * errorMsg : null
     */

    private RespProductionList.DataBean data;

    public RespProductionList.DataBean getData() {
        return data;
    }

    public void setData(RespProductionList.DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * datas : [{"groupName":"1","list":[{"id":1,"productTypeCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":1,"creationTime":"0001-01-01 00:00:00","remark":null},{"id":5,"productTypeCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":1,"creationTime":"0001-01-01 00:00:00","remark":null}]}]
         * size : 10
         * page : 1
         * totalCount : 1
         * totalPage : 1
         */

        private int size;
        private int page;
        private int totalCount;
        private int totalPage;
        private List<RespProductionList.DataBean.DatasBean> datas;

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

        public List<RespProductionList.DataBean.DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<RespProductionList.DataBean.DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * groupName : 1
             * list : [{"id":1,"productTypeCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":1,"creationTime":"0001-01-01 00:00:00","remark":null},{"id":5,"productTypeCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":1,"creationTime":"0001-01-01 00:00:00","remark":null}]
             */

            private String groupName;
            private List<RespProductionList.DataBean.DatasBean.ListBean> list;

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public List<RespProductionList.DataBean.DatasBean.ListBean> getList() {
                return list;
            }

            public void setList(List<RespProductionList.DataBean.DatasBean.ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * id : 1
                 * productTypeCode : CPXH11
                 * productCode : CPXH1120161121001
                 * procedureNumber : 1
                 * creationTime : 0001-01-01 00:00:00
                 * remark : null
                 */

                private int id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }
    }
}

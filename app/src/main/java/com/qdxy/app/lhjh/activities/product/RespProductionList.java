package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**工序工件列表
 * Created by KY on 2016/11/24.
 */

public class RespProductionList extends TempResponse{
    /**
     * data : {"datas":[{"groupName":"1","list":[{"id":1,"productTypeCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":1,"creationTime":"0001-01-01 00:00:00","remark":null},{"id":5,"productTypeCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":1,"creationTime":"0001-01-01 00:00:00","remark":null}]}],"size":10,"page":1,"totalCount":1,"totalPage":1}
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
             * groupName : 1
             * list : [{"id":1,"productTypeCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":1,"creationTime":"0001-01-01 00:00:00","remark":null},{"id":5,"productTypeCode":"CPXH11","productCode":"CPXH1120161121001","procedureNumber":1,"creationTime":"0001-01-01 00:00:00","remark":null}]
             */

            private String groupName;
            private List<ListBean> list;

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
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
                private String productTypeCode;
                private String productCode;
                private int procedureNumber;
                private String creationTime;
                private String remark;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getProductTypeCode() {
                    return productTypeCode;
                }

                public void setProductTypeCode(String productTypeCode) {
                    this.productTypeCode = productTypeCode;
                }

                public String getProductCode() {
                    return productCode;
                }

                public void setProductCode(String productCode) {
                    this.productCode = productCode;
                }

                public int getProcedureNumber() {
                    return procedureNumber;
                }

                public void setProcedureNumber(int procedureNumber) {
                    this.procedureNumber = procedureNumber;
                }

                public String getCreationTime() {
                    return creationTime;
                }

                public void setCreationTime(String creationTime) {
                    this.creationTime = creationTime;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }
            }
        }
    }
}

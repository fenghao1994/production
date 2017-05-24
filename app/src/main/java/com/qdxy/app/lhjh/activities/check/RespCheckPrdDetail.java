package com.qdxy.app.lhjh.activities.check;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/11/30.
 */

public class RespCheckPrdDetail extends TempResponse{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String productCode;
        private boolean isRandomCheck;
        private int machiningPartsId;
        private List<RandomCheckListBean> randomCheckList;
        private List<SelfCheckListBean> selfCheckList;


        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public boolean isRandomCheck() {
            return isRandomCheck;
        }

        public void setRandomCheck(boolean randomCheck) {
            isRandomCheck = randomCheck;
        }

        public int getMachiningPartsId() {
            return machiningPartsId;
        }

        public void setMachiningPartsId(int machiningPartsId) {
            this.machiningPartsId = machiningPartsId;
        }

        public List<RandomCheckListBean> getRandomCheckList() {
            return randomCheckList;
        }

        public void setRandomCheckList(List<RandomCheckListBean> randomCheckList) {
            this.randomCheckList = randomCheckList;
        }

        public List<SelfCheckListBean> getSelfCheckList() {
            return selfCheckList;
        }

        public void setSelfCheckList(List<SelfCheckListBean> selfCheckList) {
            this.selfCheckList = selfCheckList;
        }

        public static class RandomCheckListBean {
            /**
             * checkerName : admin
             * responsiblePersonName : admin
             * result : 1
             * resultString : 工废
             */

            private String checkerName;
            private String creationTime;
            private String responsiblePersonName;
            private int result;
            private String resultString;

            public String getCreationTime() {
                return creationTime;
            }

            public void setCreationTime(String creationTime) {
                this.creationTime = creationTime;
            }

            public String getCheckerName() {
                return checkerName;
            }

            public void setCheckerName(String checkerName) {
                this.checkerName = checkerName;
            }

            public String getResponsiblePersonName() {
                return responsiblePersonName;
            }

            public void setResponsiblePersonName(String responsiblePersonName) {
                this.responsiblePersonName = responsiblePersonName;
            }

            public int getResult() {
                return result;
            }

            public void setResult(int result) {
                this.result = result;
            }

            public String getResultString() {
                return resultString;
            }

            public void setResultString(String resultString) {
                this.resultString = resultString;
            }

            @Override
            public String toString() {
                return "RandomCheckListBean{" +
                        "checkerName='" + checkerName + '\'' +
                        ", responsiblePersonName='" + responsiblePersonName + '\'' +
                        ", result=" + result +
                        ", resultString='" + resultString + '\'' +
                        '}';
            }
        }

        public static class SelfCheckListBean {
            /**
             * procedureName : 工序4
             * checkerName : admin
             * result : 0
             * resultString : 正常
             */

            private String procedureName;
            private String checkerName;
            private String creationTime;
            private int result;
            private String resultString;

            public String getCreationTime() {
                return creationTime;
            }

            public void setCreationTime(String creationTime) {
                this.creationTime = creationTime;
            }

            public String getProcedureName() {
                return procedureName;
            }

            public void setProcedureName(String procedureName) {
                this.procedureName = procedureName;
            }

            public String getCheckerName() {
                return checkerName;
            }

            public void setCheckerName(String checkerName) {
                this.checkerName = checkerName;
            }

            public int getResult() {
                return result;
            }

            public void setResult(int result) {
                this.result = result;
            }

            public String getResultString() {
                return resultString;
            }

            public void setResultString(String resultString) {
                this.resultString = resultString;
            }

            @Override
            public String toString() {
                return "SelfCheckListBean{" +
                        "procedureName='" + procedureName + '\'' +
                        ", checkerName='" + checkerName + '\'' +
                        ", result=" + result +
                        ", resultString='" + resultString + '\'' +
                        '}';
            }
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "productCode='" + productCode + '\'' +
                    ", isRandomCheck=" + isRandomCheck +
                    ", machiningPartsId=" + machiningPartsId +
                    ", randomCheckList=" + randomCheckList +
                    ", selfCheckList=" + selfCheckList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RespCheckPrdDetail{" +
                "data=" + data +
                '}';
    }
}

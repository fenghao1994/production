package com.qdxy.app.lhjh.activities.storageRoom;

import com.lf.tempcore.tempResponse.TempResponse;

/**
 * Created by mac on 2017/2/20.
 */

public class RespStorageDetail extends TempResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int id;
        private String applicant;
        private String approver;
        private int count;
        private String model;
        private String manufactor;
        private String randomCheckTime;
        private String applyTime;
        private String approveTime;
        private int unqualifiedCount;
        private int status;
        private String statusString;
        private String lineName;
        private String expectedSendTime;//期望发货时间
        private String sendTime;//发货时间
        private String finishTime;//完成时间
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public String getExpectedSendTime() {
            return expectedSendTime;
        }

        public void setExpectedSendTime(String expectedSendTime) {
            this.expectedSendTime = expectedSendTime;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getFinishTime() {
            return finishTime;
        }

        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

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

        public String getApprover() {
            return approver;
        }

        public void setApprover(String approver) {
            this.approver = approver;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getManufactor() {
            return manufactor;
        }

        public void setManufactor(String manufactor) {
            this.manufactor = manufactor;
        }

        public String getRandomCheckTime() {
            return randomCheckTime;
        }

        public void setRandomCheckTime(String randomCheckTime) {
            this.randomCheckTime = randomCheckTime;
        }

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getApproveTime() {
            return approveTime;
        }

        public void setApproveTime(String approveTime) {
            this.approveTime = approveTime;
        }

        public int getUnqualifiedCount() {
            return unqualifiedCount;
        }

        public void setUnqualifiedCount(int unqualifiedCount) {
            this.unqualifiedCount = unqualifiedCount;
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

package com.qdxy.app.lhjh.activities.login;

import com.lf.tempcore.tempResponse.TempResponse;

/**
 * Created by KY on 2016/11/17.
 */

public class RespLogin  extends TempResponse{



    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 10
         * userName : test
         * token : token
         */

        private int id;
        private String userName;
        private String token;
        private int workStatus;

        public int getWorkStatus() {
            return workStatus;
        }

        public void setWorkStatus(int workStatus) {
            this.workStatus = workStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

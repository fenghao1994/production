package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempResponse.TempResponse;

/**
 * Created by mac on 2017/2/15.
 */

public class RespFeedNum extends TempResponse {

    /**
     * data : {"number":1,"length":3}
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
         * number : 1
         * length : 3
         */

        private int number;
        private int length;


        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }
}

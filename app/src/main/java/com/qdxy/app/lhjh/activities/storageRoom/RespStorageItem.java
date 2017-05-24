package com.qdxy.app.lhjh.activities.storageRoom;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by mac on 2017/2/20.
 */

public class RespStorageItem extends TempResponse{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * name : 大厂
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

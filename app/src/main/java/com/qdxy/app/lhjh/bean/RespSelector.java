package com.qdxy.app.lhjh.bean;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/12/1.
 */

public class RespSelector extends TempResponse{


    /**
     * data : {"datas":[{"id":3,"name":"龙飞"}],"size":10,"page":1,"totalCount":2,"totalPage":1}
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
         * datas : [{"id":3,"name":"龙飞"}]
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
             * id : 3
             * name : 龙飞
             */
            private boolean isChecked;
            private int id;
            private String name;
            private int deviceToolTypeId;
            private String deviceToolTypeName;
            private int deviceId;
            private int currentDurability;
            private int maxDurability;
            private int status;
            private String startTime;
            private String endTime;
            private String creationTime;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getDeviceToolTypeName() {
                return deviceToolTypeName;
            }

            public void setDeviceToolTypeName(String deviceToolTypeName) {
                this.deviceToolTypeName = deviceToolTypeName;
            }

            public int getDeviceToolTypeId() {
                return deviceToolTypeId;
            }

            public void setDeviceToolTypeId(int deviceToolTypeId) {
                this.deviceToolTypeId = deviceToolTypeId;
            }

            public int getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(int deviceId) {
                this.deviceId = deviceId;
            }

            public int getCurrentDurability() {
                return currentDurability;
            }

            public void setCurrentDurability(int currentDurability) {
                this.currentDurability = currentDurability;
            }

            public int getMaxDurability() {
                return maxDurability;
            }

            public void setMaxDurability(int maxDurability) {
                this.maxDurability = maxDurability;
            }

           /* public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
*/
            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getCreationTime() {
                return creationTime;
            }

            public void setCreationTime(String creationTime) {
                this.creationTime = creationTime;
            }

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

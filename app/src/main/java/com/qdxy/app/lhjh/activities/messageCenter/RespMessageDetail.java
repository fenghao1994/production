package com.qdxy.app.lhjh.activities.messageCenter;

import com.lf.tempcore.tempResponse.TempResponse;

/**
 * Created by KY on 2016/12/22.
 */

public class RespMessageDetail extends TempResponse {
    /**
     * data : {"Id":1,"SenderName":"龙飞","Title":"加工超时异常","Content":"ee","CreationTime":"2016-12-12"}
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
         * Id : 1
         * SenderName : 龙飞
         * Title : 加工超时异常
         * Content : ee
         * CreationTime : 2016-12-12
         */

        private int Id;
        private String SenderName;
        private String Title;
        private String Content;
        private String CreationTime;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getSenderName() {
            return SenderName;
        }

        public void setSenderName(String SenderName) {
            this.SenderName = SenderName;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getCreationTime() {
            return CreationTime;
        }

        public void setCreationTime(String CreationTime) {
            this.CreationTime = CreationTime;
        }
    }
}

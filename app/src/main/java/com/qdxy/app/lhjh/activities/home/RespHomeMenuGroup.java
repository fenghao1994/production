package com.qdxy.app.lhjh.activities.home;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/11/21.
 */

public class RespHomeMenuGroup extends TempResponse{


    /**
     * data : [{"title":"生产管理","icon":null,"activity":null,"children":[{"title":"产品抽检","icon":"","activity":"","children":[]}]},{"title":"生产加工","icon":null,"activity":null,"children":[{"title":"产品加工","icon":"","activity":"","children":[]}]}]
     * isSuccess : true
     * errorCode : null
     * errorMsg : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String title;

        private List<RespMenuChild> children;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public List<RespMenuChild> getChildren() {
            return children;
        }

        public void setChildren(List<RespMenuChild> children) {
            this.children = children;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "title='" + title + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RespHomeMenuGroup{" +
                "data=" + data +
                '}';
    }
}

package com.qdxy.app.lhjh.activities.home;

/**
 * Created by KY on 2016/11/21.
 */

public class RespMenuChild {
    /**
     * title : 生产管理
     * icon : null
     * activity : null
     * children : [{"title":"产品抽检","icon":"","activity":"","children":[]}]
     */

    private String title;
    private String icon;
    private String activity;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }


    @Override
    public String toString() {
        return "RespMenuChild{" +
                "title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", activity='" + activity + '\'' +
                '}';
    }
}

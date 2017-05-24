package com.qdxy.app.lhjh.activities.onOffLine;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**返工工序列表数据
 * Created by KY on 2016/12/6.
 */

public class RespProcedureList extends TempResponse{
    /**
     * data : [{"id":1,"name":"粗加工"},{"id":2,"name":"清洗"}]
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
        /**
         * id : 1
         * name : 粗加工
         */
        private boolean isChecked;
        private int id;
        private String name;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
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

package com.qdxy.app.lhjh.activities.count;

import com.lf.tempcore.tempResponse.TempResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fenghao on 2017/5/27.
 */
//根据id查询具体工序的统计的实体
public class RespCountProductionList extends TempResponse implements Serializable {


    /**
     * data : [{"name":"毛坯编号","machList":["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"],"machineWaste":["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"],"materialWaste":["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"],"robotWaste":["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"]},{"name":"铣左右悬","machList":["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"],"machineWaste":["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"],"materialWaste":["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"],"robotWaste":["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"]}]
     * errorCode : null
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * name : 毛坯编号
         * machList : ["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"]
         * machineWaste : ["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"]
         * materialWaste : ["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"]
         * robotWaste : ["DK10-05170518001","DK10-05170518002","DK10-05170518003","DK10-05170518005","DK10-05170518004","DK10-05170518006","DK10-05170518007"]
         */

        private String name;
        private List<String> machList;
        private List<String> machineWaste;
        private List<String> materialWaste;
        private List<String> robotWaste;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getMachList() {
            return machList;
        }

        public void setMachList(List<String> machList) {
            this.machList = machList;
        }

        public List<String> getMachineWaste() {
            return machineWaste;
        }

        public void setMachineWaste(List<String> machineWaste) {
            this.machineWaste = machineWaste;
        }

        public List<String> getMaterialWaste() {
            return materialWaste;
        }

        public void setMaterialWaste(List<String> materialWaste) {
            this.materialWaste = materialWaste;
        }

        public List<String> getRobotWaste() {
            return robotWaste;
        }

        public void setRobotWaste(List<String> robotWaste) {
            this.robotWaste = robotWaste;
        }
    }
}

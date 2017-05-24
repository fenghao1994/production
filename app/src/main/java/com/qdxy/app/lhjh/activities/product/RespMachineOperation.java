package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempResponse.TempResponse;

/**
 * Created by KY on 2016/11/24.
 */

public class RespMachineOperation extends TempResponse {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * canSendMaterial : true
         * canInBox : true
         * canMachine : true
         */

        private boolean canSendMaterial;
        private boolean canInBox;
        private boolean canMachine;

        public boolean isCanSendMaterial() {
            return canSendMaterial;
        }

        public void setCanSendMaterial(boolean canSendMaterial) {
            this.canSendMaterial = canSendMaterial;
        }

        public boolean isCanInBox() {
            return canInBox;
        }

        public void setCanInBox(boolean canInBox) {
            this.canInBox = canInBox;
        }

        public boolean isCanMachine() {
            return canMachine;
        }

        public void setCanMachine(boolean canMachine) {
            this.canMachine = canMachine;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "canSendMaterial=" + canSendMaterial +
                    ", canInBox=" + canInBox +
                    ", canMachine=" + canMachine +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RespMachineOperation{" +
                "data=" + data +
                '}';
    }
}

package com.qdxy.app.lhjh.emues;

/**
 * Created by KY on 2016/12/16.
 */

public enum  MessageType {
    /**
     * 工件异常
     */
    EXCEPTION_GONGJIAN(0),
    /**
     * 设备异常
     */
    EXCEPTION_EQUP(1),
    /**
     * 刀具异常
     */
    EXCEPTION_TOOL(2),
    /**
     * 处理超时
     */
    DEAL_OUT_OF_TIME(4),
    /**
     * 超市报警异常
     */
    ALARM_OUT_OF_TIME(3);
    private int code;

    MessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public MessageType getMessageType(int code){
        switch (code) {
            case 0 :
                return EXCEPTION_GONGJIAN;
            case 1:
                return EXCEPTION_EQUP;
            case 2:
                return EXCEPTION_TOOL;
            case 3:
                return DEAL_OUT_OF_TIME;
            case 4:
                return ALARM_OUT_OF_TIME;

        }
        return null;
    }
}

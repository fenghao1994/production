package com.lf.tempcore.tempResponse;

/**
 * Created by longf on 2016/1/25.
 */
public class TempResponse {

    private String errorCode;
    private boolean isSuccess;
    private String errorMsg;
    private String message;
    private String detailMessage;


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "TempResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", isSuccess='" + isSuccess + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", message='" + message + '\'' +
                ", detailMessage='" + detailMessage + '\'' +
                '}';
    }
}

package com.qdxy.app.lhjh.activities.exceptions;

import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;

/**
 * Created by KY on 2016/12/2.
 */

public interface ViewExceptionI extends TempViewI {
     void onJudgementDetailSucceed(RespExceptionJudgement data);
     void onOnProblemSucceed(String data);
     void onDaojuDetailSucceed(RespProblemDetail data);
     void onDaojuHandeSucceed();
}

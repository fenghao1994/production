package com.qdxy.app.lhjh.bean;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/11/22.
 */

public class RespPermission  extends TempResponse{
    /**
     * data : ["App.MachiningParts","App.MachiningParts.Create","App.MachiningParts.Remove","App.MachiningParts.Update","App.Organization","App.Organization.AddUser","App.Organization.Create","App.Organization.Delete","App.Organization.Migrate","App.Organization.RemoveUser","App.Organization.Rename","App.Product","App.Product.Create","App.Product.Remove","App.Product.Update","App.ProductBox","App.ProductBox.Create","App.ProductBox.Remove","App.ProductBox.Update","App.ProductType","App.ProductType.Create","App.ProductType.Remove","App.ProductType.Update","App.RandomCheckRule","App.RandomCheckRule.Create","App.RandomCheckRule.Remove","App.RandomCheckRule.Update","App.RandomCheckTask","App.RandomCheckTask.Create","App.RandomCheckTask.Remove","App.RandomCheckTask.Update","App.Role","App.SystemConfig","App.SystemConfig.Create","App.SystemConfig.Remove","App.SystemConfig.Update","App.User"]
     * errorCode : null
     * errorMsg : null
     */

    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}

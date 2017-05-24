package com.qdxy.app.lhjh.activities.storageRoom;

import java.util.List;

/**
 * Created by mac on 2017/2/23.
 */

public class RequestBodyMaterial {
    private int productionLineId;
    private List<ItemsBody> items;

    public int getProductionLineId() {
        return productionLineId;
    }

    public void setProductionLineId(int productionLineId) {
        this.productionLineId = productionLineId;
    }

    public List<ItemsBody> getItems() {
        return items;
    }

    public void setItems(List<ItemsBody> items) {
        this.items = items;
    }

    public static class ItemsBody{
        private int modelId;
        private int count;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getModelId() {
            return modelId;
        }

        public void setModelId(int modelId) {
            this.modelId = modelId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}

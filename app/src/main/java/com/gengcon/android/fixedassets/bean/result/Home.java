package com.gengcon.android.fixedassets.bean.result;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Home extends InvalidBean implements Serializable {

    /**
     * 0 : {"name":"所有","value":4726}
     * 1 : {"name":"闲置","value":4726}
     * 2 : {"name":"在用","value":0}
     * 3 : {"name":"报废","value":0}
     * 4 : {"name":"维修","value":0}
     */

    @SerializedName("free")
    private AssetNum free;
    @SerializedName("useing")
    private AssetNum useing;
    @SerializedName("scrap")
    private AssetNum scrap;
    @SerializedName("repair")
    private AssetNum repair;
    @SerializedName("total")
    private AssetNum total;

    public AssetNum getFree() {
        return free;
    }

    public void setFree(AssetNum free) {
        this.free = free;
    }

    public AssetNum getUseing() {
        return useing;
    }

    public void setUseing(AssetNum useing) {
        this.useing = useing;
    }

    public AssetNum getScrap() {
        return scrap;
    }

    public void setScrap(AssetNum scrap) {
        this.scrap = scrap;
    }

    public AssetNum getRepair() {
        return repair;
    }

    public void setRepair(AssetNum repair) {
        this.repair = repair;
    }

    public AssetNum getTotal() {
        return total;
    }

    public void setTotal(AssetNum total) {
        this.total = total;
    }

    public static class AssetNum {
        /**
         * name : 所有
         * value : 4726
         */

        private String name;
        private int value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}

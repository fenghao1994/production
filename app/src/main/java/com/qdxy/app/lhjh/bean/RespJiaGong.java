package com.qdxy.app.lhjh.bean;

import com.lf.tempcore.tempResponse.TempResponse;

import java.util.List;

/**
 * Created by KY on 2016/10/25.
 */

public class RespJiaGong extends TempResponse{
    private String gongXu;
    private List<GongJian> gongJian;

    @Override
    public String toString() {
        return "RespJiaGong2{" +
                "gongXu='" + gongXu + '\'' +
                ", gongJian=" + gongJian +
                '}';
    }

    public String getGongXu() {
        return gongXu;
    }

    public void setGongXu(String gongXu) {
        this.gongXu = gongXu;
    }

    public List<GongJian> getGongJian() {
        return gongJian;
    }

    public void setGongJian(List<GongJian> gongJian) {
        this.gongJian = gongJian;
    }

    public static class GongJian{
        private String Pru;
        private String num;

        @Override
        public String toString() {
            return "GongJian{" +
                    "Pru='" + Pru + '\'' +
                    ", num='" + num + '\'' +
                    '}';
        }

        public String getPru() {
            return Pru;
        }

        public void setPru(String pru) {
            Pru = pru;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}

package com.jingna.checklistapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/12/31.
 */

public class BillBean {
    /**
     * status : 200
     * data : [{"createTime":"2019-12-31 11:05:43","operatingDescribe":"订单结算","balance":123,"operatingRecord":123}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createTime : 2019-12-31 11:05:43
         * operatingDescribe : 订单结算
         * balance : 123
         * operatingRecord : 123
         */

        private String createTime;
        private String operatingDescribe;
        private double balance;
        private double operatingRecord;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOperatingDescribe() {
            return operatingDescribe;
        }

        public void setOperatingDescribe(String operatingDescribe) {
            this.operatingDescribe = operatingDescribe;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getOperatingRecord() {
            return operatingRecord;
        }

        public void setOperatingRecord(double operatingRecord) {
            this.operatingRecord = operatingRecord;
        }
    }
}

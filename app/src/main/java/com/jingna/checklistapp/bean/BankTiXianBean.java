package com.jingna.checklistapp.bean;

/**
 * Created by Administrator on 2020/1/8.
 */

public class BankTiXianBean {
    /**
     * status : 200
     * data : {"balance":5000,"cardname":"哈尔滨银行","cardid":8}
     */

    private String status;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * balance : 5000
         * cardname : 哈尔滨银行
         * cardid : 8
         */

        private int balance;
        private String cardname;
        private int cardid;

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getCardname() {
            return cardname;
        }

        public void setCardname(String cardname) {
            this.cardname = cardname;
        }

        public int getCardid() {
            return cardid;
        }

        public void setCardid(int cardid) {
            this.cardid = cardid;
        }
    }
}

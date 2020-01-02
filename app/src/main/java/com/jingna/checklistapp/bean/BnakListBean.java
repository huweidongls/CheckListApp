package com.jingna.checklistapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2020/1/2.
 */

public class BnakListBean {
    /**
     * status : 200
     * data : [{"id":7,"cardNumber":"231649799861949166","cardName":"哈尔滨银行","cardPhone":"15244615473"}]
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
         * id : 7
         * cardNumber : 231649799861949166
         * cardName : 哈尔滨银行
         * cardPhone : 15244615473
         */

        private int id;
        private String cardNumber;
        private String cardName;
        private String cardPhone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public String getCardPhone() {
            return cardPhone;
        }

        public void setCardPhone(String cardPhone) {
            this.cardPhone = cardPhone;
        }
    }
}

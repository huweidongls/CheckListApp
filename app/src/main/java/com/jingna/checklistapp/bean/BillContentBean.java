package com.jingna.checklistapp.bean;

/**
 * Created by Administrator on 2019/12/31.
 */

public class BillContentBean {
    /**
     * status : 200
     * data : {"id":1,"createTime":"2019-12-30 17:08:53","recordName":"测试商品2","recordStatus":0,"recordNum":"33891577696932830","recordPrice":123,"linkman":"刘德华","linkPhone":"18686817319","linkIdcard":"2323169451247450","updateTime":"2019-12-31 10:05:09"}
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
         * id : 1
         * createTime : 2019-12-30 17:08:53
         * recordName : 测试商品2
         * recordStatus : 0
         * recordNum : 33891577696932830
         * recordPrice : 123
         * linkman : 刘德华
         * linkPhone : 18686817319
         * linkIdcard : 2323169451247450
         * updateTime : 2019-12-31 10:05:09
         */

        private int id;
        private String createTime;
        private String recordName;
        private int recordStatus;
        private String recordNum;
        private int recordPrice;
        private String linkman;
        private String linkPhone;
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getRecordName() {
            return recordName;
        }

        public void setRecordName(String recordName) {
            this.recordName = recordName;
        }

        public int getRecordStatus() {
            return recordStatus;
        }

        public void setRecordStatus(int recordStatus) {
            this.recordStatus = recordStatus;
        }

        public String getRecordNum() {
            return recordNum;
        }

        public void setRecordNum(String recordNum) {
            this.recordNum = recordNum;
        }

        public int getRecordPrice() {
            return recordPrice;
        }

        public void setRecordPrice(int recordPrice) {
            this.recordPrice = recordPrice;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getLinkPhone() {
            return linkPhone;
        }

        public void setLinkPhone(String linkPhone) {
            this.linkPhone = linkPhone;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}

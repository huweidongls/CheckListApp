package com.jingna.checklistapp.bean;

/**
 * Created by Administrator on 2019/12/30.
 */

public class Loginbean {

    /**
     * status : 200
     * data : {"companyName":"佳通管理","userId":2,"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTI0NDYxNTQ3MyIsImNyZWF0ZWQiOjE1Nzc2ODY0NTU0NTcsImlzcyI6MiwiZXhwIjoxNTc4MjkxMjU1fQ.cUtES4OIPB3SfQAv1cJ8J9ln9ahek8PbxYHjINLtCBowtT4ULW6XXD-u-uk6xRNr2RyRGMtQOMfUrZixXbqPoQ=app"}
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
         * companyName : 佳通管理
         * userId : 2
         * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTI0NDYxNTQ3MyIsImNyZWF0ZWQiOjE1Nzc2ODY0NTU0NTcsImlzcyI6MiwiZXhwIjoxNTc4MjkxMjU1fQ.cUtES4OIPB3SfQAv1cJ8J9ln9ahek8PbxYHjINLtCBowtT4ULW6XXD-u-uk6xRNr2RyRGMtQOMfUrZixXbqPoQ=app
         */

        private String companyName;
        private int userId;
        private String token;

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}

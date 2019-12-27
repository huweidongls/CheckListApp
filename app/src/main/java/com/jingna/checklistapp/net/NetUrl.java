package com.jingna.artworkmall.net;

/**
 * Created by Administrator on 2019/12/10.
 */

public class NetUrl {

    public static final String BASE_URL = "http://192.168.1.102:8866/";//http://192.168.2.211:8080/
    public static final String MemUsersendMessage = "/MemUser/sendMessage";//发送验证码
    public static final String MemUserloginAPP = "/MemUser/loginAPP";//短信登录
    public static final String MemUserloginAppPassword = "/MemUser/loginAppPassword";//账号密码登录
    public static final String MemUseraddMember = "/MemUser/addMember";//注册
    public static final String MemAdresstoUpdate = "/MemAdress/toUpdate";//发布收货地址
    public static final String MemAdressqueryList = "/MemAdress/queryList";//地址列表
    public static final String MemAdresssetDefault="/MemAdress/setDefault";//设置会员默认地址
    public static final String MemAdresstoDelete = "/MemAdress/toDelete";//删除地址
    public static final String MemAdressgetOne = "/MemAdress/getOne";//获取单个会员收货地址信息
    public static final String MemUsergetOne = "/MemUser/getOne";//通过ID查询会员
    public static final String MemUsertoUpdate = "/MemUser/toUpdate";//新增个人信息详情
    public static final String AppBankCardqueryList = "/AppBankCard/queryList";//全部银行卡
    public static final String AppBankCardtoDelete = "/AppBankCard/toDelete";//删除银行卡
    public static final String AppBankCardtoUpdate = "/AppBankCard/toUpdate";//添加银行卡
}

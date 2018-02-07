package com.lq.okex.common;

public class Constant {
	public static final String EXCEPTION_NOT_LOGIN = "300"; //没登陆异常
	public static final String EXCEPTION_NOT_ACCOUNT_KEYS = "301";//登陆了没accountKeys异常
	public static final String EXCEPTION_PARAM = "302"; //参数类异常
	public static final String EXCEPTION_ERROR = "303"; //错误类异常
	public static final String EXCEPTION_FAILD = "304"; //失败类异常
	public static final String EXCEPTION_VOLID = "305"; //校验类异常
	public static final String EXCEPTION_OTHER = "399"; //其他类异常
	
	
	public static final String SUCCESS_INSERT = "插入成功";
	public static final String FAILD_INSERT = "插入失败";
	public static final String SUCCESS_SELECT = "插入成功";
	public static final String FAILD_SELECT = "插入失败";
	public static final String SUCCESS_UPDATE = "插入成功";
	public static final String FAILD_UPDATE = "插入失败";
	public static final String SUCCESS_DELETET = "插入成功";
	public static final String FAILD_DELETE = "插入失败";
	public static final String FAILD_PARAM = "参数传入不正确，请核对";
	public static final String FAILD_PARAM_NUM = "传入参数长度不正确";
	
	public static final String USER_NOT_EXISTS = "用户不存在";
	public static final String USER_NAME_EXISTS = "用户名已存在";
	public static final String USER_PASSWORD = "用户名或密码错误";
	public static final String USER_LOGIN_SUCCESS = "用户登录成功";
	public static final String USER_LOGIN_SUCCESS_BUT_NOT_KEYS = "用户登录成功，但是没有key";
	public static final String NO_ACCOUNT_KEYS = "您还没有输入publicKeys";
	public static final String NO_SQL_ACCOUNT_KEYS = "请重新输入publicKeys";
	public static final String NOT_VALID_ACCOUNT_KEYS = "您的publicKeys已经无效";
	public static final String VALID_ACCOUNT_SUCCESS = "校验成功";
	public static final String VALID_ACCOUNT_FAILD = "校验失败,您输入的不正确";
	public static final String VALID_ACCOUNT_EXCEPTION = "校验异常";
	public static final String ACCOUNT_MESSAGE_SUCCESS = "用户信息获取成功";
	public static final String ACCOUNT_MESSAGE_FAILD = "用户信息获取失败";
	
	public static final String ENTRUST_NUM_LESS = "低于最低值";
	public static final String ENTRUST_COIN_TYPE_NOT_EXISTS = "币种不存在";
	public static final String ENTRUST_TYPE_ERROR = "交易类型不正确";
	public static final String ENTRUST_SUCCESS = "下单成功";
	public static final String ENTRUST_ALL_PRICE_LESS = "交易金额不得低于0.001BTC";
	public static final String CONNECT_ADMIN = "出现异常，请联系管理员";
}

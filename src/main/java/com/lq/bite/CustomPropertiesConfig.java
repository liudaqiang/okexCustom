package com.lq.bite;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:custom.properties")
public class CustomPropertiesConfig implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Value("${CustomPropertiesConfig.dropRange}")
	private float dropRange;// 跌幅百分比
	@Value("${CustomPropertiesConfig.redisSaveTime}")
	private int redisSaveTime;// 缓存默认保存时间
	@Value("${CustomPropertiesConfig.orderSaveTime}")
	private int orderSaveTime;// 交易列表最高保存时间
	@Value("${CustomPropertiesConfig.myPrice}")
	private float myPrice;
	@Value("${CustomPropertiesConfig.publicKey}")
	private String publicKey;
	@Value("${CustomPropertiesConfig.privateKey}")
	private String privateKey;
	
	
	public float getDropRange() {
		return dropRange;
	}

	public void setDropRange(float dropRange) {
		this.dropRange = dropRange;
	}

	public int getRedisSaveTime() {
		return redisSaveTime;
	}

	public void setRedisSaveTime(int redisSaveTime) {
		this.redisSaveTime = redisSaveTime;
	}

	public int getOrderSaveTime() {
		return orderSaveTime;
	}

	public void setOrderSaveTime(int orderSaveTime) {
		this.orderSaveTime = orderSaveTime;
	}

	public float getMyPrice() {
		return myPrice;
	}

	public void setMyPrice(float myPrice) {
		this.myPrice = myPrice;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	
	
}

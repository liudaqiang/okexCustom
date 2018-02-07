package com.lq.okex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.lq.okex.utils.RedisAPI;

@Component
public class MyApplicationRunner implements ApplicationRunner {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CustomPropertiesConfig customPropertiesConfig;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		logger.warn("-------------项目启动-------------------");
		logger.warn("-------------初始化加载缓存-------------------");
		RedisAPI.setStr("publicKey", customPropertiesConfig.getPublicKey(), 86400);
		RedisAPI.setStr("privateKey", customPropertiesConfig.getPrivateKey(), 86400);
		RedisAPI.setStr("url", customPropertiesConfig.getUrl(), 86400);
		logger.warn("-------------加载结束-------------------");
	}
}
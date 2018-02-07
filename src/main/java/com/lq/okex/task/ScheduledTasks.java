package com.lq.okex.task;

import java.io.IOException;

import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lq.okex.rest.stock.IStockRestApi;
import com.lq.okex.rest.stock.impl.StockRestApi;
import com.lq.okex.utils.RedisAPI;

@Component
public class ScheduledTasks {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	private IStockRestApi stockGet = new StockRestApi(RedisAPI.getStr("url"),RedisAPI.getStr("publicKey"),RedisAPI.getStr("privateKey"));
	/**
	 * 每5分钟执行1次   到30秒的时候执行
	 */
	//@Scheduled(cron = "30 0/5 * * * ?")
	@Scheduled(cron="30 * * * * ?")
	public void getKLine() {
		try {
			String ltcKLine = stockGet.kline("ltc_btc", "5min");
			logger.warn(ltcKLine);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

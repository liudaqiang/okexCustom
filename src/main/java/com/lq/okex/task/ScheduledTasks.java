package com.lq.okex.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lq.okex.dao.MyOrderDao;
import com.lq.okex.entity.Kline;
import com.lq.okex.rest.stock.IStockRestApi;
import com.lq.okex.rest.stock.impl.StockRestApi;
import com.lq.okex.transaction.TradeStrategy;
import com.lq.okex.utils.AngleCalculationUtils;
import com.lq.okex.utils.RedisAPI;

@Component
public class ScheduledTasks {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	private IStockRestApi stockGet = new StockRestApi(RedisAPI.getStr("url"), RedisAPI.getStr("publicKey"),
			RedisAPI.getStr("privateKey"));
	private Double avg1 = null;
	private Double avg2 = null;
	private Double avg3 = null;
	
	@Autowired
	private MyOrderDao myOrderDao;
	/**
	 * 每5分钟执行1次 到30秒的时候执行
	 */
	//@Scheduled(cron = "30 1/5 * * * ?")
	@Scheduled(cron = "10 * * * * ?")
	public void getKLine() {
		try {
			String ltcKLine = stockGet.kline("ltc_usdt", "5min");
			ltcKLine = ltcKLine.substring(1, ltcKLine.length() - 1);
			ltcKLine = ltcKLine.replace("[", "");
			String[] allKLines = ltcKLine.split("],");
			List<Kline> klineList = toKlineList(allKLines);
			boolean flag = angleList(klineList) ;//|| M5AVGFlag(klineList);
			if(flag && (StringUtils.isBlank(RedisAPI.getStr("trade"))|| "true".equals(RedisAPI.getStr("trade")))){
				TradeStrategy ts = new TradeStrategy(myOrderDao);
				logger.warn("采取挂单操作");
				// TODO
				//出于速度考虑 这里可以用多线程去写
				//策略1 新的开始价
				ts.openTrade(klineList.get(klineList.size()-1));
				//策略2 新的当前最低价
				ts.newLowTrade(klineList.get(klineList.size()-1));
				//策略3 旧的MA5
				ts.oldMD5Trade(avg2);
			}else{
				logger.warn("看戏");
			}
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			logger.error("e:"+e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("e:"+e.getMessage());
		}
	}

	private boolean angleList(List<Kline> klineList) {
		List<Double> closeList = klineList.stream().map(Kline::getClose).collect(Collectors.toList());
		Double sum1 = 0D;
		Double sum2 = 0D;
		Double sum3 = 0D;
		for (int i = 0; i < closeList.size(); i++) {
			if (i>1 && i < 7) {
				sum1 += closeList.get(i);
			}
			if (i > 2 && i < 8) {
				sum2 += closeList.get(i);
			}
			if (i > 3 && i<9) {
				sum3 += closeList.get(i);
			}
		}
		logger.info("avg1:" + sum1 / 5);
		logger.info("avg2:" + sum2 / 5);
		logger.info("avg3:" + sum3 / 5);
		avg1 = sum1 / 5;
		avg2 = sum2 / 5;
		avg3 = sum3 / 5;
		double ma51 = AngleCalculationUtils.tanCalculation(0, 0.2477, sum1 / 5, sum2 / 5);
		double ma52 = AngleCalculationUtils.tanCalculation(0, 0.2477, sum2 / 5, sum3 / 5);
		logger.info("ma51:" + ma51);
		logger.info("ma52:" + ma52);
		if(ma51 > 0 && ma52 > 0){
			//度数大5度
			if(ma52 > ma51+5){
				logger.warn("角度变大  成上升趋势  买");
				return true;
			}
			//度数虽然没有大5度  但是实际度数却大于30度
			if(ma52 < ma51+5 && ma52>33){
				logger.warn("角度虽变小  但实际角度依然超过30度   成减缓型上升趋势  买");
				return true;
			}
		}
		return false;
	}
	
	//M5平均线
	private boolean M5AVGFlag(List<Kline> klineList){
		List<Double> closeList = klineList.stream().map(Kline::getClose).collect(Collectors.toList());
		Double sum1 = 0D;
		for (int i = 0; i < closeList.size(); i++) {
			if (i > 3 && i < 9) {
				sum1 += closeList.get(i);
			}
		}
		avg2 = sum1 / 5;
		if(avg2 < closeList.get(closeList.size()-1)){
			return true;
		}
		return false;
	}
	
	private List<Kline> toKlineList(String[] allKLines) {
		List<Kline> kLineList = new ArrayList<>();
		for (int i = 0; i < allKLines.length; i++) {
			String param[] = allKLines[i].replace("\"", "").replace("]", "").split(",");
			Kline kLine = new Kline();
			kLine.setTime(Double.parseDouble(param[0]));
			kLine.setOpen(Double.parseDouble(param[1]));
			kLine.setTop(Double.parseDouble(param[2]));
			kLine.setLow(Double.parseDouble(param[3]));
			kLine.setClose(Double.parseDouble(param[4]));
			kLine.setVolume(Double.parseDouble(param[5]));
			kLineList.add(kLine);
		}
		return kLineList;
	}

}

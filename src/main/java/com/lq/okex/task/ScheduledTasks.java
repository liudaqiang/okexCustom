package com.lq.okex.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lq.okex.entity.Kline;
import com.lq.okex.rest.stock.IStockRestApi;
import com.lq.okex.rest.stock.impl.StockRestApi;
import com.lq.okex.utils.AngleCalculationUtils;
import com.lq.okex.utils.RedisAPI;

@Component
public class ScheduledTasks {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	private IStockRestApi stockGet = new StockRestApi(RedisAPI.getStr("url"), RedisAPI.getStr("publicKey"),
			RedisAPI.getStr("privateKey"));

	/**
	 * 每5分钟执行1次 到30秒的时候执行
	 */
	//@Scheduled(cron = "30 0/5 * * * ?")
	@Scheduled(cron = "10 * * * * ?")
	public void getKLine() {
		try {
			String ltcKLine = stockGet.kline("ltc_usdt", "5min");
			ltcKLine = ltcKLine.substring(1, ltcKLine.length() - 1);
			ltcKLine = ltcKLine.replace("[", "");
			String[] allKLines = ltcKLine.split("],");
			List<Kline> klineList = toKlineList(allKLines);
			angleList(klineList);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void angleList(List<Kline> klineList) {
		List<Double> closeList = klineList.stream().map(Kline::getClose).collect(Collectors.toList());
		Double sum1 = 0D;
		Double sum2 = 0D;
		Double sum3 = 0D;
		for (int i = 0; i < closeList.size(); i++) {
			if (i < 7) {
				sum1 += closeList.get(i);
			}
			if (i > 0 && i < 8) {
				sum2 += closeList.get(i);
			}
			if (i > 1 && i<9) {
				sum3 += closeList.get(i);
			}
		}
		logger.warn("avg1:" + sum1 / 7);
		logger.warn("avg2:" + sum2 / 7);
		logger.warn("avg3:" + sum3 / 7);
		double ma51 = AngleCalculationUtils.tanCalculation(0, 0.2477, sum1 / 7, sum2 / 7);
		double ma52 = AngleCalculationUtils.tanCalculation(0, 0.2477, sum2 / 7, sum3 / 7);
		logger.warn("ma51:" + ma51);
		logger.warn("ma52:" + ma52);
		if(ma51 > 0 && ma52 > 0){
			//度数大5度
			if(ma52 > ma51+5){
				//买
				logger.warn("角度变大  成上升趋势  买");
			}
			//度数虽然没有大5度  但是实际度数却大于30度
			if(ma52 < ma51+5 && ma52>30){
				//买
				logger.warn("角度虽变小  但实际角度依然超过30度   成减缓型上升趋势  买");
			}
		}
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

package com.lq.okex.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lq.okex.dao.MyOrderDao;
import com.lq.okex.entity.Kline;
import com.lq.okex.entity.ResultEntity;
import com.lq.okex.rest.stock.IStockRestApi;
import com.lq.okex.rest.stock.impl.StockRestApi;
import com.lq.okex.thread.IsBuyTradeThread;
import com.lq.okex.utils.RedisAPI;

/**
 * 挂单策略类
 * 
 * @author l.q
 *
 */
public class TradeStrategy {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private IStockRestApi iStockRestApi =new StockRestApi(RedisAPI.getStr("url"), RedisAPI.getStr("publicKey"),
			RedisAPI.getStr("privateKey"));
	private MyOrderDao myOrderDao;
	public TradeStrategy(MyOrderDao myOrderDao) {
		// 一旦进入挂单策略后 挂单定时器禁止2b
		RedisAPI.setStr("trade", "false", 120);
		this.myOrderDao = myOrderDao;
	}

	// 以新的开盘价格进行挂单
	public void openTrade(Kline kline) {
		Double price = kline.getOpen();
		logger.warn("策略1将以：" + price + "的价格进行购买");
		try {
			ResultEntity resultEntity = buy(price);
			if(resultEntity != null){
				IsBuyTradeThread ibtt = new IsBuyTradeThread(resultEntity,myOrderDao);
				Thread t = new Thread(ibtt);
				t.start();
			}
		}catch (Exception e) {
			logger.error("e:"+e.getMessage());
			logger.warn("重新下单");
			try {
				Thread.sleep(3000);
				openTrade(kline);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				logger.error("e:"+e1.getMessage());
			}
		}
	}

	// 以新的开盘中最低的成交金额进行挂单交易
	public void newLowTrade(Kline kline) {
		Double price = kline.getLow();
		logger.warn("策略2将以：" + price + "的价格进行购买");
		try {
			ResultEntity resultEntity = buy(price);
			if(resultEntity != null){
				IsBuyTradeThread ibtt = new IsBuyTradeThread(resultEntity,myOrderDao);
				Thread t = new Thread(ibtt);
				t.start();
			}
		}catch (Exception e) {
			logger.error("e:"+e.getMessage());
			logger.warn("重新下单");
			try {
				Thread.sleep(3000);
				newLowTrade(kline);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				logger.error("e:"+e1.getMessage());
			}
		}
	}

	// 以上一个开盘的MA5进行挂单交易
	public void oldMD5Trade(Double ma52) {
		logger.warn("策略3将以：" + ma52 + "的价格进行购买");
		try {
			ResultEntity resultEntity = buy(ma52);
			if(resultEntity != null){
				IsBuyTradeThread ibtt = new IsBuyTradeThread(resultEntity,myOrderDao);
				Thread t = new Thread(ibtt);
				t.start();
			}
		}catch (Exception e) {
			logger.error("e:"+e.getMessage());
			logger.warn("重新下单");
			try {
				Thread.sleep(3000);
				oldMD5Trade(ma52);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				logger.error("e:"+e1.getMessage());
			}
		}
	}
	
	
	private ResultEntity buy(Double price)throws Exception{
		String result = iStockRestApi.trade("ltc_usdt","buy",String.valueOf(price),String.valueOf(0.11));
		ResultEntity resultEntity = JSON.parseObject(result, ResultEntity.class);
		if(resultEntity.isResult()){
			logger.warn("买挂单成功");
			return resultEntity;
		}
		return null;
	}
}

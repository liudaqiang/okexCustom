package com.lq.okex.thread;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.lq.okex.dao.MyOrderDao;
import com.lq.okex.entity.MyOrder;
import com.lq.okex.entity.Orders;
import com.lq.okex.entity.ResultEntity;
import com.lq.okex.rest.stock.IStockRestApi;
import com.lq.okex.rest.stock.impl.StockRestApi;
import com.lq.okex.utils.RedisAPI;

public class IsSellTradeThread implements Runnable {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private ResultEntity resultEntity = null;
	private IStockRestApi iStockRestApi = new StockRestApi(RedisAPI.getStr("url"), RedisAPI.getStr("publicKey"),
			RedisAPI.getStr("privateKey"));
	private MyOrderDao myOrderDao;
	public IsSellTradeThread(ResultEntity resultEntity,MyOrderDao myOrderDao) {
		this.resultEntity = resultEntity;
		this.myOrderDao = myOrderDao;
	}

	@Override
	public void run() {
		try {
			boolean flag = true;
			while (flag) {
				String result = iStockRestApi.order_info("ltc_usdt", resultEntity.getOrder_id());
				ResultEntity resultEntity = JSON.parseObject(result, ResultEntity.class);
				if (resultEntity.isResult()) {
					List<Orders> ordersList = JSON.parseArray(resultEntity.getOrders(), Orders.class);
					if (ordersList != null && ordersList.size() != 0) {
						if (ordersList.get(0).getStatus() == 2) {
							logger.warn("卖单已卖出，重新进入看戏状态");
							flag = false;
							RedisAPI.setStr("trade", "true", 86400);
							MyOrder myOrder = new MyOrder();
							logger.warn("myOrder:"+myOrder.toString()+"id:"+ordersList.get(0).getOrder_id());
							myOrder.setOrderId(ordersList.get(0).getOrder_id());
							myOrder = myOrderDao.get(myOrder).get(0);
							myOrder.setSellPrice(ordersList.get(0).getAvg_price());
							myOrder.setFactSellPrice(ordersList.get(0).getAvg_price()*0.9980);
							myOrder.setAllSellPrice(myOrder.getFactBuyNum()*ordersList.get(0).getAvg_price()*0.9980);
							myOrder.setProfit(myOrder.getAllSellPrice()-myOrder.getAllBuyPrice());
							myOrderDao.update(myOrder);
						}
					}
				}
				if (flag) {
					Thread.sleep(1000);
				}
			}
		} catch (Exception e) {
			logger.error("e:" + e.getMessage());
			e.printStackTrace();
		}
	}

}

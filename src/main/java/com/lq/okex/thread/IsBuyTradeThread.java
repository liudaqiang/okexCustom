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
import com.lq.okex.utils.DoubleFormat;
import com.lq.okex.utils.RedisAPI;

/**
 * 挂单买校验线程
 * @author l.q
 *
 */
public class IsBuyTradeThread implements Runnable{
	private Logger logger = LoggerFactory.getLogger(getClass());
	private ResultEntity resultEntity = null;
	private IStockRestApi iStockRestApi =new StockRestApi(RedisAPI.getStr("url"), RedisAPI.getStr("publicKey"),
			RedisAPI.getStr("privateKey"));
	private MyOrderDao myOrderDao;
	public IsBuyTradeThread(ResultEntity resultEntity,MyOrderDao myOrderDao){
		this.resultEntity = resultEntity;
		this.myOrderDao = myOrderDao;
	}
	
	@Override
	public void run() {
		try {
			boolean flag = true;
			while(flag){
				String result = iStockRestApi.order_info("ltc_usdt",resultEntity.getOrder_id());
				ResultEntity resultEntity = JSON.parseObject(result, ResultEntity.class);
				if(resultEntity.isResult()){
					List<Orders> ordersList = JSON.parseArray(resultEntity.getOrders(), Orders.class);
					if(ordersList != null && ordersList.size() != 0){
						if(ordersList.get(0).getStatus() == 2){
							logger.warn("挂单已被购买，即将进行卖挂单");
							MyOrder myOrder = new MyOrder();
							myOrder.setBuyNum(ordersList.get(0).getAmount());
							myOrder.setBuyPrice(ordersList.get(0).getAvg_price());
							myOrder.setOrderId(ordersList.get(0).getOrder_id());
							myOrder.setFactBuyNum(ordersList.get(0).getAmount()*0.9985);
							myOrder.setAllBuyPrice(ordersList.get(0).getAmount()*ordersList.get(0).getAvg_price());
							myOrderDao.insert(myOrder);
							flag = false;
							sell(ordersList.get(0));
						}else if((System.currentTimeMillis()-Double.parseDouble(ordersList.get(0).getCreate_date()))>300000){
							logger.warn("挂单超过五分钟没人买，取消挂单");
							boolean flagCancel = true;
							while(flagCancel){
								String resultCancel = iStockRestApi.cancel_order("ltc_usdt",ordersList.get(0).getOrder_id());
								ResultEntity resultEntityCancel = JSON.parseObject(resultCancel, ResultEntity.class);
								if(resultEntityCancel.isResult()){
									flag = false;
									flagCancel = false;
									logger.warn("卖单已卖出，重新进入看戏状态");
									RedisAPI.setStr("trade", "true", 86400);
								}else{
									Thread.sleep(1000);
									logger.warn("撤单失败，继续撤单");
								}
							}
						}
					}
				}
				if(flag){
					Thread.sleep(1000);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("e:"+e.getMessage());
		}
	}
	
	private void sell(Orders orders){
		try {
			String result = iStockRestApi.trade("ltc_usdt","sell",String.valueOf(orders.getAvg_price()*1.004),String.valueOf(DoubleFormat.d4(orders.getAmount()*0.9985)));
			ResultEntity resultEntity = JSON.parseObject(result, ResultEntity.class);
			if(resultEntity.isResult()){
				logger.warn("卖挂单成功");
				logger.warn("进行卖监控操作");
				Thread t = new Thread(new IsSellTradeThread(resultEntity,myOrderDao));
				t.start();
			}else{
				Thread.sleep(500);
				sell(orders);
			}
		} catch (Exception e) {
			logger.error("e:"+e.getMessage());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sell(orders);
		}
	}
}

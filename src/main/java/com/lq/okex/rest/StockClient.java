package com.lq.okex.rest;

import java.io.IOException;

import org.apache.http.HttpException;

import com.lq.okex.rest.stock.IStockRestApi;
import com.lq.okex.rest.stock.impl.StockRestApi;

/**
 * 现货 REST API 客户端请求
 * 
 * @author zhangchi
 *
 */
public class StockClient {

	public static void main(String[] args) throws HttpException, IOException {

		String api_key = "6bedadc0-2d81-40c3-8c60-73204a8d575d"; // OKCoin申请的apiKey
		String secret_key = "F802833279F7FD35A5303D2945CBB42D"; // OKCoin
																// 申请的secret_key
		String url_prex = "https://www.okex.com"; // 注意：请求URL
													// 国际站https://www.okcoin.com
													// ;
													// 国内站https://www.okcoin.cn

		/**
		 * get请求无需发送身份认证,通常用于获取行情，市场深度等公共信息
		 * 
		 */
		IStockRestApi stockGet = new StockRestApi(url_prex);

		/**
		 * post请求需发送身份认证，获取用户个人相关信息时，需要指定api_key,与secret_key并与参数进行签名，
		 * 此处对构造方法传入api_key与secret_key,在请求用户相关方法时则无需再传入，
		 * 发送post请求之前，程序会做自动加密，生成签名。
		 * 
		 */
		IStockRestApi stockPost = new StockRestApi(url_prex, api_key, secret_key);

		// 现货行情
//		stockGet.ticker("btc_usd");
//
//		// 现货市场深度
//		stockGet.depth("btc_usd");
//
//		// 现货OKCoin历史交易信息
//		stockGet.trades("btc_usd", "20");
		String a = stockGet.kline("ltc_btc", "5min");
		System.out.println(a);
		// 现货用户信息
//		stockPost.userinfo();
//
//		// 现货下单交易
//		String tradeResult = stockPost.trade("btc_usd", "buy", "50", "0.02");
//		System.out.println(tradeResult);
//		JSONObject tradeJSV1 = JSONObject.parseObject(tradeResult);
//		String tradeOrderV1 = tradeJSV1.getString("order_id");
//
//		// 现货获取用户订单信息
//		stockPost.order_info("btc_usd", tradeOrderV1);
//
//		// 现货撤销订单
//		stockPost.cancel_order("btc_usd", tradeOrderV1);
//
//		// 现货批量下单
//		stockPost.batch_trade("btc_usd", "buy", "[{price:50, amount:0.02},{price:50, amount:0.03}]");
//
//		// 批量获取用户订单
//		stockPost.orders_info("0", "btc_usd", "125420341, 125420342");
//
//		// 获取用户历史订单信息，只返回最近七天的信息
//		stockPost.order_history("btc_usd", "0", "1", "20");

	}
}

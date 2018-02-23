package com.lq.okex.entity;

public class MyOrder {
	private String orderId;
	private Double buyPrice;//挂单买入价格
	private Double buyNum;//挂单买入数量
	private Double factBuyNum;//实际买入数量
	private Double allBuyPrice;//总花费    买入单价*买入数量
	private Double sellPrice; //挂单卖出价格
	private Double factSellPrice;//实际卖出价格
	private Double allSellPrice;//总卖价   实际买入数量*实际卖出价格
	private Double profit;//盈利  总卖价-总买价
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public Double getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(Double buyNum) {
		this.buyNum = buyNum;
	}
	public Double getFactBuyNum() {
		return factBuyNum;
	}
	public void setFactBuyNum(Double factBuyNum) {
		this.factBuyNum = factBuyNum;
	}
	public Double getAllBuyPrice() {
		return allBuyPrice;
	}
	public void setAllBuyPrice(Double allBuyPrice) {
		this.allBuyPrice = allBuyPrice;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Double getFactSellPrice() {
		return factSellPrice;
	}
	public void setFactSellPrice(Double factSellPrice) {
		this.factSellPrice = factSellPrice;
	}
	public Double getAllSellPrice() {
		return allSellPrice;
	}
	public void setAllSellPrice(Double allSellPrice) {
		this.allSellPrice = allSellPrice;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	@Override
	public String toString() {
		return "MyOrder [orderId=" + orderId + ", buyPrice=" + buyPrice + ", buyNum=" + buyNum + ", factBuyNum="
				+ factBuyNum + ", allBuyPrice=" + allBuyPrice + ", sellPrice=" + sellPrice + ", factSellPrice="
				+ factSellPrice + ", allSellPrice=" + allSellPrice + ", profit=" + profit + "]";
	}
	
	
	
}

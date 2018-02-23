package com.lq.okex.entity;

public class Orders {
	private Double amount;//委托数量
	private Double avg_price;//平均成交价
	private String create_date;//委托时间
	private Double deal_amount;//成交数量
	private String order_id;//订单ID
	private String orders_id;//订单ID(不建议使用)
	private Double price;//委托价格
	private Integer status;//-1:已撤销  0:未成交  1:部分成交  2:完全成交 3:撤单处理中
	private String type;//buy_market:市价买入 / sell_market:市价卖出
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getAvg_price() {
		return avg_price;
	}
	public void setAvg_price(Double avg_price) {
		this.avg_price = avg_price;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public Double getDeal_amount() {
		return deal_amount;
	}
	public void setDeal_amount(Double deal_amount) {
		this.deal_amount = deal_amount;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getOrders_id() {
		return orders_id;
	}
	public void setOrders_id(String orders_id) {
		this.orders_id = orders_id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Orders [amount=" + amount + ", avg_price=" + avg_price + ", create_date=" + create_date
				+ ", deal_amount=" + deal_amount + ", order_id=" + order_id + ", orders_id=" + orders_id + ", price="
				+ price + ", status=" + status + ", type=" + type + "]";
	}
	
	
}

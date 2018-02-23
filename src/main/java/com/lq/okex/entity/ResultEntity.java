package com.lq.okex.entity;

/**
 * 返回的公共类
 * 
 * @author l.q
 *
 */
public class ResultEntity {
	private boolean result; //返回类型 true/false
	private String order_id; //订单id
	private String orders;//
	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "ResultEntity [result=" + result + ", order_id=" + order_id + ", orders=" + orders + "]";
	}

}

package com.lq.okex.entity;

public class Kline implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double time;//时间戳
	private Double open;//开
	private Double top;//高
	private Double low;//低
	private Double close;//收
	private Double volume;//交易量
	
	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getTop() {
		return top;
	}

	public void setTop(Double top) {
		this.top = top;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "Kline [time=" + time + ", open=" + open + ", top=" + top + ", low=" + low + ", close=" + close
				+ ", volume=" + volume + "]";
	}
	
	
}

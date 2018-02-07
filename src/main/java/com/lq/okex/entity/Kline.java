package com.lq.okex.entity;

public class Kline implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long time;//时间戳
	private Long open;//开
	private Long top;//高
	private Long low;//低
	private Long close;//收
	private Long volume;//交易量
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Long getOpen() {
		return open;
	}
	public void setOpen(Long open) {
		this.open = open;
	}
	public Long getTop() {
		return top;
	}
	public void setTop(Long top) {
		this.top = top;
	}
	public Long getLow() {
		return low;
	}
	public void setLow(Long low) {
		this.low = low;
	}
	public Long getClose() {
		return close;
	}
	public void setClose(Long close) {
		this.close = close;
	}
	public Long getVolume() {
		return volume;
	}
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	
	
}

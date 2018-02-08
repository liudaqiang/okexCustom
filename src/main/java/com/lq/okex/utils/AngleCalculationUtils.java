package com.lq.okex.utils;

public class AngleCalculationUtils {
	/**
	 * 计算夹角
	 * @param x0
	 * @param x1
	 * @param y0
	 * @param y1
	 * @return
	 */
	public static double tanCalculation(double x0,double x1,double y0,double y1){
		return Math.toDegrees (Math.atan2 (y1-y0,x1-x0));
	}
	
	public static void main(String[] args) {
		//tanCalculation(0.1,0.2);
	}
}

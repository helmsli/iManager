package com.xinwei.process.entity;

public class CycleTime {
	public static final String DAY = "day";
	public static final String MONTH = "month";
	public static final String YEAR = "year";
	
	private int cycle;//周期
	private String unit;//单位
	
	public int getCycle() {
		return cycle;
	}
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

}

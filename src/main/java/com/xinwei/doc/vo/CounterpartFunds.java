package com.xinwei.doc.vo;

/**
 * 配套资金
 */
public class CounterpartFunds {
	private String ownFunds;
	private String socialFunds;
	private String total;

	public String getOwnFunds() {
		return ownFunds;
	}

	public void setOwnFunds(String ownFunds) {
		this.ownFunds = ownFunds;
	}

	public String getSocialFunds() {
		return socialFunds;
	}

	public void setSocialFunds(String socialFunds) {
		this.socialFunds = socialFunds;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "配套资金 [配套资金=" + ownFunds + ", 社会募集资金=" + socialFunds + ", 合计=" + total + "]";
	}

}

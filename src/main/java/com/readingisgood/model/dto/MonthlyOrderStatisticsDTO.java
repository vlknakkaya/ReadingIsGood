package com.readingisgood.model.dto;

public class MonthlyOrderStatisticsDTO {

	private int monthId;
	private long totalOrderCount;
	private long totalBookCount;
	private double totalPurchasedAmount;

	public MonthlyOrderStatisticsDTO() {
		super();
	}

	public MonthlyOrderStatisticsDTO(int monthId, long totalOrderCount, long totalBookCount,
			double totalPurchasedAmount) {
		super();
		this.monthId = monthId;
		this.totalOrderCount = totalOrderCount;
		this.totalBookCount = totalBookCount;
		this.totalPurchasedAmount = totalPurchasedAmount;
	}

	public int getMonthId() {
		return monthId;
	}

	public void setMonthId(int monthId) {
		this.monthId = monthId;
	}

	public long getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(long totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	public long getTotalBookCount() {
		return totalBookCount;
	}

	public void setTotalBookCount(long totalBookCount) {
		this.totalBookCount = totalBookCount;
	}

	public double getTotalPurchasedAmount() {
		return totalPurchasedAmount;
	}

	public void setTotalPurchasedAmount(double totalPurchasedAmount) {
		this.totalPurchasedAmount = totalPurchasedAmount;
	}

}

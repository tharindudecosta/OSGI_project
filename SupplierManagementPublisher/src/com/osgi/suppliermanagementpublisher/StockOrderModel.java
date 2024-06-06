package com.osgi.suppliermanagementpublisher;

public class StockOrderModel {

	private int id;
	private String noOfStocks;
	private String productName;
	private String supplierCompany;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNoOfStocks() {
		return noOfStocks;
	}

	public void setNoOfStocks(String noOfStocks) {
		this.noOfStocks = noOfStocks;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSupplierCompany() {
		return supplierCompany;
	}

	public void setSupplierCompany(String supplierCompany) {
		this.supplierCompany = supplierCompany;
	}

}

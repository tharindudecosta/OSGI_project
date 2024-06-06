package com.osgi.customermanagementpublisher;

public class ProductOrderModel {

	private int orderId;
	private int productId;
	private String productName;
	private String deliveryAddress;
	private String price;
	private boolean isActive;

	public ProductOrderModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductOrderModel(int orderId, int productId, String productName, String deliveryAddress, String price,
			boolean isActive) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.deliveryAddress = deliveryAddress;
		this.price = price;
		this.isActive = isActive;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDeliveryaddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
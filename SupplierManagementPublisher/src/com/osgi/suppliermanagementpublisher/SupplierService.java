package com.osgi.suppliermanagementpublisher;

public interface SupplierService {
	public void addNewSupplier();

	public void getAllSupplierDetails();

	public void deleteSupplier();

	public void getSupplierById();

	public void stockOrder();

	public void getAllStockOrders();

	public void deleteStockOrder();

	public void getStockOrderById();

	public void getsupplierReport();

	void getstockOrderReport();

}

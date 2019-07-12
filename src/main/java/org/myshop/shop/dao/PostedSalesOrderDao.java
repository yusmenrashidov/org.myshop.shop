package org.myshop.shop.dao;

import java.util.Date;
import java.util.List;

import org.myshop.shop.model.PostedSalesOrder;

public interface PostedSalesOrderDao {

	public void addPostedSalesOrder(PostedSalesOrder order);
	public List<PostedSalesOrder> getPostedPurchaseOrders();
	public List<PostedSalesOrder> getPostedPurchaseOrdersByDate(Date date);
	public PostedSalesOrder getPostedPurchaseOrder(String id);

	
}

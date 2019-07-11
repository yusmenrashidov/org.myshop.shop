package org.myshop.shop.dao;

import java.util.Date;
import java.util.List;
import org.myshop.shop.model.PurchaseOrder;

public interface PurchaseOrderDao {
	
	public void addPurchaseOrder(PurchaseOrder order);
	public PurchaseOrder getPurchaseOrder(String id);
	public List<PurchaseOrder> getPurchaseOrdersByDate(Date date);
	public List<PurchaseOrder> getPurchaseOrders();
	
}

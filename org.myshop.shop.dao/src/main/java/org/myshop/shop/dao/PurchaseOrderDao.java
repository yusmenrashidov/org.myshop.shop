package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.PurchaseOrder;

public interface PurchaseOrderDao {
	
	public void create(PurchaseOrder order);
	
	public List<PurchaseOrder> read();
	
	public PurchaseOrder get(String id);
	
	public PurchaseOrder update(PurchaseOrder order);
	
	public void delete(PurchaseOrder order);
	
}

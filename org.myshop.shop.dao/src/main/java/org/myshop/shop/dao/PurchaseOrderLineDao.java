package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.PurchaseOrderLine;

public interface PurchaseOrderLineDao {
	
	public void create(PurchaseOrderLine line);
	
	public List<PurchaseOrderLine> read();
	
	public PurchaseOrderLine get(String id);
	
	public PurchaseOrderLine update(PurchaseOrderLine line);
	
	public void delete(PurchaseOrderLine line);
}

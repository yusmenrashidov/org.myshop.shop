package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.PurchaseOrderLine;

public interface PurchaseOrderLineDao {
	
	public void addPurchaseOrderLine(PurchaseOrderLine line);
	public List<PurchaseOrderLine> getPurchaseOrderLines();
	public List<PurchaseOrderLine> getPurchaseOrderLinesByItem(Item item);
	public PurchaseOrderLine getPurchaseOrderLine(PurchaseOrderLine line);
	
	
}

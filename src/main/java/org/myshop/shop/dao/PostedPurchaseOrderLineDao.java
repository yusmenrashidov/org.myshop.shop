package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.PostedPurchaseOrderLine;

public interface PostedPurchaseOrderLineDao {
	
	public void addPostedPurchaseOrderLine(PostedPurchaseOrderLine line);
	public List<PostedPurchaseOrderLine> getPurchaseOrderLines();
	public List<PostedPurchaseOrderLine> getPurchaseOrderLinesByItem(Item item);
	public PostedPurchaseOrderLine getPurchaseOrderLine(PostedPurchaseOrderLine line);
	
}

package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.PostedPurchaseOrderLine;

public interface PostedPurchaseOrderLineDao {
	
	public void create(PostedPurchaseOrderLine line);
	
	public List<PostedPurchaseOrderLine> read();
	
	public PostedPurchaseOrderLine get(String id);
	
	public PostedPurchaseOrderLine update(PostedPurchaseOrderLine line);
	
	public void delete(PostedPurchaseOrderLine line);
}

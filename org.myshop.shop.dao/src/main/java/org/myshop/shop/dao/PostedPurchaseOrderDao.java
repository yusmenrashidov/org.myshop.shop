package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.PostedPurchaseOrder;

public interface PostedPurchaseOrderDao {
	
	public void create(PostedPurchaseOrder order);
	
	public List<PostedPurchaseOrder> read();
	
	public PostedPurchaseOrder get(String id);
	
	public PostedPurchaseOrder update(PostedPurchaseOrder order);
	
	public void delete(PostedPurchaseOrder order);
	
}


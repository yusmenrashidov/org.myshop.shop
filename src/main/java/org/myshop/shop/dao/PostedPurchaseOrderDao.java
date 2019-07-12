package org.myshop.shop.dao;

import java.util.Date;
import java.util.List;
import org.myshop.shop.model.PostedPurchaseOrder;

public interface PostedPurchaseOrderDao {
	
		public void addPostedPurchaseOrder(PostedPurchaseOrder order);
		public List<PostedPurchaseOrder> getPostedPurchaseOrders();
		public List<PostedPurchaseOrder> getPostedPurchaseOrdersByDate(Date date);
		public PostedPurchaseOrder getPostedPurchaseOrder(String id);

}


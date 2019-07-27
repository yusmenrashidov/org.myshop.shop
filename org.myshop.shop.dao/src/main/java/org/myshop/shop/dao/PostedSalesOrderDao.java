package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.PostedSalesOrder;

public interface PostedSalesOrderDao {

	public void create(PostedSalesOrder order);
	
	public List<PostedSalesOrder> read();
	
	public PostedSalesOrder get(String id);
	
	public PostedSalesOrder update(PostedSalesOrder order);
	
	public void delete(PostedSalesOrder order);
	
}

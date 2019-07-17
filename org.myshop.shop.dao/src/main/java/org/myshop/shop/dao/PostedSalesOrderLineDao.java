package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.PostedSalesOrderLine;

public interface PostedSalesOrderLineDao {
	
	public void create(PostedSalesOrderLine line);
	
	public List<PostedSalesOrderLine> read();
	
	public PostedSalesOrderLine get(String id);
	
	public PostedSalesOrderLine update(PostedSalesOrderLine line);
	
	public void delete(PostedSalesOrderLine line);
}

package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.PostedSalesOrderLine;


public interface PostedSalesOrderLineDao {
	
	public void addPostedSalesOrderLine(PostedSalesOrderLine line);
	public List<PostedSalesOrderLine> getSalesOrderLines();
	public List<PostedSalesOrderLine> getSalesOrderLinesByItem(Item imtem);
	public PostedSalesOrderLine getSalesOrderLine(String id);
	public void editSalesOrderLine(String id);
	public void deleteSalesOrderLine(String id);
}

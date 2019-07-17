package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.SalesOrder;

public interface SalesOrderDao {

	public void create(SalesOrder order);
	
	public List<SalesOrder> read();
	
	public SalesOrder get(String id);
	
	public SalesOrder update(SalesOrder order);
	
	public void delete(SalesOrder order);
}

package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.SalesOrderLine;

public interface SalesOrderLineDao {

	public void create(SalesOrderLine line);
	
	public List<SalesOrderLine> read();
	
	public SalesOrderLine get(String id);
	
	public SalesOrderLine update(SalesOrderLine line);
	
	public void delete(SalesOrderLine line);
}

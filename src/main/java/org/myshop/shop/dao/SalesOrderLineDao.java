package org.myshop.shop.dao;

import java.util.List;
import org.myshop.shop.model.Item;
import org.myshop.shop.model.SalesOrderLine;

public interface SalesOrderLineDao {

	public void addSalesOrderLine(SalesOrderLine line);
	public List<SalesOrderLine> getSalesOrderLines();
	public List<SalesOrderLine> getSalesOrderLinesByItem(Item imtem);
	public SalesOrderLine getSalesOrderLine(String id);
	public void editSalesOrderLine(String id);
	public void deleteSalesOrderLine(String id);
	
}

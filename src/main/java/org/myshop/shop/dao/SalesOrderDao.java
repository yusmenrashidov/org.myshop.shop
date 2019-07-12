package org.myshop.shop.dao;

import java.util.Date;
import java.util.List;
import org.myshop.shop.model.SalesOrder;

public interface SalesOrderDao {

	public void addSalesOrder(SalesOrder order);
	public List<SalesOrder> getSalesOrders();
	public List<SalesOrder> getSalesOrderByDate(Date date);
	public SalesOrder getSalesOrder();
}

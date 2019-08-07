package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;
import org.myshop.shop.model.SalesOrder;

public interface ISalesOrderSerializer {
	
	public String serializerList(List<SalesOrder> salesOrderList);
}

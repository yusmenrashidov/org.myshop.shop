package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

import org.myshop.shop.model.SalesOrderLine;

public interface ISalesOrderLineSerializer {

	public String serializerList(List<SalesOrderLine> salesOrderLineList);
}

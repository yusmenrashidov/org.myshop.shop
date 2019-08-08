package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.SalesOrderLine;
import org.org.myshop.shop.api.rest.servlet.exc.SalesOrderLineDeserializationException;

public interface ISalesOrderLineDeserializer {

	public SalesOrderLine deserialize(String jsonString) throws SalesOrderLineDeserializationException;
}

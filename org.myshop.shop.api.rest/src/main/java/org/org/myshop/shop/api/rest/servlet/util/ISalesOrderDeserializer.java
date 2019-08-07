package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.SalesOrder;
import org.org.myshop.shop.api.rest.servlet.exc.SalesOrderDeserializationException;

public interface ISalesOrderDeserializer {
	
	public SalesOrder deserialize(String jsonString) throws SalesOrderDeserializationException;
}

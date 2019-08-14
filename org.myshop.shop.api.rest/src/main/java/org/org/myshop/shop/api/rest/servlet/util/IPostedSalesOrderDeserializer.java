package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.PostedSalesOrder;
import org.org.myshop.shop.api.rest.servlet.exc.PostedSalesOrderDeserializationException;

public interface IPostedSalesOrderDeserializer {
	
	public PostedSalesOrder deserialize(String jsonString) throws PostedSalesOrderDeserializationException;
}

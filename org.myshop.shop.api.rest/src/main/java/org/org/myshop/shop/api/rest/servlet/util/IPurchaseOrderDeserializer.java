package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.PurchaseOrder;
import org.org.myshop.shop.api.rest.servlet.exc.PurchaseOrderDeserializationException;

public interface IPurchaseOrderDeserializer {

	public PurchaseOrder deserialize(String jsonString) throws PurchaseOrderDeserializationException;
}

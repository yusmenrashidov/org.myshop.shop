package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.PurchaseOrderLine;

import org.org.myshop.shop.api.rest.servlet.exc.PurchaseOrderLineDeserializationException;

public interface IPurchaseOrderLineDeserializer {

	public PurchaseOrderLine deserialize(String jsonString) throws PurchaseOrderLineDeserializationException;
}

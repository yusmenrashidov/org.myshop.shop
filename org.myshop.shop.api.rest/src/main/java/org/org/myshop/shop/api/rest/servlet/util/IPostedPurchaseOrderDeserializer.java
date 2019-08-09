package org.org.myshop.shop.api.rest.servlet.util;

import org.myshop.shop.model.PostedPurchaseOrder;
import org.org.myshop.shop.api.rest.servlet.exc.PostedPurchaseOrderDeserializationException;

public interface IPostedPurchaseOrderDeserializer {

	public PostedPurchaseOrder deserialize(String jsonString) throws PostedPurchaseOrderDeserializationException;
}

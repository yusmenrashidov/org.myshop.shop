package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;

import org.myshop.shop.model.PurchaseOrderLine;
import org.org.myshop.shop.api.rest.servlet.util.IPurchaseOrderLineSerializer;

import com.google.gson.Gson;

public class PurchaseOrderLineSerializer implements IPurchaseOrderLineSerializer{

	public String serializerList(List<PurchaseOrderLine> purchaseOrderLineList) {
		
		Gson gson = new Gson();
		
		String listToJson = gson.toJson(purchaseOrderLineList);		
		
		return listToJson;
	}

}

package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;

import org.myshop.shop.model.PurchaseOrder;
import org.org.myshop.shop.api.rest.servlet.util.IPurchaseOrderSerializer;

import com.google.gson.Gson;

public class PurchaseOrderSerializer implements IPurchaseOrderSerializer{

	public String serializerList(List<PurchaseOrder> purchaseOrderList) {
		
		Gson gson = new Gson();
		
		String listToJson = gson.toJson(purchaseOrderList);
		
		return listToJson;
	}

}

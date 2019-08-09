package org.myshop.shop.api.rest.servlet.util.implementation;

import java.util.List;

import org.myshop.shop.model.PostedPurchaseOrder;
import org.org.myshop.shop.api.rest.servlet.util.IPostedPurchaseOrderSerializer;

import com.google.gson.Gson;

public class PostedPurchaseOrderSerializer implements IPostedPurchaseOrderSerializer{

	public String serializerList(List<PostedPurchaseOrder> postedPurchaseOrderList) {
		
		Gson gson = new Gson();
		
		String listToJson = gson.toJson(postedPurchaseOrderList);		
		
		return listToJson;
	}

}

package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

import org.myshop.shop.model.PostedPurchaseOrder;

public interface IPostedPurchaseOrderSerializer {

	public String serializerList(List<PostedPurchaseOrder> postedPurchaseOrderList);
}

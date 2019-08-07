package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

import org.myshop.shop.model.PurchaseOrder;

public interface IPurchaseOrderSerializer {

	public String serializerList(List<PurchaseOrder> purchaseOrderList);
}

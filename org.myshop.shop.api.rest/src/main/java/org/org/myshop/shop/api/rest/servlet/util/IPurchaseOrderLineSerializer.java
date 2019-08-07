package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;

import org.myshop.shop.model.PurchaseOrderLine;

public interface IPurchaseOrderLineSerializer {

	public String serializerList(List<PurchaseOrderLine> purchaseOrderLineList);
}

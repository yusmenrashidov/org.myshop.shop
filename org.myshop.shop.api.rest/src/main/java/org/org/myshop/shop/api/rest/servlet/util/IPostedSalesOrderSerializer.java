package org.org.myshop.shop.api.rest.servlet.util;

import java.util.List;
import org.myshop.shop.model.PostedSalesOrder;

public interface IPostedSalesOrderSerializer {

	public String serializeLis(List<PostedSalesOrder> postedSalesOrderList);
}

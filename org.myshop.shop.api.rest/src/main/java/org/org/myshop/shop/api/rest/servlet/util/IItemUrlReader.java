package org.org.myshop.shop.api.rest.servlet.util;

import javax.servlet.http.HttpServletRequest;

public interface IItemUrlReader {

	public String getIdFromQuery(HttpServletRequest request);
	
}

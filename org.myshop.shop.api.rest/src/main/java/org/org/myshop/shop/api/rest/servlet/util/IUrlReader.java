package org.org.myshop.shop.api.rest.servlet.util;

import javax.servlet.http.HttpServletRequest;

public interface IUrlReader {

	public String getIdFromQuery(HttpServletRequest request);
	
}

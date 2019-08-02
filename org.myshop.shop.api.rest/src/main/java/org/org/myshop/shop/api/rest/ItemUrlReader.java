package org.org.myshop.shop.api.rest;

import javax.servlet.http.HttpServletRequest;

import org.org.myshop.shop.api.rest.servlet.util.IItemUrlReader;

public class ItemUrlReader implements IItemUrlReader{

	public String getIdFromQuery(HttpServletRequest request) {
	
		return request.getQueryString();
	}

}

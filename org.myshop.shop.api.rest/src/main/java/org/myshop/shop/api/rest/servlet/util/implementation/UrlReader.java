package org.myshop.shop.api.rest.servlet.util.implementation;

import javax.servlet.http.HttpServletRequest;

import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class UrlReader implements IUrlReader{

	public String getIdFromQuery(HttpServletRequest request) {
	
		return request.getQueryString();
	}

}

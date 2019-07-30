package org.org.myshop.shop.api.rest.servlet.util;

import javax.servlet.http.HttpServletRequest;

public interface IRequestBodyReader {
    
    public String readBody(HttpServletRequest request);

}

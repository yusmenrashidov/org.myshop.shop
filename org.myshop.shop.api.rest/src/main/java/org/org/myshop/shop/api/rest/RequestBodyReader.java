package org.org.myshop.shop.api.rest;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;

public class RequestBodyReader implements IRequestBodyReader {

    public String readBody(HttpServletRequest request) {
        
    	StringBuilder buffer = new StringBuilder();
    	BufferedReader reader;
    	String requestLine;
    	
    	try {
			reader = request.getReader();
		
			while((requestLine = reader.readLine()) != null)
				buffer.append(requestLine);
			
    	} catch (IOException e) {
			return null;
		}
    	
        return buffer.toString();
    }

}

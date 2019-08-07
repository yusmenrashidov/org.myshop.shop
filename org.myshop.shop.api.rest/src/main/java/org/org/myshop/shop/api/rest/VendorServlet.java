package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.api.rest.servlet.util.implementation.RequestBodyReader;
import org.myshop.shop.api.rest.servlet.util.implementation.UrlReader;
import org.myshop.shop.api.rest.servlet.util.implementation.VendorDeserializer;
import org.myshop.shop.api.rest.servlet.util.implementation.VendorSerializer;

import org.myshop.shop.dao.VendorDao;
import org.myshop.shop.model.Vendor;

import org.org.myshop.shop.api.rest.servlet.exc.VendorDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;
import org.org.myshop.shop.api.rest.servlet.util.IVendorDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IVendorSerializer;

@WebServlet(name = "vendorServlet", urlPatterns = {"/api/v1/model/customer"})
public class VendorServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private IRequestBodyReader requestBodyReader;
	
	private IVendorDeserializer vendorDeserializer;
	
	private IVendorSerializer vendorSerializer;
	
	private IUrlReader urlReader;
	
	private VendorDao vendorDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		Vendor vendor = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			vendor = vendorDeserializer.deserialize(requestBody);
			
			vendorDao.create(vendor);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(VendorDeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List <Vendor> vendorList;
		vendorList = vendorDao.read();
		
		if(vendorList == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else if(vendorList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		
		}else {
			
			PrintWriter printWriter = response.getWriter();
			response.setContentType("application/json");
			
			printWriter.write(vendorSerializer.serializeList(vendorList));
			printWriter.flush();
			printWriter.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String requestBody;
		Vendor vendor = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			vendor = vendorDeserializer.deserialize(requestBody);
			vendor = vendorDao.update(vendor);
		
		}catch(VendorDeserializationException e ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(vendor == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else {
			response.setStatus(HttpServletResponse.SC_OK);	
		}
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		Vendor vendor = null;
		
		id = urlReader.getIdFromQuery(request);
		vendor = vendorDao.get(id);
		
		if(vendor == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else {
			vendorDao.delete(vendor);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null) {
            requestBodyReader = new RequestBodyReader();
        }
        return requestBodyReader;
    }
	
	public IVendorDeserializer getVendorDeserializer() {
        if (vendorDeserializer == null) {
        	vendorDeserializer = new  VendorDeserializer();
        }
        return vendorDeserializer;
    }
	
	public IVendorSerializer getVendorSerializer() {
    	if(vendorSerializer == null) {
    		vendorSerializer = new VendorSerializer();
    	}
    	return vendorSerializer;
    }
	
	public IUrlReader getUrlReader() {
		if(urlReader == null) {
			urlReader = new UrlReader();
		}
		return urlReader;
	}
	
	public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
        this.requestBodyReader = requestBodyReader;
    }
	
	public void setVendorSerializer(IVendorSerializer vendorSerializer) {
		this.vendorSerializer = vendorSerializer;
	}
	
	public void setVendorDeserializer(IVendorDeserializer vendorDeserializer) {
		this.vendorDeserializer = vendorDeserializer;
	}
	
	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}
	
	public void setVendorDao(VendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}
}

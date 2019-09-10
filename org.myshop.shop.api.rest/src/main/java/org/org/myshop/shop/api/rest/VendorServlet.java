package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.api.rest.servlet.util.implementation.Deserializer;
import org.myshop.shop.api.rest.servlet.util.implementation.RequestBodyReader;
import org.myshop.shop.api.rest.servlet.util.implementation.Serializer;
import org.myshop.shop.api.rest.servlet.util.implementation.UrlReader;

import org.myshop.shop.dao.VendorDao;
import org.myshop.shop.model.Vendor;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name = "vendorServlet", urlPatterns = {"/api/v1/model/vendor"})
public class VendorServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<Vendor> deserializer;
	
	private ISerializer<Vendor> serializer;
	
	private IUrlReader urlReader;
	
	private VendorDao vendorDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		Vendor vendor = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			vendor = deserializer.deserialize(requestBody);
			
			vendorDao.create(vendor);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(DeserializationException e) {
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
			
			printWriter.write(serializer.serializeList(vendorList));
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
			vendor = deserializer.deserialize(requestBody);
			vendor = vendorDao.update(vendor);
		
		}catch(DeserializationException e ) {
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
	
	public IDeserializer<Vendor> getDeserializer() {
        if (deserializer == null) {
        	deserializer = new Deserializer<Vendor>(Vendor.class);
        }
        return deserializer;
    }
	
	public ISerializer<Vendor> getSerializer() {
    	if(serializer == null) {
    		serializer = new Serializer<Vendor>(Vendor.class);
    	}
    	return serializer;
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
	
	public void setSerializer(ISerializer<Vendor> serializer) {
		this.serializer = serializer;
	}
	
	public void setDeserializer(IDeserializer<Vendor> deserializer) {
		this.deserializer = deserializer;
	}
	
	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}
	
	public void setVendorDao(VendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}
}

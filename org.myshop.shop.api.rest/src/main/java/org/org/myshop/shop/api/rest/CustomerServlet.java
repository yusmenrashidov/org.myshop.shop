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
import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.model.Customer;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name = "CustomerServlet", urlPatterns = {"/api/v1/model/customer"})
public class CustomerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<Customer> deserializer;
	
	private ISerializer<Customer> serializer;
	
	private IUrlReader urlReader;
	
	private CustomerDao customerDao;
	
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		Customer customer = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			customer =  deserializer.deserialize(requestBody);
			
			customerDao.create(customer);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List <Customer> customerList;
		customerList = customerDao.read();
		
		if(customerList == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else if(customerList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		
		}else {

			PrintWriter printWriter = response.getWriter();
			response.setContentType("application/json");
			
			printWriter.write(serializer.serializeList(customerList));
			printWriter.flush();
			printWriter.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String requestBody;
		Customer customer = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			customer = deserializer.deserialize(requestBody);
			customer = customerDao.update(customer);
			
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(customer == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else {
			response.setStatus(HttpServletResponse.SC_OK);	
		}
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		Customer customer = null;
		
		id = urlReader.getIdFromQuery(request);
		customer = customerDao.get(id);
		
		if(customer == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else {
			customerDao.delete(customer);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
    public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null) {
            requestBodyReader = new RequestBodyReader();
        }
        return requestBodyReader;
    }
    
    
    public ISerializer<Customer> getSerializer() {
    	if(serializer == null) {
    		serializer = new Serializer<Customer>(Customer.class);
    	}
    	return serializer;
    }  
    
    public IDeserializer<Customer> getDeserializer() {
    	if(deserializer == null) {
    		deserializer = new Deserializer<Customer>(Customer.class);
    	}
    	return deserializer;
    }
    
    public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
        this.requestBodyReader = requestBodyReader;
    }

	public void setDeserializer(IDeserializer<Customer> deserializer) {
		this.deserializer = deserializer;
	}
	
	public void setSerializer(ISerializer<Customer> serializer) {
		this.serializer = serializer;
	}
	
	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
}

package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.api.rest.servlet.util.implementation.CustomerDeserializer;
import org.myshop.shop.api.rest.servlet.util.implementation.CustomerSerializer;
import org.myshop.shop.api.rest.servlet.util.implementation.RequestBodyReader;

import org.myshop.shop.dao.CustomerDao;
import org.myshop.shop.model.Customer;

import org.org.myshop.shop.api.rest.servlet.exc.CustomerDeserializationException;

import org.org.myshop.shop.api.rest.servlet.util.ICustomerDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.ICustomerSerializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name = "CustomerServlet", urlPatterns = {"/api/v1/model/customer"})
public class CustomerServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private IRequestBodyReader requestBodyReader;
	
	private ICustomerDeserializer customerDeserializer;
	
	private ICustomerSerializer customerSerializer;
	
	private IUrlReader urlReader;
	
	private CustomerDao customerDao;
	
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		Customer customer = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			customer = customerDeserializer.deserialize(requestBody);
			
			customerDao.create(customer);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch(CustomerDeserializationException e) {
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
			
			printWriter.write(customerSerializer.serializerList(customerList));
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
			customer = customerDeserializer.deserialize(requestBody);
			customer = customerDao.update(customer);
			
		}catch(CustomerDeserializationException e) {
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
    
    public ICustomerDeserializer getCustomerDeserializer() {
        if (customerDeserializer == null) {
        	customerDeserializer = new  CustomerDeserializer();
        }
        return customerDeserializer;
    }
    
    public ICustomerSerializer getCustomerSerializer() {
    	if(customerSerializer == null) {
    		customerSerializer = new CustomerSerializer();
    	}
    	return customerSerializer;
    }  
    
    public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
        this.requestBodyReader = requestBodyReader;
    }

	public void setCustomerDeserializer(ICustomerDeserializer customerDeserializer) {
		this.customerDeserializer = customerDeserializer;
	}

	public void setCustomerSerializer(ICustomerSerializer customerSerializer) {
		this.customerSerializer = customerSerializer;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
    
    
}

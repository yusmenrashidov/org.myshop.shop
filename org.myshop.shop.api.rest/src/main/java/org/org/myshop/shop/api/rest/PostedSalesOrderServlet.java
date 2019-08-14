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

import org.myshop.shop.dao.PostedSalesOrderDao;
import org.myshop.shop.model.PostedSalesOrder;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name = "PostedSalesOrderServlet", urlPatterns = {"/api/v1/model/postedSalesOrder"})
public class PostedSalesOrderServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<PostedSalesOrder> deserializer;
	
	private ISerializer<PostedSalesOrder> serializer;
	
	private IUrlReader urlReader;
	
	private PostedSalesOrderDao postedSalesOrderDao;

	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		PostedSalesOrder order = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			order = deserializer.deserialize(requestBody);
			
			postedSalesOrderDao.create(order);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<PostedSalesOrder> orderList;
		orderList = postedSalesOrderDao.read();
		
		if(orderList == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else if(orderList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			
		}else {
			
			PrintWriter printWriter = response.getWriter();
			response.setContentType("application/json");
			
			printWriter.write(serializer.serializeList(orderList));
			printWriter.flush();
			printWriter.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String requestBody;
		PostedSalesOrder order = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			order = deserializer.deserialize(requestBody);
			order = postedSalesOrderDao.update(order);
			
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(order == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			response.setStatus(HttpServletResponse.SC_OK);	
		}
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		PostedSalesOrder order = null;
		
		id = urlReader.getIdFromQuery(request);
		order = postedSalesOrderDao.get(id);
		
		if(order == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else{
			postedSalesOrderDao.delete(order);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null) {
            requestBodyReader = new RequestBodyReader();
        }
        return requestBodyReader;
    }
	
	 public IDeserializer<PostedSalesOrder> getDeserializer() {
	        if (deserializer == null) {
	        	deserializer = new  Deserializer<PostedSalesOrder>(PostedSalesOrder.class);
	        }
	        return deserializer;
	 }
	 
	 public ISerializer<PostedSalesOrder> getSerializer() {
		 if(serializer == null) {
			 serializer = new Serializer<PostedSalesOrder>(PostedSalesOrder.class);
		 }
		 return serializer;
	 }

	public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
		this.requestBodyReader = requestBodyReader;
	}

	public void setDeserializer(IDeserializer<PostedSalesOrder> deserializer) {
		this.deserializer = deserializer;
	}

	public void setSerializer(ISerializer<PostedSalesOrder> serializer) {
		this.serializer = serializer;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setPostedSalesOrderDao(PostedSalesOrderDao postedSalesOrderDao) {
		this.postedSalesOrderDao = postedSalesOrderDao;
	}
}

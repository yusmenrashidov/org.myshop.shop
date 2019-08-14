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

import org.myshop.shop.dao.SalesOrderDao;
import org.myshop.shop.model.SalesOrder;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name = "salesOrderServlet", urlPatterns = {"/api/v1/model/salesOrder"})
public class SalesOrderServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<SalesOrder> deserializer;
	
	private ISerializer<SalesOrder> serializer;
	
	private IUrlReader urlReader;
	
	private SalesOrderDao salesOrderDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		SalesOrder salesOrder = null; 
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			salesOrder = deserializer.deserialize(requestBody);
			
			salesOrderDao.create(salesOrder);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		List<SalesOrder> salesOrderList;
		salesOrderList = salesOrderDao.read();
		
		if(salesOrderList == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else if(salesOrderList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			
		}else {
			
			PrintWriter printWriter = response.getWriter();
			response.setContentType("application/json");
			
			printWriter.write(serializer.serializeList(salesOrderList));
			printWriter.flush();
			printWriter.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String requestBody;
		SalesOrder salesOrder = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			salesOrder = deserializer.deserialize(requestBody);
			salesOrder = salesOrderDao.update(salesOrder);
		
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(salesOrder == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			response.setStatus(HttpServletResponse.SC_OK);	
		}
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		SalesOrder salesOrder = null;
		
		id = urlReader.getIdFromQuery(request);
		salesOrder = salesOrderDao.get(id);
		
		if(salesOrder == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			salesOrderDao.delete(salesOrder);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null) {
            requestBodyReader = new RequestBodyReader();
        }
        return requestBodyReader;
    }
	
	public IDeserializer<SalesOrder> getDeserializer() {
		if(deserializer == null) {
			deserializer = new Deserializer<SalesOrder>(SalesOrder.class);
		}	
		return deserializer;
	}
	
	public ISerializer<SalesOrder> getrSerializer() {
		if(serializer == null) {
			serializer = new Serializer<SalesOrder>(SalesOrder.class);
		}
		return serializer;
	}

	public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
		this.requestBodyReader = requestBodyReader;
	}

	public void setDeserializer(IDeserializer<SalesOrder> deserializer) {
		this.deserializer = deserializer;
	}

	public void setSerializer(ISerializer<SalesOrder> serializer) {
		this.serializer = serializer;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setSalesOrderDao(SalesOrderDao salesOrderDao) {
		this.salesOrderDao = salesOrderDao;
	}
	
	
}

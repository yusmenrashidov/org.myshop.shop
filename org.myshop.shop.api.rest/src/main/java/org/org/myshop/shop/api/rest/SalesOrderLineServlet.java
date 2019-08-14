package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.api.rest.servlet.util.implementation.Deserializer;
import org.myshop.shop.api.rest.servlet.util.implementation.RequestBodyReader;
import org.myshop.shop.api.rest.servlet.util.implementation.Serializer;

import org.myshop.shop.dao.SalesOrderLineDao;
import org.myshop.shop.model.SalesOrderLine;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class SalesOrderLineServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<SalesOrderLine> deserializer;
	
	private ISerializer<SalesOrderLine> serializer;
	
	private IUrlReader urlReader;
	
	private SalesOrderLineDao salesOrderLineDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		SalesOrderLine salesOrderLine = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			salesOrderLine = deserializer.deserialize(requestBody);
			
			salesOrderLineDao.create(salesOrderLine);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<SalesOrderLine> salesOrderLineList;
		salesOrderLineList = salesOrderLineDao.read();
		
		if(salesOrderLineList == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else if(salesOrderLineList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			
		}else {
			
			PrintWriter printWriter = response.getWriter();
			response.setContentType("application/json");
			
			printWriter.write(serializer.serializeList(salesOrderLineList));
			printWriter.flush();
			printWriter.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String requestBody;
		SalesOrderLine salesOrderLine = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			salesOrderLine = deserializer.deserialize(requestBody);
			salesOrderLine = salesOrderLineDao.update(salesOrderLine);
			
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);	
		}
		
		if(salesOrderLine == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			response.setStatus(HttpServletResponse.SC_OK);	
		}
	}

	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		SalesOrderLine salesOrderLine = null;
		
		id = urlReader.getIdFromQuery(request);
		salesOrderLine = salesOrderLineDao.get(id);
		
		if(salesOrderLine == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			salesOrderLineDao.delete(salesOrderLine);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null) {
            requestBodyReader = new RequestBodyReader();
        }
        return requestBodyReader;
    }
	
	public IDeserializer<SalesOrderLine> getDeserializer() {
        if (deserializer == null) {
        	deserializer = new Deserializer<SalesOrderLine>(SalesOrderLine.class);
        }
        return deserializer;
    }
	
	public ISerializer<SalesOrderLine> getSerializer() {
    	if(serializer == null) {
    		serializer = new Serializer<SalesOrderLine>(SalesOrderLine.class);
    	}
    	return serializer;
    }

	public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
		this.requestBodyReader = requestBodyReader;
	}

	public void setDeserializer(IDeserializer<SalesOrderLine> deserializer) {
		this.deserializer = deserializer;
	}

	public void setSerializer(ISerializer<SalesOrderLine> serializer) {
		this.serializer = serializer;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setSalesOrderLineDao(SalesOrderLineDao salesOrderLineDao) {
		this.salesOrderLineDao = salesOrderLineDao;
	}
}

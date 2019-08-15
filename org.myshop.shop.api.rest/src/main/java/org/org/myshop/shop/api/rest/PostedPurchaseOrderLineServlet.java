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

import org.myshop.shop.dao.PostedPurchaseOrderLineDao;
import org.myshop.shop.model.PostedPurchaseOrderLine;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

public class PostedPurchaseOrderLineServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<PostedPurchaseOrderLine> deserializer;
	
	private ISerializer<PostedPurchaseOrderLine> serializer;
	
	private IUrlReader urlReader;
	
	private PostedPurchaseOrderLineDao postedPurchaseOrderLineDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		PostedPurchaseOrderLine line = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			line = deserializer.deserialize(requestBody);
			
			postedPurchaseOrderLineDao.create(line);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		} catch (DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}	
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<PostedPurchaseOrderLine> lineList;
		lineList = postedPurchaseOrderLineDao.read();
		
		if(lineList == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else if(lineList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			
		}else {
			PrintWriter printWriter = response.getWriter();
			response.setContentType("application/json");
			
			printWriter.write(serializer.serializeList(lineList));
			printWriter.flush();
			printWriter.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String requestBody;
		PostedPurchaseOrderLine line = null;
		
		
		try {
			requestBody = requestBodyReader.readBody(request);
			line = deserializer.deserialize(requestBody);
			line = postedPurchaseOrderLineDao.update(line);
			
		} catch (DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(line == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else {
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		PostedPurchaseOrderLine line = null;
		
		id = urlReader.getIdFromQuery(request);
		line = postedPurchaseOrderLineDao.get(id);
		
		if(line == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			postedPurchaseOrderLineDao.delete(line);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null)
            requestBodyReader = new RequestBodyReader();
        
        return requestBodyReader;
    }
	
	public IDeserializer<PostedPurchaseOrderLine> getDeserializer(){
		if(deserializer == null) {
			deserializer = new Deserializer<PostedPurchaseOrderLine>(PostedPurchaseOrderLine.class);
		}
		return deserializer;
	}
	
	public ISerializer<PostedPurchaseOrderLine> getSerializer() {
		if(serializer == null)
			serializer = new Serializer<PostedPurchaseOrderLine>(PostedPurchaseOrderLine.class);
		
		return serializer;
	}

	public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
		this.requestBodyReader = requestBodyReader;
	}

	public void setDeserializer(IDeserializer<PostedPurchaseOrderLine> deserializer) {
		this.deserializer = deserializer;
	}

	public void setSerializer(ISerializer<PostedPurchaseOrderLine> serializer) {
		this.serializer = serializer;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setPostedPurchaseOrderLineDao(PostedPurchaseOrderLineDao postedPurchaseOrderLineDao) {
		this.postedPurchaseOrderLineDao = postedPurchaseOrderLineDao;
	}
	
	
}

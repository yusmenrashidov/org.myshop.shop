package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.dao.PostedPurchaseOrderDao;
import org.myshop.shop.model.PostedPurchaseOrder;
import org.myshop.shop.api.rest.servlet.util.implementation.Deserializer;
import org.myshop.shop.api.rest.servlet.util.implementation.RequestBodyReader;
import org.myshop.shop.api.rest.servlet.util.implementation.Serializer;
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name = "PosterPurchaseOrderServlet", urlPatterns = {"/api/v1/model/postedPurchaseOrder"})
public class PostedPurchaseOrderServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<PostedPurchaseOrder> deserializer;
	
	private ISerializer<PostedPurchaseOrder> serializer;
	
	private IUrlReader urlReader;
	
	private PostedPurchaseOrderDao postedPurchaseOrderDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		PostedPurchaseOrder order = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			order = deserializer.deserialize(requestBody);
			
			postedPurchaseOrderDao.create(order);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<PostedPurchaseOrder> orderList;
		orderList = postedPurchaseOrderDao.read();
		
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
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String requestBody;
		PostedPurchaseOrder order = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			order = deserializer.deserialize(requestBody);
			
			order = postedPurchaseOrderDao.update(order);
			
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
		PostedPurchaseOrder order = null;
		
		id = urlReader.getIdFromQuery(request);
		order = postedPurchaseOrderDao.get(id);
		
		if(order == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			postedPurchaseOrderDao.delete(order);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null) {
            requestBodyReader = new RequestBodyReader();
        }
        return requestBodyReader;
    }
	
	public IDeserializer<PostedPurchaseOrder> getDeserializer() {
        if (deserializer == null) {
        	deserializer = new  Deserializer<PostedPurchaseOrder>(PostedPurchaseOrder.class);
        }
        return deserializer;
    }
    
    public ISerializer<PostedPurchaseOrder> getSerializer() {
    	if(serializer == null) {
    		serializer = new Serializer<PostedPurchaseOrder>(PostedPurchaseOrder.class);
    	}
    	return serializer;
    }

	public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
		this.requestBodyReader = requestBodyReader;
	}

	public void setDeserializer(IDeserializer<PostedPurchaseOrder> deserializer) {
		this.deserializer = deserializer;
	}

	public void setSerializer(ISerializer<PostedPurchaseOrder> serializer) {
		this.serializer = serializer;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setPostedPurchaseOrderDao(PostedPurchaseOrderDao postedPurchaseOrderDao) {
		this.postedPurchaseOrderDao = postedPurchaseOrderDao;
	}  

    
}

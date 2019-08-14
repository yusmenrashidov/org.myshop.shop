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

import org.myshop.shop.dao.PurchaseOrderLineDao;
import org.myshop.shop.model.PurchaseOrderLine;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name = "purchaseOrderLineServlet", urlPatterns = {"/api/v1/model/purchaseOrderLine"})
public class PurchaseOrderLineServlet extends HttpServlet{

private static final long serialVersionUID = 1L;
	
	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<PurchaseOrderLine> deserializer;
	
	private ISerializer<PurchaseOrderLine> serializer;
	
	private IUrlReader urlReader;
	
	private PurchaseOrderLineDao purchaseOrderLineDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		PurchaseOrderLine purchaseOrderLine = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			purchaseOrderLine = deserializer.deserialize(requestBody);
			
			purchaseOrderLineDao.create(purchaseOrderLine);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<PurchaseOrderLine> purchaseOrderLineList;
		purchaseOrderLineList = purchaseOrderLineDao.read();
		
		if(purchaseOrderLineList == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else if(purchaseOrderLineList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			
		}else {
			
			PrintWriter printWriter = response.getWriter();
			response.setContentType("application/json");
			
			printWriter.write(serializer.serializeList(purchaseOrderLineList));
			printWriter.flush();
			printWriter.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String requestBody;
		PurchaseOrderLine purchaseOrderLine = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			purchaseOrderLine = deserializer.deserialize(requestBody);
			purchaseOrderLine = purchaseOrderLineDao.update(purchaseOrderLine);
			
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(purchaseOrderLine == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			response.setStatus(HttpServletResponse.SC_OK);	
		}
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		PurchaseOrderLine purchaseOrderLine = null;
		
		id = urlReader.getIdFromQuery(request);
		purchaseOrderLine = purchaseOrderLineDao.get(id);
		
		if(purchaseOrderLine == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			purchaseOrderLineDao.delete(purchaseOrderLine);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null) {
            requestBodyReader = new RequestBodyReader();
        }
        return requestBodyReader;
    }
	
	public IDeserializer<PurchaseOrderLine> getDeserializer() {
        if (deserializer == null) {
        	deserializer = new Deserializer<PurchaseOrderLine>(PurchaseOrderLine.class);
        }
        return deserializer;
    }
    
    public ISerializer<PurchaseOrderLine> geteSerializer() {
    	if(serializer == null) {
    		serializer = new Serializer<PurchaseOrderLine>(PurchaseOrderLine.class);
    	}
    	return serializer;
    }

	public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
		this.requestBodyReader = requestBodyReader;
	}

	public void setDeserializer(IDeserializer<PurchaseOrderLine> deserializer) {
		this.deserializer = deserializer;
	}

	public void setSerializer(ISerializer<PurchaseOrderLine> serializer) {
		this.serializer = serializer;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setPurchaseOrderLineDao(PurchaseOrderLineDao purchaseOrderLineDao) {
		this.purchaseOrderLineDao = purchaseOrderLineDao;
	}
    
    
}

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

import org.myshop.shop.dao.PurchaseOrderDao;
import org.myshop.shop.model.PurchaseOrder;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name ="PurchaseOrderServlet", urlPatterns = {"/api/v1/model/purchaseOrder"})
public class PurchaseOrderServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<PurchaseOrder> deserializer;
	
	private ISerializer<PurchaseOrder> serializer;
	
	private IUrlReader urlReader;
	
	private PurchaseOrderDao purchaseOrderDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		PurchaseOrder purchaseOrder = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			purchaseOrder = deserializer.deserialize(requestBody);
			
			purchaseOrderDao.create(purchaseOrder);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List <PurchaseOrder> purchaseOrderList;
		purchaseOrderList = purchaseOrderDao.read();
		
		if(purchaseOrderList == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else if(purchaseOrderList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			
		}else {
			
			PrintWriter printWriter = response.getWriter();
			response.setContentType("application/json");
			
			printWriter.write(serializer.serializeList(purchaseOrderList));
			printWriter.flush();
			printWriter.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String requestBody;
		PurchaseOrder purchaseOrder = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			purchaseOrder = deserializer.deserialize(requestBody);
			purchaseOrder = purchaseOrderDao.update(purchaseOrder);
		
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(purchaseOrder == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else {
			response.setStatus(HttpServletResponse.SC_OK);	
		}
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		PurchaseOrder purchaseOrder = null;
		
		id = urlReader.getIdFromQuery(request);
		purchaseOrder = purchaseOrderDao.get(id);
		
		if(purchaseOrder == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else {
			purchaseOrderDao.delete(purchaseOrder);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null) {
            requestBodyReader = new RequestBodyReader();
        }
        return requestBodyReader;
    }
	
	public IDeserializer<PurchaseOrder> getDeserializer() {
		if(deserializer == null) {
			deserializer = new Deserializer<PurchaseOrder>(PurchaseOrder.class);
		}
		return deserializer;
	}
	
	public ISerializer<PurchaseOrder> getSerializer() {
		if(serializer == null) {
			serializer = new Serializer<PurchaseOrder>(PurchaseOrder.class);
		}
		return serializer;
	}

	public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
		this.requestBodyReader = requestBodyReader;
	}

	public void setDeserializer(IDeserializer<PurchaseOrder> deserializer) {
		this.deserializer = deserializer;
	}

	public void setSerializer(ISerializer<PurchaseOrder> serializer) {
		this.serializer = serializer;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setPurchaseOrderDao(PurchaseOrderDao purchaseOrderDao) {
		this.purchaseOrderDao = purchaseOrderDao;
	}
	
}

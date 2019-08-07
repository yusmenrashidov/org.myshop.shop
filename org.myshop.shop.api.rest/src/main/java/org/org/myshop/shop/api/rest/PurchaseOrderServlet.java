package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.api.rest.servlet.util.implementation.PurchaseOrderDeserializer;
import org.myshop.shop.api.rest.servlet.util.implementation.PurchaseOrderSerializer;
import org.myshop.shop.api.rest.servlet.util.implementation.RequestBodyReader;
import org.myshop.shop.dao.PurchaseOrderDao;
import org.myshop.shop.model.PurchaseOrder;
import org.org.myshop.shop.api.rest.servlet.exc.PurchaseOrderDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IPurchaseOrderDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IPurchaseOrderSerializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name ="PurchaseOrderServlet", urlPatterns = {"/api/v1/model/purchaseOrder"})
public class PurchaseOrderServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private IRequestBodyReader requestBodyReader;
	
	private IPurchaseOrderDeserializer purchaseOrderDeserializer;
	
	private IPurchaseOrderSerializer purchaseOrderSerializer;
	
	private IUrlReader urlReader;
	
	private PurchaseOrderDao purchaseOrderDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		PurchaseOrder purchaseOrder = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			purchaseOrder = purchaseOrderDeserializer.deserialize(requestBody);
			
			purchaseOrderDao.create(purchaseOrder);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(PurchaseOrderDeserializationException e) {
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
			
			printWriter.write(purchaseOrderSerializer.serializerList(purchaseOrderList));
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
			purchaseOrder = purchaseOrderDeserializer.deserialize(requestBody);
			purchaseOrder = purchaseOrderDao.update(purchaseOrder);
		
		}catch(PurchaseOrderDeserializationException e) {
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
	
	public IPurchaseOrderDeserializer getPurchaseOrderDeserializer() {
		if(purchaseOrderDeserializer == null) {
			purchaseOrderDeserializer = new PurchaseOrderDeserializer();
		}
		return purchaseOrderDeserializer;
	}
	
	public IPurchaseOrderSerializer getPurchaseOrderSerializer() {
		if(purchaseOrderSerializer == null) {
			purchaseOrderSerializer = new PurchaseOrderSerializer();
		}
		return purchaseOrderSerializer;
	}

	public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
		this.requestBodyReader = requestBodyReader;
	}

	public void setPurchaseOrderDeserializer(IPurchaseOrderDeserializer purchaseOrderDeserializer) {
		this.purchaseOrderDeserializer = purchaseOrderDeserializer;
	}

	public void setPurchaseOrderSerializer(IPurchaseOrderSerializer purchaseOrderSerializer) {
		this.purchaseOrderSerializer = purchaseOrderSerializer;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setPurchaseOrderDao(PurchaseOrderDao purchaseOrderDao) {
		this.purchaseOrderDao = purchaseOrderDao;
	}
	
}

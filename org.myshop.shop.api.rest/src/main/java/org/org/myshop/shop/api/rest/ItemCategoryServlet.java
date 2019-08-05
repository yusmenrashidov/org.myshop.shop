package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.api.rest.servlet.util.implementation.ItemCategoryDeserializer;
import org.myshop.shop.api.rest.servlet.util.implementation.ItemCategorySerializer;
import org.myshop.shop.api.rest.servlet.util.implementation.RequestBodyReader;

import org.myshop.shop.dao.ItemCategoryDao;
import org.myshop.shop.model.ItemCategory;

import org.org.myshop.shop.api.rest.servlet.exc.ItemCategoryDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IItemCategoryDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IItemCategorySerializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name="itemCateogryServlet", urlPatterns = {"/api/v1/model/itemCategory"})
public class ItemCategoryServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	private IRequestBodyReader requestBodyReader;
	
	private IItemCategorySerializer itemCategorySerializer;
	
	private IItemCategoryDeserializer itemCategoryDeserializer;
	
	private IUrlReader urlReader;
	
	private ItemCategoryDao itemCategoryDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		ItemCategory itemCategory = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			itemCategory = itemCategoryDeserializer.deserialize(requestBody);
			
			itemCategoryDao.create(itemCategory);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(ItemCategoryDeserializationException e){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<ItemCategory> itemCategoryList;
		itemCategoryList = itemCategoryDao.read();
		
		if(itemCategoryList == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else if(itemCategoryList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			
		}else {
			PrintWriter printWriter = response.getWriter();
			response.setContentType("application/json");
			
			printWriter.write(itemCategorySerializer.serializerList(itemCategoryList));
			printWriter.flush();
			printWriter.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String requestBody;
		ItemCategory itemCategory = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			itemCategory = itemCategoryDeserializer.deserialize(requestBody);
			itemCategory = itemCategoryDao.update(itemCategory);
		
		}catch(ItemCategoryDeserializationException e ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(itemCategory == null)
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		else
			response.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		ItemCategory itemCategory = null;
		
		id = urlReader.getIdFromQuery(request);
		itemCategory = itemCategoryDao.get(id);
		
		if(itemCategory == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else {
			itemCategoryDao.delete(itemCategory);
			response.setStatus(HttpServletResponse.SC_OK);
		}	
	}
    
	public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null)
            requestBodyReader = new RequestBodyReader();
        
        return requestBodyReader;
    }
	
	public IItemCategoryDeserializer getItemCategoryDeserializer() {
		if(itemCategoryDeserializer == null)
			itemCategoryDeserializer = new ItemCategoryDeserializer();
		
		return itemCategoryDeserializer;
	}
	
	public IItemCategorySerializer getItemCategorySerializer() {
		if(itemCategorySerializer == null)
			itemCategorySerializer = new ItemCategorySerializer();
		
		return itemCategorySerializer;
	}
	
    public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
        this.requestBodyReader = requestBodyReader;
    }
    
    public void setItemCategoryDeserializer(IItemCategoryDeserializer itemCategoryDeserializer) {
    	this.itemCategoryDeserializer = itemCategoryDeserializer;
    }
    
    public void setItemCategorySerializer(IItemCategorySerializer itemCategorySerializer) {
    	this.itemCategorySerializer = itemCategorySerializer;
    }
    
    public void setItemCategoryDao(ItemCategoryDao itemCategoryDao) {
    	this.itemCategoryDao = itemCategoryDao;
    }
    
    public void setUlrReader(IUrlReader urlReader) {
    	this.urlReader = urlReader;
    }   
}



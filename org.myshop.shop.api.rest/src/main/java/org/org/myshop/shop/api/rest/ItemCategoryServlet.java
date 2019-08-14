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

import org.myshop.shop.dao.ItemCategoryDao;
import org.myshop.shop.model.ItemCategory;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name="itemCateogryServlet", urlPatterns = {"/api/v1/model/itemCategory"})
public class ItemCategoryServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;

	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<ItemCategory> deserializer;
	
	private ISerializer<ItemCategory> serializer;
	
	private IUrlReader urlReader;
	
	private ItemCategoryDao itemCategoryDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		ItemCategory itemCategory = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			itemCategory = deserializer.deserialize(requestBody);
			
			itemCategoryDao.create(itemCategory);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(DeserializationException e){
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
			
			printWriter.write(serializer.serializeList(itemCategoryList));
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
			itemCategory = deserializer.deserialize(requestBody);
			itemCategory = itemCategoryDao.update(itemCategory);
		
		}catch(DeserializationException e ) {
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
	
	public IDeserializer<ItemCategory> getDeserializer(){
		if(deserializer == null) {
			deserializer = new Deserializer<ItemCategory>(ItemCategory.class);
		}
		return deserializer;
	}
	
	public ISerializer<ItemCategory> getSerializer() {
		if(serializer == null)
			serializer = new Serializer<ItemCategory>(ItemCategory.class);
		
		return serializer;
	}
	
    public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
        this.requestBodyReader = requestBodyReader;
    }
    
    public void setDeserializer(IDeserializer<ItemCategory> deserializer) {
    	this.deserializer = deserializer;
    }
    
    public void setSerializer(ISerializer<ItemCategory> serializer) {
    	this.serializer = serializer;
    }
    
    public void setItemCategoryDao(ItemCategoryDao itemCategoryDao) {
    	this.itemCategoryDao = itemCategoryDao;
    }
    
    public void setUlrReader(IUrlReader urlReader) {
    	this.urlReader = urlReader;
    }   
}



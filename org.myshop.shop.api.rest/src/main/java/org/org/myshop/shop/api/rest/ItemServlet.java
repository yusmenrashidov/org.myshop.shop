package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.api.rest.servlet.utilImpl.ItemDeserializer;
import org.myshop.shop.api.rest.servlet.utilImpl.ItemSerializer;
import org.myshop.shop.api.rest.servlet.utilImpl.UrlReader;
import org.myshop.shop.api.rest.servlet.utilImpl.RequestBodyReader;

import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;

import org.org.myshop.shop.api.rest.servlet.exc.ItemDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IItemDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IItemSerializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;


@WebServlet(name = "itemServlet", urlPatterns = {"/api/v1/model/item"})
public class ItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IRequestBodyReader requestBodyReader;
	
	private IItemDeserializer itemDeserializer;
	
	private ItemSerializer itemSerializer;
	
	private UrlReader urlReader;
	
	private ItemDao itemDao;
	
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {

        String requestBody;
        Item item = null;

        try {
            requestBody = requestBodyReader.readBody(request);

            item = itemDeserializer.deserialize(requestBody);

            itemDao.create(item);

            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ItemDeserializationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
		
			List <Item> itemList;
			itemList = itemDao.read();
			
			if(itemList == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
			}else if(itemList.isEmpty())
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			else {
				
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				
				printWriter.write(itemSerializer.serializeList(itemList));
				printWriter.flush();
				printWriter.close();
				
				response.setStatus(HttpServletResponse.SC_OK);
			}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
			
			String requestBody;
			Item item = null;
			
			try {
				requestBody = requestBodyReader.readBody(request);
				item = itemDeserializer.deserialize(requestBody);
				item = itemDao.update(item);
				
			} catch (ItemDeserializationException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
			
			if(item == null)
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			else
				response.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String id;
		Item item = null;
		
		id = urlReader.getIdFromQuery(request);
		item = itemDao.get(id);
		
		if(item == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}else {
			itemDao.delete(item);
			response.setStatus(HttpServletResponse.SC_OK);
		}
		
	}
	
    public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null) {
            requestBodyReader = new RequestBodyReader();
        }
        return requestBodyReader;
    }

    public IItemDeserializer getItemDeserializer() {
        if (itemDeserializer == null) {
            itemDeserializer = new ItemDeserializer();
        }
        return itemDeserializer;
    }
    
    public IItemSerializer getItemSerializer() {
    	if(itemSerializer == null) {
    		itemSerializer = new ItemSerializer();
    	}
    	return itemSerializer;
    }
    
    public ItemDao getItemDao() {
        if (itemDao == null) {
            //TODO Init itemDao
        }
        return itemDao;
    }

    public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
        this.requestBodyReader = requestBodyReader;
    }

    public void setItemDeserializer(IItemDeserializer itemDeserializer) {
        this.itemDeserializer = itemDeserializer;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
    
    public void setItemSerializer(ItemSerializer itemSerializer) {
    	this.itemSerializer = (ItemSerializer) itemSerializer;
    }
    
    public void setItemUlrReader(UrlReader urlReader) {
    	this.urlReader = urlReader;
    }
}

package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.exc.ItemDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IItemDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;


@WebServlet(name = "itemServlet", urlPatterns = {"/api/v1/model/item"})
public class ItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IRequestBodyReader requestBodyReader;
	
	private IItemDeserializer itemDeserializer;
	
	private ItemDao itemDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
	    
		String requestBody;
		Item item = null;
		
		try {
	     requestBody = requestBodyReader.readBody(request);
	    
	     item = itemDeserializer.deserialize(requestBody);
	    
	     itemDao.create(item);
	     
		}catch(IOException e) {
	    	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    } catch (ItemDeserializationException e) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    }
	   
	    response.setStatus(HttpServletResponse.SC_ACCEPTED);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
			List <Item> itemList;
			itemList = itemDao.read();
		
			if(itemList == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
			}else if(itemList.isEmpty())
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			else {
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
}

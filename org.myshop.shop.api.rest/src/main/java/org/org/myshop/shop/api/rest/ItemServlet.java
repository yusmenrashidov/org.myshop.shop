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
import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;
import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name = "itemServlet", urlPatterns = {"/api/v1/model/item"})
public class ItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<Item> deserializer;
	
	private ISerializer<Item> serializer;
	
	private IUrlReader urlReader;
	
	private ItemDao itemDao;
	
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {

        String requestBody;
        Item item = null;

        try {
            requestBody = requestBodyReader.readBody(request);

            item = deserializer.deserialize(requestBody);

            itemDao.create(item);

            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (DeserializationException e) {
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
				
				printWriter.write(serializer.serializeList(itemList));
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
				item = deserializer.deserialize(requestBody);
				item = itemDao.update(item);
				
			} catch (DeserializationException e) {
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

    public IDeserializer<Item> getDeserializer() {
        if (deserializer == null) {
        	deserializer = new Deserializer<Item>(Item.class);
        }
        return deserializer;
    }
    
    public ISerializer<Item> getSerializer() {
    	if(serializer == null) {
    		serializer = new Serializer<Item>(Item.class);
    	}
    	return serializer;
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

    public void setDeserializer(IDeserializer<Item> deserializer) {
        this.deserializer = deserializer;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
    
    public void setSerializer(ISerializer<Item> serializer) {
    	this.serializer = serializer;
    }
    
    public void setItemUlrReader(IUrlReader urlReader) {
    	this.urlReader = urlReader;
    }
}

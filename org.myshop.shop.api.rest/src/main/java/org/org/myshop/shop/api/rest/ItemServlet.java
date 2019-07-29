package org.org.myshop.shop.api.rest;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.dao.ItemDao;
import org.myshop.shop.model.Item;
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
	    
	    String requestBody = requestBodyReader.readBody(request);
	    
	    Item item = itemDeserializer.deserialize(requestBody);
	    
	    itemDao.create(item);
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

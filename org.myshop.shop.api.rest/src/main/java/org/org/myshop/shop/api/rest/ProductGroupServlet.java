package org.org.myshop.shop.api.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.api.rest.servlet.util.implementation.ProductGroupDeserializer;
import org.myshop.shop.api.rest.servlet.util.implementation.ProductGroupSerializer;
import org.myshop.shop.api.rest.servlet.util.implementation.RequestBodyReader;
import org.myshop.shop.api.rest.servlet.util.implementation.UrlReader;
import org.myshop.shop.dao.ProductGroupDao;
import org.myshop.shop.model.ProductGroup;
import org.org.myshop.shop.api.rest.servlet.exc.ProductGroupDeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IProductGroupDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IProductGroupSerializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name = "productGroupServlet", urlPatterns = {"/api/v1/model/productGroup"})
public class ProductGroupServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private IRequestBodyReader requestBodyReader;
	
	private IProductGroupDeserializer deserializer;
	
	private IProductGroupSerializer serializer;
	
	private IUrlReader urlReader;
	
	private ProductGroupDao productGroupDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
			
		String requestBody;
		ProductGroup productGroup = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			productGroup = deserializer.deserialize(requestBody);
		
			productGroupDao.create(productGroup);
		
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		
		}catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (ProductGroupDeserializationException e) {
			 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}	
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
			
			List<ProductGroup> productGroupList;
			
			productGroupList = productGroupDao.read();
			
			if(productGroupList == null) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
			}else if(productGroupList.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			
			}else {
				PrintWriter printWriter = response.getWriter();
				response.setContentType("application/json");
				
				printWriter.write(serializer.serialize(productGroupList));
				printWriter.flush();
				printWriter.close();
				
				response.setStatus(HttpServletResponse.SC_OK);
			}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String requestBody;
		ProductGroup productGroup = null;
		
		try {
		requestBody = requestBodyReader.readBody(request);
		productGroup = deserializer.deserialize(requestBody);
		productGroup = productGroupDao.update(productGroup);
		
		} catch (ProductGroupDeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(productGroup == null)
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		else
			response.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		ProductGroup productGroup = null;
		
		id = urlReader.getIdFromQuery(request);
		productGroup = productGroupDao.get(id);
		
		if(productGroup == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
		}else {
			productGroupDao.delete(productGroup);
			response.setStatus(HttpServletResponse.SC_OK);
		}
		
	}
	
	
	public IRequestBodyReader getReader() {
		if(requestBodyReader == null)
			requestBodyReader = new RequestBodyReader();
		
		return requestBodyReader;
	}

	public void setRequestBodyReader(IRequestBodyReader reader) {
		this.requestBodyReader = reader;
	}

	public IProductGroupDeserializer getDeserializer() {
		if(deserializer == null)
			deserializer = new ProductGroupDeserializer();
		
		return deserializer;
	}

	public void setDeserializer(IProductGroupDeserializer deserializer) {
		this.deserializer = deserializer;
	}

	public IProductGroupSerializer getSerializer() {
		if(serializer == null)
			serializer = new ProductGroupSerializer();
		
		return serializer;
	}

	public void setSerializer(IProductGroupSerializer serializer) {
		this.serializer = serializer;
	}

	public IUrlReader getUrlReader() {
		if(urlReader == null)
		  urlReader = new UrlReader();
		
		return urlReader;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}
	
	public void setProductGroupDao(ProductGroupDao productGroupDao) {
		this.productGroupDao = productGroupDao;
	}
	
}

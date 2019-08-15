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

import org.myshop.shop.dao.PostedSalesOrderLineDao;
import org.myshop.shop.model.PostedSalesOrderLine;

import org.org.myshop.shop.api.rest.servlet.exc.DeserializationException;
import org.org.myshop.shop.api.rest.servlet.util.IDeserializer;
import org.org.myshop.shop.api.rest.servlet.util.IRequestBodyReader;
import org.org.myshop.shop.api.rest.servlet.util.ISerializer;
import org.org.myshop.shop.api.rest.servlet.util.IUrlReader;

@WebServlet(name = "PostedSalesOrderLineServlet", urlPatterns = {"/api/v1/model/postedSalesOrderLine"})
public class PostedSalesOrderLineServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	private IRequestBodyReader requestBodyReader;
	
	private IDeserializer<PostedSalesOrderLine> deserializer;
	
	private ISerializer<PostedSalesOrderLine> serializer;
	
	private IUrlReader urlReader;
	
	private PostedSalesOrderLineDao postedSalesOrderLineDao;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
		String requestBody;
		PostedSalesOrderLine line = null;
		
		try {
			requestBody = requestBodyReader.readBody(request);
			
			line = deserializer.deserialize(requestBody);
			
			postedSalesOrderLineDao.create(line);
			
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		} catch(IOException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}catch(DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<PostedSalesOrderLine> lineList;
		lineList = postedSalesOrderLineDao.read();
		
		if(lineList == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else if(lineList.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			
		}else {
			
			PrintWriter printWriter = response.getWriter();
			response.setContentType("application/json");
			
			printWriter.write(serializer.serializeList(lineList));
			printWriter.flush();
			printWriter.close();
			
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String requestBody;
		PostedSalesOrderLine line = null;
		
		try {
		
			requestBody = requestBodyReader.readBody(request);
			
			line = deserializer.deserialize(requestBody);
			
			line = postedSalesOrderLineDao.update(line);
	
		} catch (DeserializationException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		if(line == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		
		String id;
		PostedSalesOrderLine line = null;
		
		id = urlReader.getIdFromQuery(request);
		line = postedSalesOrderLineDao.get(id);
		
		if(line == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
		}else {
			postedSalesOrderLineDao.delete(line);
			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
	
	public IRequestBodyReader getRequestBodyReader() {
        if (requestBodyReader == null)
            requestBodyReader = new RequestBodyReader();
        
        return requestBodyReader;
    }
	
	public IDeserializer<PostedSalesOrderLine> getDeserializer(){
		if(deserializer == null) {
			deserializer = new Deserializer<PostedSalesOrderLine>(PostedSalesOrderLine.class);
		}
		return deserializer;
	}
	
	public ISerializer<PostedSalesOrderLine> getSerializer() {
		if(serializer == null)
			serializer = new Serializer<PostedSalesOrderLine>(PostedSalesOrderLine.class);
		
		return serializer;
	}

	public void setRequestBodyReader(IRequestBodyReader requestBodyReader) {
		this.requestBodyReader = requestBodyReader;
	}

	public void setDeserializer(IDeserializer<PostedSalesOrderLine> deserializer) {
		this.deserializer = deserializer;
	}

	public void setSerializer(ISerializer<PostedSalesOrderLine> serializer) {
		this.serializer = serializer;
	}

	public void setUrlReader(IUrlReader urlReader) {
		this.urlReader = urlReader;
	}

	public void setPostedSalesOrderLineDao(PostedSalesOrderLineDao postedSalesOrderLineDao) {
		this.postedSalesOrderLineDao = postedSalesOrderLineDao;
	}
	
	
}

package org.org.myshop.shop.api.rest;

import javax.servlet.http.HttpServlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myshop.shop.dao.jdbc.JdbcItemDao;
import org.myshop.shop.model.Item;

import com.google.gson.Gson;


@WebServlet(name = "itemServlet", urlPatterns = {"/api/v1/model/item"})
public class ItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Connection sqlConnection;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
			JdbcItemDao itemDao = new JdbcItemDao(sqlConnection);
			
					try {
						itemDao.create(this.queryToObject(request));
					} catch (IOException e) {
					
						e.printStackTrace();
					}
		
		
	}
	
	public Item queryToObject(HttpServletRequest request) throws IOException {
		
		Item item = new Gson().fromJson(request.getReader(), Item.class);
		
		return item;
	}
	

}

package org.org.myshop.shop.api.rest;

import javax.servlet.http.HttpServlet;

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
import org.myshop.shop.dao.jdbc.JdbcProductGroupDao;
import org.myshop.shop.dao.jdbc.JdbcItemCategoryDao;
import org.myshop.shop.model.Item;


@WebServlet(name = "itemServlet", urlPatterns = {"/api/v1/model/item"})
public class ItemServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Connection sqlConnection;
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) {
		
			JdbcItemDao itemDao = new JdbcItemDao(sqlConnection);
			
			itemDao.create(this.queryToObject(request));
		
	}
	
	public Item queryToObject(HttpServletRequest request) {
		
		Item item = new Item();
		
		JdbcProductGroupDao productGroupDao = new JdbcProductGroupDao(sqlConnection);
		JdbcItemCategoryDao itemCategoryDao = new JdbcItemCategoryDao(sqlConnection);
		
		String queryString = request.getQueryString();
		
		String fieldsString [] = queryString.split("&");
		
		String idString[] = fieldsString[0].split("=");
		String nameString[] = fieldsString[1].split("=");
		String descriptionString[] = fieldsString[2].split("=");
		String productGroupString[] = fieldsString[3].split("=");
		String itemCategoryString[] = fieldsString[4].split("=");
		String purchasePriceString[] = fieldsString[5].split("=");
		String salesPriceString[] = fieldsString[6].split("=");
		
		item.setId(idString[1]);
		item.setName(nameString[1]);
		item.setDescription(descriptionString[1]);
		item.setProductGroup(productGroupDao.get(productGroupString[1]));
		item.setItemCategory(itemCategoryDao.get(itemCategoryString[1]));
		item.setPurchasePrice(Float.parseFloat(purchasePriceString[1]));
		item.setSalesPrice(Float.parseFloat(salesPriceString[1]));
		
		return item;
	}
	

}

package org.org.myshop.shop.api.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.myshop.shop.model.Item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDeserializerTest {
	
	@JsonProperty
	private static final String TEST_JSON_STRING = "{\"id\": \"item_id\", \"name\": \"item_name\" , \"productGroup_id\": \"product_gpoup_id\","
			+ " \"itemCategory_id\" : \"item_category_id\" , \"purchasePrice\" : \"123.456\" , \"salesPrice\" : \"123.456\"}";	
	
	@JsonProperty
	private static final String TEST_JSON_FAIL_STRING = "test_fail";
			
	
	@Mock
	private ItemDeserializer itemDeserializerMock;
	
	@Mock
	private Item itemMock;
	
	@Mock
	private ObjectMapper mapperMock;
	
	@Before
	@JsonIgnoreProperties
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.initMocks(this);
		
		mapperMock = Mockito.mock(ObjectMapper.class);
		
		mapperMock.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		when(mapperMock.readValue(TEST_JSON_STRING, Item.class)).thenReturn(itemMock);
		when(mapperMock.readValue(TEST_JSON_FAIL_STRING, Item.class)).thenThrow(new IOException());
		
		itemDeserializerMock = new ItemDeserializer();
	}
	
	@Test
	public void test() throws IOException{
		Item item = itemDeserializerMock.deserialize(TEST_JSON_STRING);
		
		assertNotNull(item);
		
	}
	
	 @Test(expected = IOException.class)
	public void testIOException() throws IOException, JsonParseException, JsonMappingException {
		
		itemDeserializerMock.deserialize(TEST_JSON_FAIL_STRING);
	}
}

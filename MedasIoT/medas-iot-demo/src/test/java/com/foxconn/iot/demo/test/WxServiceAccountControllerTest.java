package com.foxconn.iot.demo.test;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WxServiceAccountControllerTest {
	
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void whenCreateSuccess() throws Exception {
		String content = "{\"appId\": \"12345678\", \"secret\": \"7890123456789\"}";
		
		String result = 
		
		mockMvc.perform(
				MockMvcRequestBuilders
					.post("/wx/account/")
					.contentType("application/json;charset=utf-8")
					.content(content)
					.accept("application/json;charset=utf-8"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn()
		.getResponse().getContentAsString();
		
		System.out.println(result);
	}
}

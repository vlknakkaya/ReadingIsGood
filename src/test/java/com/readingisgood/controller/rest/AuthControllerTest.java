package com.readingisgood.controller.rest;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingisgood.model.dto.AuthRequestDTO;
import com.readingisgood.util.ErrorCodes;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Tests AuthController.createToken(AuthRequestDTO) with true credentials
	 * 
	 * @throws Exception
	 */
	@Test
	void testCreateTokenWithTrueCredentials() throws Exception {
		this.mockMvc.perform(post("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(new AuthRequestDTO("user1", "1111"))))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
				.andExpect(content().string(not(IsEmptyString.emptyOrNullString())));
	}

	/**
	 * Tests AuthController.createToken(AuthRequestDTO) with false credentials
	 * 
	 * @throws Exception
	 */
	@Test
	void testCreateTokenWithFalseCredentials() throws Exception {
		this.mockMvc.perform(post("/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(new AuthRequestDTO("user1", "2222"))))
				.andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(ErrorCodes.BAD_CREDENTIALS));
	}
	
}

package com.readingisgood.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.model.dto.AuthRequestDTO;
import com.readingisgood.model.validator.AuthRequestDTOValidator;
import com.readingisgood.service.DummyUserDetailsService;
import com.readingisgood.util.JWTUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private DummyUserDetailsService userDetailsService;
	@Autowired
	private AuthRequestDTOValidator authRequestDTOValidator;

	@InitBinder(value = "authRequestDTO")
	void initAuthRequestDTO(WebDataBinder binder) {
		binder.setValidator(authRequestDTOValidator);
	}
	
	@PostMapping
	public String createToken(@RequestBody @Valid AuthRequestDTO authRequestDTO) throws BadCredentialsException {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDTO.getUsername());

		return jwtUtil.generateToken(userDetails);
	}

}

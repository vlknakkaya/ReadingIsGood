package com.readingisgood.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class DummyUserService {

	private Map<String, User> users = new HashMap<>();

	@PostConstruct
	public void initialize() {
		users.put("user1", new User("user1", "1111", new ArrayList<>()));
		users.put("user2", new User("user2", "2222", new ArrayList<>()));
		users.put("user3", new User("user3", "3333", new ArrayList<>()));
	}

	public User findByUsername(String username) {
		return users.get(username);
	}

}

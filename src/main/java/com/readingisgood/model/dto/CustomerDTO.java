package com.readingisgood.model.dto;

public class CustomerDTO {

	private long id;
	private String email;
	private String name;
	private String address;

	public CustomerDTO() {
		super();
	}

	public CustomerDTO(long id, String email, String name, String address) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}

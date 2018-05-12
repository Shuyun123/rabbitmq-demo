package net.anumbrella.rabbitmq.entity;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * uid
	 */
	private static final long serialVersionUID = -4076516019527805945L;
	
	private String name;
	
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
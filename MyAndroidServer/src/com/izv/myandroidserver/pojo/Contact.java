package com.izv.myandroidserver.pojo;

public class Contact {

	private String id, name, number;

	public Contact() {

	}

	public Contact(String id, String name, String number) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Contacto [id=" + id + ", name=" + name + ", number=" + number
				+ "]";
	}

}

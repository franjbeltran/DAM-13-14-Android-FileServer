package com.izv.myandroidserver.pojo;

import MiFecha.MiFecha;

public class SMS {

	private String number, message;
	private MiFecha miFecha;

	public SMS() {

	}

	public SMS(String number, String message, MiFecha miFecha) {
		super();
		this.number = number;
		this.message = message;
		this.miFecha = miFecha;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MiFecha getDate() {
		return miFecha;
	}

	public void setMiFecha(MiFecha miFecha) {
		this.miFecha = miFecha;
	}

	@Override
	public String toString() {
		return "SMS [number=" + number + ", message=" + message + ", date="
				+ miFecha + "]";
	}

}

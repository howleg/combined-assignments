package com.cooksys.serialization.assignment.model;

public class Instructor {
	private Contact contact;

	public Instructor() {

		// this.contact = null
	}

	public Instructor(Contact contact) {
		this.contact = contact;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
}

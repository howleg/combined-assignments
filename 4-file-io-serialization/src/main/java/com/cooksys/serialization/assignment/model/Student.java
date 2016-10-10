package com.cooksys.serialization.assignment.model;

public class Student {
	private Contact contact;

	public Student() {
		// this.contact = null;
	}

	public Student(Contact contact) {
		this.contact = contact;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
}

package com.bluetrainsoftware.easyb.grails.test.service;

import com.bluetrainsoftware.easyb.grails.test.domain.Book

/**
 * this exists only for mocking purposes
 */
public class AuthorService {
	public String doSomething(int val) {
		return "burp";
	}
	
	public Book allBooksByMe(String who) {
		return null;
	}
	
	def listAllBooks() {
		return Book.list()
	}
}
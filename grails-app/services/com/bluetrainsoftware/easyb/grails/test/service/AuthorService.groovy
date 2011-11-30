package com.bluetrainsoftware.easyb.grails.test.service;

import com.bluetrainsoftware.easyb.grails.test.domain.Author
import com.bluetrainsoftware.easyb.grails.test.domain.Book

/**
 * this exists only for mocking purposes
 */
public class AuthorService {
	public String doSomething(int val) {
		return "burp";
	}
	
	def allBooksByMe(String who) {
		//Ugly, but who cares
		def books = []
		Book.list().each{book ->
			if(book.authors.find{author -> author.name == who}) books << book
		}
		return books
	}
	
	def listAllBooks() {
		return Book.list()
	}
}
package com.bluetrainsoftware.easyb.grails.test.controllers

import com.bluetrainsoftware.easyb.grails.test.domain.Author

public class AuthorController {
	def authorService
	def list() {
			[Author.list()]
	}
	
	def somethingThatRenders() {
		render(view: 'list', model: Author.list())
	}
	
	def somethingThatRedirects() {
		redirect(action: 'list')
	}
	def getAnAuthorByAParam() {
		[Author.get(params.id)]
	}
	
	def useAServiceCall() {
		authorService
	}
	
	def handleCommand(AuthorCommand authorCommand) {
		if (authorCommand.hasErrors()) {
			render "Bad"
		}
		else {
			render "Good"
		}
	}
}

class AuthorCommand {
	String name

	static constraints = {
		name(blank: false, minSize: 6)
	}
}

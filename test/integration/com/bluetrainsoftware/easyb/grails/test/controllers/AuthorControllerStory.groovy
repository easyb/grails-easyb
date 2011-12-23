package com.bluetrainsoftware.easyb.grails.test.controllers;

import com.bluetrainsoftware.easyb.grails.test.domain.*;

description """The standard semantics for Grails in integration test mode
is to create MockkHttpServletRequest/Response by resolving the controller name from the test class name. Despite
this you still have to instantiate a controller object yourself."""

scenario "Controller mocking setup based on class package and name", {
	def controller
    given "a mocked domain of authors", {
        new Author(name: 'Terry').save(flush: true)
		new Author(name: 'Ernest').save(flush: true)
    }
	and "a controller", {
		controller = new AuthorController()
	}
    when "when we make a request of the controller", {
        authors = controller.list()
    }
    then "the controller should return a list of authors", {
        authors.shouldNotBe null
        authors.size().shouldBe 1
        authors[0].size().shouldBe 2
        def titles = authors[0].collect {author -> author.name }
        ensure(titles) {
            contains("Terry")
            and
            contains("Ernest")
        }
    }
}
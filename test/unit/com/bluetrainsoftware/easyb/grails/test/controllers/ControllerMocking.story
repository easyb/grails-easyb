package com.bluetrainsoftware.easyb.grails.test.controllers;

import com.bluetrainsoftware.easyb.grails.test.domain.*

// this won't pick up the controller automatically, mock it and stick it in


scenario "Controller won't be automatically injected", {
    then "controller variable should be null", {
        ensureThrows(MissingPropertyException) {
            controller.shouldBe null
        }
    }
}

scenario "We should be able to mock the controller with mockController closure", {
	given "a mocked domain of authors", {
		mockDomain( Author, [new Author(name:'Terry'), new Author(name:'Ernest')])
	}
	and "a mocked controller", {
		mockController( AuthorController )
	}
	when "when we make a request of the controlleer", {
		authors = controller.list()		
	}
	then "the controller should return a list of authors", {
		authors.shouldNotBe null
		authors.size().shouldBe 1
		authors[0].size().shouldBe 2
		def titles = authors[0].collect { author -> author.name }
		ensure(titles) { 
            contains("Terry")
            and
            contains("Ernest") 
        }
	}
}
package com.bluetrainsoftware.easyb.grails.test.controllers

import com.bluetrainsoftware.easyb.grails.test.domain.Author

it "should allow us to use mocking", {
	mockController( AuthorController)
	controller.justAVoidAction()
}

it "will explode if a domain also needs to be mocked", {
	mockController( AuthorController)
	ensureThrows(MissingMethodException) {
		controller.list()
	}
}

it "will work if the domain is mocked", {
	mockDomain(Author)
	mockController( AuthorController)
	controller.list()
}

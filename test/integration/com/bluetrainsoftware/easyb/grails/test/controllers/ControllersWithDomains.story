package com.bluetrainsoftware.easyb.grails.test.controllers;

before "setup controller",{
	
	given "an AuthorController is setup to be used in the story", {
		controller 'com.bluetrainsoftware.easyb.grails.test.controllers.AuthorController'
	}
}

scenario "controller injected due to existance of controller closure", {
	AuthorController controller = new AuthorController()
	def authors
	given "a domain of authors", {
		new com.bluetrainsoftware.easyb.grails.test.domain.Author(name:'Terry').save(flush: true)
        new com.bluetrainsoftware.easyb.grails.test.domain.Author(name:'Ernest').save(flush: true)
	}
	when "when we make a request of the controller", {
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

scenario "making sure that the mocked render works", {
	AuthorController controller = new AuthorController()
	
	when "when we make a request of the controller", {
		controller.somethingThatRenders()
	}
	then "somekind of view should have been rendered", {
		controller.modelAndView.viewName.shouldBe '/author/list'
	}
}

scenario "making sure that the mocked redirect works", {
	AuthorController controller = new AuthorController()
	
	when "when we make a request of the controller", {
		controller.somethingThatRedirects()
	}
	then "we should have been redirected", {
		controller.response.getRedirectedUrl().shouldBe '/author/list'
	}
}

scenario "making sure that the params work", {
	AuthorController controller = new AuthorController()
	def author, domain
	given "an author", {
		domain = new com.bluetrainsoftware.easyb.grails.test.domain.Author(name:'Terry').save(flush: true)
	}
	when "when we make a request of the controller with some params", {
		controller.params.id = domain.id
		author = controller.getAnAuthorByAParam().first()
	}
	then "we should have an author", {
		author.shouldNotBe null
		author.name.shouldBe 'Terry'
	}
}

scenario "reset controller variable to non existing controller", {
	given "we use a non existing controller", {
		controller 'IsNotAController'
	}
	then "it blows up", {
		ensureThrows(org.codehaus.groovy.runtime.InvokerInvocationException) {
			controller.list()
		}
	}
}
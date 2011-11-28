package com.bluetrainsoftware.easyb.grails.test.controllers;

import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletRequest
import org.codehaus.groovy.grails.plugins.testing.GrailsMockHttpServletResponse
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.mock.web.MockHttpSession

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

scenario "grails test framework convenience methods are accessible", {
	given "a mocked controller",{
		mockController AuthorController
		mockDomain Author
	}
	then "a request object exists", {
		ensure(request) {
			isNotNull
		}
		and
		ensure(request instanceof GrailsMockHttpServletRequest) {
			isTrue
		}
	}
	then "a response object exists", {
		ensure(response) {
			isNotNull
		}
		and
		ensure(response instanceof GrailsMockHttpServletResponse) {
			isTrue
		}
	}
	then "a webRequest object exists", {
		ensure(webRequest) {
			isNotNull
		}
		and
		ensure(webRequest instanceof GrailsWebRequest) {
			isTrue
		}
	}
	then "a params object exists", {
		ensure(params) {
			isNotNull
		}
		and
		ensure(params instanceof GrailsParameterMap) {
			isTrue
		}
	}
	then "a session object exists", {
		ensure(session) {
			isNotNull
		}
		and
		ensure(session instanceof MockHttpSession) {
			isTrue
		}
	}
	then "a model object exists", {
		ensure(model) {
			isNotNull
		}
	}
	then "a flash object exists", {
		ensure(flash) {
			isNotNull
		}
	}
	then "a views object exists", {
		ensure(views) {
			isNotNull
		}
	}
	then "a view object can't exist because it initializes as null", {
		ensureThrows(MissingPropertyException){
			view
		}
	}
}

scenario "We should be able to mock a command object", {
	given "a mocked domain of authors", {
		mockDomain( Author, [new Author(name:'Terry'), new Author(name:'Ernest')])
	}
	and "a mocked controller", {
		mockController( AuthorController )
	}
	when "when we make a request of the controller", {
		def cmd = mockCommandObject(AuthorCommand)
		cmd.name = '' // doesn't allow blank names
		cmd.validate()
		controller.handleCommand(cmd)
	}
	then "the controller should respond", {
		response.text.shouldBe 'Bad'
	}
}
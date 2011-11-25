
import com.bluetrainsoftware.easyb.grails.test.controllers.AuthorController
import com.bluetrainsoftware.easyb.grails.test.domain.Author
description """
This story makes sure that it is possible to mock urlmappings
through the mixin UrlMappingsUnitTestMixin"""

scenario "an urlmappings class is mocked",{
	def urlMapping 
	given "an mocked urlmapping",{
		urlMapping = mockUrlMappings(UrlMappings)
	}
	then "the mocked url mapping exists", {
		ensure(urlMapping) {
			isNotNull
		}
	}
}

scenario "simulate mapping to a controller through a mocked urlmapping",{
	AuthorController authorController
	given "a mocked controller and mocked domain",{
		mockController(AuthorController)
		mockDomain(Author)
	}
	when "the mapping to the controller is simulated", {
		authorController = mapURI('/author/list')
	}
	then "the simulate controller exists", {
		ensure(authorController) {
			isNotNull
		}
	}
	then "the controller list method is callable", {
		authorController.list().shouldNotBe null
	}
}
import com.bluetrainsoftware.easyb.grails.test.controllers.AuthorController
import com.bluetrainsoftware.easyb.grails.test.domain.Author
description '''Filter should be possible to mock through the FilterUnitTestMixin'''

//This test has to reside in the default package due to that the CancellingFilter is in the default package.
scenario "We should be able to mock filters and testing them with withFilters", {
	given "a mocked controller", {
		mockController(AuthorController)
		mockDomain(Author)
	}
	and "mocked filters",{
		mockFilters CancellingFilters 
	}
	
	when "applying withFilters", {
		 withFilters(action:"create") {
			 controller.create()
		 }
	}
	
	then "the controller action should have been redirected", {
		response.redirectedUrl.shouldBe '/author/list'
	}
}
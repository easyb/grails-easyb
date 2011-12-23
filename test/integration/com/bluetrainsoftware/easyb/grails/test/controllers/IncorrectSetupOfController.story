package com.bluetrainsoftware.easyb.grails.test.controllers

import com.bluetrainsoftware.easyb.grails.test.domain.Author

scenario "setting up an controller inline won't allow naming a variable with the name controller",{
	AuthorController controller
	when "a controller is setup", {
		ensureThrows(NullPointerException) {
			controller 'com.bluetrainsoftware.easyb.grails.test.controllers.AuthorController'
			controller = new AuthorController()
		}
	}
}

//This is basically negating the Wibble.story and more inline with how a proper Grails integration test
//is setup. It resolves the controller from the test class, but it is necessary to instantiate the
//controller in any case
scenario "controller variable is not sufficient anymore, you have to instantiate the controller as well", {
	given "a controller", {
		controller 'com.bluetrainsoftware.easyb.grails.test.controllers.AuthorController'
	}
	when "we have an author", {
		new Author(name:'Terry').save(flush: true)
	}
	then "we should be able to ask the controller for one", {
		ensureThrows(MissingMethodException) {
			controller.list()
		}
	}
}
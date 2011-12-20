package com.bluetrainsoftware.easyb.grails.test.controllers
scenario "setting up an controller inline won't allow naming a variable with the name controller",{
	AuthorController controller
	when "a controller is setup", {
		ensureThrows(NullPointerException) {
			controller 'com.bluetrainsoftware.easyb.grails.test.controllers.AuthorController'
			controller = new AuthorController()
		}
	}
}
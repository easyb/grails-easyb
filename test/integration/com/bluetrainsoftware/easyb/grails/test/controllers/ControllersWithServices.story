package com.bluetrainsoftware.easyb.grails.test.controllers

shared_behavior "AuthorController setup",{
	given "an AuthorController is setup", {
		controller 'com.bluetrainsoftware.easyb.grails.test.controllers.AuthorController'
	}
}

scenario "making sure that services are callable through controllers",{
	AuthorController controller
	it_behaves_as "AuthorController setup"
	given "a controller", {
		controller = new AuthorController()
	}
	when "calling a method that delegates to a service ", {
		println controller.useAServiceCall()
	}
}

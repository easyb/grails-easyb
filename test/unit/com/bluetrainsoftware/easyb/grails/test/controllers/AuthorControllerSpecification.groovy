import com.bluetrainsoftware.easyb.grails.test.domain.Author



it "will have injected controller to based on class name", {
	mockDomain(Author)
	controller.list()
}

it "will have the injected controller for the remainder of spec", {
	ensure(controller) {
		isNotNull
	}
}

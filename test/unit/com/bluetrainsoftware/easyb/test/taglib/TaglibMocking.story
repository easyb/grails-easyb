package com.bluetrainsoftware.easyb.test.taglib

import grails.easyb.plugin.test.taglib.SimpleTagLib

import com.bluetrainsoftware.easyb.grails.test.domain.Author

description """
This story makes sure that it is possible to mock taglibs and uses the render method
that is available on the mixin GroovyPageUnitTestMixin"""


scenario "a taglib is mocked and verified by applying a template",{
	given "a mocked tagLib",{ mockTagLib(SimpleTagLib) }
	then "it should be possible to use the taglib through applyTemplate", {
		applyTemplate('<s:hello />').shouldBe 'Hello World'
		applyTemplate('<s:hello name="Fred" />').shouldBe 'Hello Fred'
	}
}

scenario "views and templates can be rendered",{

	given "", { mockDomain Author  }
	then "render something", {
		render(view: "/author/hello").trim().shouldBe 'Hello'
	}
}

scenario "built in GroovyPageUnitTestMixin asserts are not supported",{
	given "a mocked tagLib",{ mockTagLib(SimpleTagLib) }
	then "any attempt to use built in asserts throws MissingMethodException", {
		ensureThrows(MissingMethodException) { assertOutputEquals ('Hello World', '<s:hello />') }
		ensureThrows(MissingMethodException) { assertOutputMatches (/.*Fred.*/, '<s:hello name="Fred" />') }
	}
}
package com.bluetrainsoftware.easyb.grails.test.taglib
scenario "a taglib resolved through package and class name",{
	then "it should be possible to use the taglib through applyTemplate", {
		applyTemplate('<s:hello />').shouldBe 'Hello World'
		applyTemplate('<s:hello name="Fred" />').shouldBe 'Hello Fred'
	}
}
package com.bluetrainsoftware.easyb.grails.test.taglib

class SimpleTagLib {
	static namespace = 's'
	
		def hello = { attrs, body ->
			out << "Hello ${attrs.name ?: 'World'}"
		}
}

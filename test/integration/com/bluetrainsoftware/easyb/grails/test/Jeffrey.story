package com.bluetrainsoftware.easyb.grails.test

/**
 * introduced by Jeffrey C. Erikson
 */


//scenario "we should be able to inject services", {
//	when "requesting the author service bean", {
//		inject "authorService"
//	}
//	then "author service should be callable", {
//		authorService.doSomething(3).shouldBe "burp"
//	}
//}
//
//scenario "non existent beans should fail", {
//	when "we have a request for a non-existent bean", {
//		beanReq = { 
//			inject "wibble"
//		}
//	}
//	then "it should fail", {
//		ensureThrows(Exception) {
//			beanReq.call()
//		}
//	}
//}
package com.bluetrainsoftware.easyb.grails.test.controllers;

import com.bluetrainsoftware.easyb.grails.test.domain.*;

//scenario "Controller automatically injected based on class package and name", {
//    given "a mocked domain of authors", {
//        mockDomain(Author, [new Author(name: 'Terry'), new Author(name: 'Ernest')])
//    }
//    when "when we make a request of the controller", {
//        authors = controller.list()
//    }
//    then "the controller should return a list of authors", {
//        authors.shouldNotBe null
//        authors.size().shouldBe 1
//        authors[0].size().shouldBe 2
//        def titles = authors[0].collect {author -> author.name }
//        ensure(titles) {
//            contains("Terry")
//            and
//            contains("Ernest")
//        }
//    }
//}
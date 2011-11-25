package com.bluetrainsoftware.easyb.grails.test.domain;

/*
 * We are only testing mocking here, not actually testing the domain.
 */

//it "should be possible to mock the Author and test for constraints", {
//    Author author = new Author(name: 'Mr Ed')
//    mockForConstraintsTests(Author, [author])
//    Author another = new Author()
//
//    another.validate().shouldBe false
//
//    another.errors.name.shouldBe "nullable"
//}
//
//it "must let us mock a valid author", {
//    mockForConstraintsTests(Author, [])
//    Author a = new Author(name: 'Mr Ed')
//    a.validate().shouldBe true
//}
//
//it "must blow up if mocking not enavled", {
//    Author a = new Author(name: 'Me')
//    ensureThrows(MissingMethodException) {
//        a.validate()
//    }
//}
//
//it "must let us mock logging", {
//    mockLogging(Author, true)
//    Author a = new Author(name: 'You')
//    a.fakeLogging() // should be ok
//}
//
//it "must explode without mock logging", {
//    Author a = new Author(name: 'You')
//    ensureThrows(MissingPropertyException) {
//        a.fakeLogging() // should be explosions
//    }
//}
//
//it "must allow mocking of domains", {
//    Author you = new Author(name: 'You')
//    Author me = new Author(name: 'Me')
//    mockDomain(Author, [you, me])
//    Author.findAll().size().shouldBe 2
//    Author.findAllByName('Me').size().shouldBe 1
//}
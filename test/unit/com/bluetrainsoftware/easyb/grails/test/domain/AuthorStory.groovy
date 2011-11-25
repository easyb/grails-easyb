package com.bluetrainsoftware.easyb.grails.test.domain;

import com.bluetrainsoftware.easyb.grails.test.service.AuthorService

//scenario "Mocking out the author service", {
//    given "a mocked domain", {
//        book = new Book(title: "Sharks")
//        me = new Author(name: "Me")
//        you = new Author(name: "you")
//
//        mockDomain(Author, [me, you])
//        mockDomain(Book, [book])
//
//        book.addToAuthors(me)
//        book.addToAuthors(you)
//    }
//    and "we a mock author service", {
//        authorServiceMock = mockFor(AuthorService)
//        authorServiceMock.demand.allBooksByMe() {String byWho ->
//            return book
//        }
//    }
//    when "we mock the author service", {
//        authorService = authorServiceMock.createMock()
//    }
//    and "ask for the books", {
//        requestedBook = authorService.allBooksByMe('me')
//    }
//    then "we should have the mocked book", {
//        requestedBook.title.shouldBe "Sharks"
//    }
//    and "the demand should have been called on the mock", {
//        authorServiceMock.verify()
//    }
//}
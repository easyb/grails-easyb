package com.bluetrainsoftware.easyb.grails.test.domain;

import com.bluetrainsoftware.easyb.grails.test.service.AuthorService

scenario "Mocking out the author service", {
    def authorService, requestedBooks
	given "a mocked domain", {
        def book = new Book(title: "Sharks")
        me = new Author(name: "Me")
        def you = new Author(name: "you")

        mockDomain(Author, [me, you])
        mockDomain(Book, [book])

        book.addToAuthors(me)
        book.addToAuthors(you)
    }
    and "we a mock author service", {
        authorService = mockService(AuthorService)
    }
    when "ask for the books", {
        requestedBooks = authorService.allBooksByMe('Me')
    }
    then "we should have the mocked book", {
        requestedBooks.first().title.shouldBe "Sharks"
    }
}
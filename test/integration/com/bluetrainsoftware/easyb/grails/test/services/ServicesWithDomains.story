package com.bluetrainsoftware.easyb.grails.test.services

import com.bluetrainsoftware.easyb.grails.test.domain.Author
import com.bluetrainsoftware.easyb.grails.test.domain.Book

transactional true

before "setup dependencies",{
	given "inject service",{
		inject 'authorService'
	}
}
scenario "a domain object should be retrieveable through a service",{
	given "a book with an author",{
		def book = new Book(title: 'aBook')
		book.addToAuthors(new Author(name: 'AnAuthor'))
		book.save(flush: true)
	}
	
	then "author service is called and the author is listed", {
		authorService.listAllBooks().size().shouldBe 1
	}
}

scenario "no scenario should depend on another scenario",{
	given "nothing is created",{
	}
	
	then "author service is called and no author is listed", {
		authorService.listAllBooks().size().shouldBe 0
	}
}
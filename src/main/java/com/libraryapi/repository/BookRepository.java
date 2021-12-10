package com.libraryapi.repository;

import com.libraryapi.collection.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {

    boolean existsByIsbn(String isbn);

}

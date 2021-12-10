package com.libraryapi.service;

import com.libraryapi.collection.Book;

import java.util.Optional;

public interface BookService {

    Book save(Book book);

    Optional<Book> getBydId(String id);
}

package com.libraryapi.service;

import com.libraryapi.collection.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book save(Book book);

    List<Book> getAll();

    Optional<Book> getById(String id);

    void delete(Book book);


}

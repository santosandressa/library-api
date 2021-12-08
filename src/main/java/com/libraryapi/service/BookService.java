package com.libraryapi.service;

import com.libraryapi.collection.Book;
import org.springframework.stereotype.Service;

@Service
public interface BookService {

    Book save(Book book);

}

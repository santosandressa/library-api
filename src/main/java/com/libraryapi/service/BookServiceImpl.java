package com.libraryapi.service;

import com.libraryapi.collection.Book;
import com.libraryapi.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {


    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        boolean isbnExists = bookRepository.existsByIsbn(book.getIsbn());

        if(isbnExists) {
            throw new RuntimeException("Já existe um livro com o isbn");
        }

        return bookRepository.save(book);
    }
}

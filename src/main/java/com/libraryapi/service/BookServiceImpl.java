package com.libraryapi.service;

import com.libraryapi.collection.Book;
import com.libraryapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;

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

    @Override
    public Optional<Book> getBydId(String id) {
        return Optional.empty();
    }
}

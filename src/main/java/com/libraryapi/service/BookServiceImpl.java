package com.libraryapi.service;

import com.libraryapi.collection.Book;
import com.libraryapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        boolean isbnExists = this.bookRepository.existsByIsbn(book.getIsbn());

        if(isbnExists) {
            throw new RuntimeException("Já existe um livro com o isbn");
        }

        return this.bookRepository.save(book);
    }

    @Override
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> getById(String id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Book update(Book book) {
        return this.bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        if(book == null || book.getId() == null){
            throw new RuntimeException("Book id cannot be null.");
        }

        this.bookRepository.delete(book);
    }


}
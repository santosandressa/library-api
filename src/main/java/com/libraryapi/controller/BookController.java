package com.libraryapi.controller;

import com.libraryapi.collection.Book;
import com.libraryapi.dto.BookDTO;
import com.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<BookDTO> create(@Valid @RequestBody BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        book = bookService.save(book);

        modelMapper.map(book, bookDTO);

        return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);
    }
}

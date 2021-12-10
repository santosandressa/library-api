package com.libraryapi.controller;

import com.libraryapi.collection.Book;
import com.libraryapi.dto.BookDTO;
import com.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/{id}")
    public BookDTO get(@PathVariable String id) {
        return bookService.getBydId(id).map(book -> modelMapper.map(book, BookDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    @PostMapping
    public ResponseEntity<BookDTO> create(@Valid @RequestBody BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        book = bookService.save(book);

        modelMapper.map(book, bookDTO);

        return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);
    }
}

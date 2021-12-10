package com.libraryapi.repository;

import com.libraryapi.collection.Book;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class BookRepositoryTest  {

    @Autowired
    BookRepository bookRepository;

    public Book createABook(){
        Book book = new Book();
        book.setTitle("The Lord of the Rings");
        book.setAuthor("J.R.R. Tolkien");
        book.setIsbn("123456789");
        return book;
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro com o isbn informado")
    public void findByIsbn_shouldReturnTrueWhenExists() {
        // cenario
        Book book = createABook();
        bookRepository.save(book);
        String isbn = "123456789";

        // acao
        boolean exists = bookRepository.existsByIsbn(isbn);

        // verificacao
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um livro com o isbn informado")
    public void findByIsbn_shouldReturnFalseWhenNotExists() {
        // cenario
        String isbn = "1";

        // acao
        boolean exists = bookRepository.existsByIsbn(isbn);

        // verificacao
        assertThat(exists).isFalse();
    }

}

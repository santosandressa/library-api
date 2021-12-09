package com.libraryapi.service;

import com.libraryapi.collection.Book;
import com.libraryapi.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService bookService;

    @MockBean
    BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        this.bookService = new BookServiceImpl(bookRepository);
    }

    private Book createBook(){
        Book book = new Book();
        book.setTitle("Java");
        book.setAuthor("Paulo Silveira");
        book.setIsbn("123");
        return book;
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void shouldSaveaBook() {
        Book book = createBook();
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(false);

        Book myBook = new Book();
        myBook.setId("1");
        myBook.setTitle("Java");
        myBook.setAuthor("Paulo Silveira");
        myBook.setIsbn("123");

        Mockito.when(bookRepository.save(book)).thenReturn(myBook);

        Book savedBook = bookService.save(book);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Java");
        assertThat(savedBook.getAuthor()).isEqualTo("Paulo Silveira");
        assertThat(savedBook.getIsbn()).isEqualTo("123");
    }

    @Test
    @DisplayName("Deve lançar erro ao salvar um livro com isbn duplicado")
    public void shouldNotSaveaBookWithDuplicatedIsbn() {
        Book book = createBook();
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(true);

        Throwable exception = Assertions.catchThrowable( () -> bookService.save(book));

        assertThat(exception).isInstanceOf(RuntimeException.class).hasMessage("Já existe um livro com o isbn");

        Mockito.verify(bookRepository, Mockito.never()).save(book);
    }

}

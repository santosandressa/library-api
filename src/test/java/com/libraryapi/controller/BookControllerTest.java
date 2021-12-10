package com.libraryapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryapi.collection.Book;
import com.libraryapi.dto.BookDTO;
import com.libraryapi.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerTest {

    final static String BOOK_API = "/api/books";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    private BookDTO createNewBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle("Lord of the Rings");
        bookDTO.setAuthor("J.R.R. Tolkien");
        bookDTO.setIsbn("123456789");
        return bookDTO;
    }

    @Test
    @DisplayName("Deve criar um livro com sucesso")
    public void shouldCreateABook() throws Exception {

        BookDTO bookDTO = createNewBook();

        Book savedBook = new Book();
        savedBook.setId("1");
        savedBook.setTitle("Lord of the Rings");
        savedBook.setAuthor("J.R.R. Tolkien");
        savedBook.setIsbn("123456789");

        BDDMockito.given(bookService.save(Mockito.any(Book.class))).willReturn(savedBook);

        String json = new ObjectMapper().writeValueAsString(bookDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value(bookDTO.getTitle()))
                .andExpect(jsonPath("author").value(bookDTO.getAuthor()))
                .andExpect(jsonPath("isbn").value(bookDTO.getIsbn()));
    }


    @Test
    @DisplayName("Deve lançar erro quando não ter dados suficientes para criar um livro")
    public void shouldFailWhenNoData() throws Exception {

        String json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("fields", Matchers.hasSize(3)));
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn já existente")
    public void shouldNotCreateBookWithDuplicateIsbn() throws Exception {
        BookDTO dto = createNewBook();
        String json = new ObjectMapper().writeValueAsString(dto);
        String message = "Já existe um livro com o isbn";
        BDDMockito.given(bookService.save(Mockito.any(Book.class))).willThrow(new RuntimeException(message));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(message));
    }

    @Test
    @DisplayName("Deve obter informações de um livro")
    public void shouldGetBookDetails() throws Exception {

        String id = "1";
        Book book = new Book();
        book.setId(id);
        book.setTitle(createNewBook().getTitle());
        book.setAuthor(createNewBook().getAuthor());
        book.setIsbn(createNewBook().getIsbn());

        BDDMockito.given(bookService.getBydId(id)).willReturn(Optional.of(book));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(BOOK_API.concat("/"+id))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("title").value(book.getTitle()))
                .andExpect(jsonPath("author").value(book.getAuthor()))
                .andExpect(jsonPath("isbn").value(book.getIsbn()));
    }

    @Test
    @DisplayName("Deve retornar resource not found quando não encontrar um livro")
    public void bookNotFound() throws Exception {
        BDDMockito.given(bookService.getBydId(Mockito.anyString())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(BOOK_API.concat("/"+"2"))
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}
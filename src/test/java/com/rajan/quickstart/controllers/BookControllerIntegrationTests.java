package com.rajan.quickstart.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajan.quickstart.TestDataUtil;
import com.rajan.quickstart.domain.dto.BookDto;
import com.rajan.quickstart.domain.entities.BookEntity;
import com.rajan.quickstart.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private final BookService bookService;
    private final ObjectMapper objectMapper;
    private final MockMvc mockMvc;

    @Autowired
    public BookControllerIntegrationTests(BookService bookService, MockMvc mockMvc, ObjectMapper objectMapper) {
        this.bookService = bookService;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @Transactional
    public void testThatCreateBookReturnsHttpStatus201Created() throws Exception {
        BookDto testBookA = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(testBookA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/books/978-1-2345-6789-0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    @Transactional
    public void testThatCreateBookReturnsSavedBook() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/books/" + bookDto.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJson)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle()));
    }

    @Test
    @Transactional
    public void testThatListBooksReturnsHttp200Created() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk());
    }

//    @Test
//    @Transactional
//    public void testThatListBooksReturnsListOfBooks() throws Exception {
//        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
//        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/books")
//                                .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(
//                        MockMvcResultMatchers.jsonPath("$.[0].isbn").value("978-1-2345-6789-0"))
//                .andExpect(
//                        MockMvcResultMatchers.jsonPath("$.[0].title").value("It Starts with Us"));
//    }

    @Test
    @Transactional
    public void testThatGetBookReturnsHttp200OkWhenBookExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books/978-1-2345-6789-0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void testThatGetBookReturnsHttp404NotFoundWhenBookDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/books/978-1-2345s-343")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Transactional
    public void testThatGetBookReturnsBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/books/978-1-2345-6789-0")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.title").value("It Starts with Us"));
    }

    @Test
    @Transactional
    public void testThatFullUpdateBookReturnsHttp200OkWhenBookExists() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(savedBook.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void testThatFullUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setIsbn(savedBook.getIsbn());
        bookDto.setTitle("Winter is very very coming");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/books/" + savedBook.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJson)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.isbn").value("978-1-2345-6789-0"))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.title").value("Winter is very very coming"));
    }

    @Test
    @Transactional
    public void testThatPartialUpdateBookReturnsHttp200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setTitle("Winter is very very coming");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void testThatPartialUpdateBookReturnsHttpUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto bookDto = TestDataUtil.createTestBookDtoA(null);
        bookDto.setTitle("Winter is very very coming");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/books/" + savedBook.getIsbn())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJson)
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.isbn").value(savedBook.getIsbn()))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.title").value("Winter is very very coming"));
    }

    @Test
    @Transactional
    public void testThatDeleteBookReturnsHttp204() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        BookEntity savedBook = bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Transactional
    public void testThatDeleteBookReturnsHttp404WhenBookNotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/books/8913-381-481-1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

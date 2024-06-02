package com.rajan.quickstart.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajan.quickstart.TestDataUtil;
import com.rajan.quickstart.domain.dto.AuthorDto;
import com.rajan.quickstart.domain.entities.AuthorEntity;
import com.rajan.quickstart.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AuthorControllerIntegrationTests {

    private static final Logger log = LoggerFactory.getLogger(AuthorControllerIntegrationTests.class);

    @Autowired
    private AuthorService authorService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @Transactional
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorDtoJson)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Collen Hoover"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(34));
    }

    @Test
    @Transactional
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
        authorService.save(testAuthorA);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/authors")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Collen Hoover"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].age").value(34));
    }

//    @Test
//    @Transactional
//    public void testThatGetAuthorReturnsHttp200OkWhenAuthorExists() throws Exception {
//        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorA();
//        authorService.save(testAuthorA);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/api/authors/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isOk());
//    }

    @Test
    @Transactional
    public void testThatGetAuthorReturnsHttp404NotFoundWhenAuthorDoesNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/authors/901")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

//    @Test
//    @Transactional
//    public void testThatGetAuthorReturnsAuthor() throws Exception {
//        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
//        AuthorEntity savedAuthor = authorService.save(authorEntity);
//
//        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
//        String authorJson = objectMapper.writeValueAsString(authorDto);
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/authors/1")
//                                .contentType(MediaType.APPLICATION_JSON)
//                ).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Collen Hoover"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(34));
//    }

    @Test
    @Transactional
    public void testThatFullUpdateAuthorReturnsHttp200OkWhenAuthorExists() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void testThatFullUpdateAuthorReturnsHttp404NotFoundWhenAuthorDoesNotExists() throws Exception {
        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/authors/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Transactional
    public void testThatPartialUpdateAuthorReturnsHttp200OkWhenAuthorExists() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("Updated");
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    public void testThatPartialUpdateAuthorReturnsHttpUpdatedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorEntity);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDtoA();
        authorDto.setName("Updated");
        String authorJson = objectMapper.writeValueAsString(authorDto);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/api/authors/" + savedAuthor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(authorJson)
                ).andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(authorDto.getAge()));
    }

    @Test
    @Transactional
    public void testThatDeleteAuthorReturnsHttp204() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        AuthorEntity savedAuthor = authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Transactional
    public void testThatDeleteAuthorReturnsHttp404WhenAuthorNotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/authors/9999")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

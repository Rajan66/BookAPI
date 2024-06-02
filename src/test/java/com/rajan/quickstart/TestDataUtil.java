package com.rajan.quickstart;

import com.rajan.quickstart.domain.dto.AuthorDto;
import com.rajan.quickstart.domain.dto.BookDto;
import com.rajan.quickstart.domain.entities.AuthorEntity;
import com.rajan.quickstart.domain.entities.BookEntity;

public final class TestDataUtil {

    private TestDataUtil() {

    }

    public static AuthorEntity createTestAuthorA(){
        return AuthorEntity.builder()
                .id(1L)
                .name("Collen Hoover")
                .age(34)
                .build();
    }

    public static AuthorEntity createTestAuthorB(){
        return AuthorEntity.builder()
                .id(2L)
                .name("Thomas Shelby")
                .age(32)
                .build();
    }

    public static AuthorEntity createTestAuthorC(){
        return AuthorEntity.builder()
                .id(3L)
                .name("Jesse James")
                .age(80)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA(){
        return AuthorDto.builder()
                .id(1L)
                .name("Collen Hoover")
                .age(34)
                .build();
    }


    public static AuthorDto createTestAuthorDtoB(){
        return AuthorDto.builder()
                .id(2L)
                .name("Thomas Shelby")
                .age(32)
                .build();
    }


    public static BookEntity createTestBookA(final AuthorEntity author){
        return BookEntity.builder()
                .isbn("978-1-2345-6789-0")
                .title("It Starts with Us")
                .author(author)
                .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity author){
        return BookEntity.builder()
                .isbn("413-5-9578-2835-1")
                .title("Rich Gang Poor Gang")
                .author(author)
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity author){
        return BookEntity.builder()
                .isbn("529-7-7522-9183-5")
                .title("Mission Istanbul")
                .author(author)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto author){
        return BookDto.builder()
                .isbn("978-1-2345-6789-0")
                .title("It Starts with Us")
                .author(author)
                .build();
    }

    public static BookDto createTestBookDtoC(final AuthorDto author){
        return BookDto.builder()
                .isbn("529-7-7522-9183-5")
                .title("Mission Istanbul")
                .author(author)
                .build();
    }
}

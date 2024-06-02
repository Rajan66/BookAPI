package com.rajan.quickstart.repositories;

import com.rajan.quickstart.TestDataUtil;
import com.rajan.quickstart.domain.entities.AuthorEntity;
import com.rajan.quickstart.domain.entities.BookEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = QuickstartApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {
    @Autowired
    private BookRepository underTest;

//    @Test
//    @Transactional
//    public void testThatAuthorCanBeCreatedAndRecalled(){
//        AuthorEntity author = TestDataUtil.createTestAuthorA();
//        BookEntity bookEntity = TestDataUtil.createTestBookA(author);
//        underTest.save(bookEntity);
//        Optional<BookEntity> result = underTest.findById(bookEntity.getIsbn());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(bookEntity);
//    }

//    @Test
//    @Transactional
//    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
//        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
//        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
//        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
//
//        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorA);
//        BookEntity bookEntityB = TestDataUtil.createTestBookB(authorB);
//        BookEntity bookEntityC = TestDataUtil.createTestBookC(authorC);
//
//        underTest.save(bookEntityA);
//        underTest.save(bookEntityB);
//        underTest.save(bookEntityC);
//
//        Iterable<BookEntity> result = underTest.findAll();
//        Assertions.assertThat(result).hasSize(3).containsExactly(bookEntityA, bookEntityB, bookEntityC);
//    }

//    @Test
//    @Transactional
//    public void testThatAuthorCanBeUpdated() {
//        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
//        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorA);
//        underTest.save(bookEntityA);
//        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
//        bookEntityA.setAuthor(authorC);
//        underTest.save(bookEntityA);
//        Optional<BookEntity> result = underTest.findById(bookEntityA.getIsbn());
//        Assertions.assertThat(result).isPresent();
//        Assertions.assertThat(result.get()).isEqualTo(bookEntityA);
//    }

}

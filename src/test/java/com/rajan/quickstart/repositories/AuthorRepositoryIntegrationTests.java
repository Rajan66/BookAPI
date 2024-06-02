package com.rajan.quickstart.repositories;

import com.rajan.quickstart.BooksApiApplication;
import com.rajan.quickstart.TestDataUtil;
import com.rajan.quickstart.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BooksApiApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {
    @Autowired
    private AuthorRepository underTest;

//    @Test
//    @Transactional
//    public void testThatAuthorCanBeCreatedAndRecalled() {
//        AuthorEntity author = TestDataUtil.createTestAuthorA();
//        underTest.save(author);
//        Optional<AuthorEntity> result = underTest.findById(author.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(author);
//    }
//
//    @Test
//    @Transactional
//    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
//        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
//        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
//        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
//        underTest.save(authorA);
//        underTest.save(authorB);
//        underTest.save(authorC);
//        Iterable<AuthorEntity> result = underTest.findAll();
//        assertThat(result).hasSize(3).containsExactly(authorA, authorB, authorC);
//    }
//
//    @Test
//    @Transactional
//    public void testThatAuthorCanBeUpdated() {
//        AuthorEntity author = TestDataUtil.createTestAuthorB();
//        underTest.save(author);
//        author.setName("James Mcgill");
//        underTest.save(author);
//        Optional<AuthorEntity> result = underTest.findById(author.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(author);
//    }

    @Test
    @Transactional
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        underTest.save(author);
        underTest.deleteById(author.getId());
        Optional<AuthorEntity> result = underTest.findById(author.getId());
        assertThat(result).isEmpty();
    }

//    @Test
//    public void testThatGetAuthorsWithAgeLessThan() {
//        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
//        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
//        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
//        underTest.save(authorA);
//        underTest.save(authorB);
//        underTest.save(authorC);
//        Iterable<AuthorEntity> result = underTest.ageLessThan(50);
//        assertThat(result).containsExactly(authorA, authorB);
//    }
//
//    @Test
//    public void testThatGetAuthorsWithAgeGreaterThan() {
//        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
//        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
//        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
//        underTest.save(authorA);
//        underTest.save(authorB);
//        underTest.save(authorC);
//        // shit method name on purpose so that spring cannot automate the method.
//        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(50);
//        assertThat(result).containsExactly(authorC);
//    }
}

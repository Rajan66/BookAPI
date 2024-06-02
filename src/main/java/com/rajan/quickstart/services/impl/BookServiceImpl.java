package com.rajan.quickstart.services.impl;

import com.rajan.quickstart.domain.entities.BookEntity;
import com.rajan.quickstart.repositories.BookRepository;
import com.rajan.quickstart.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookEntity createUpdateBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn); // just making sure the isbn is the one that's in the url
        return bookRepository.save(bookEntity);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(book.getTitle()).ifPresent(existingBook::setTitle);
            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book doesn't exist."));
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(
                        bookRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}

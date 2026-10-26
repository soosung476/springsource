package com.example.book.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookRequestDto;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper mapper;
    // CRUD 호출

    public Long create(BookRequestDto dto) {
        // dto => entity (ModelMapper)
        // Book book = mapper.map(dto, Book.class);
        // bookRepository.save(book)
        return bookRepository.save(mapper.map(dto, Book.class)).getId();
    }

    public BookRequestDto readTitle(String title) {
        Book book = bookRepository.findByTitle(title).orElseThrow();
        // entity => dto
        return mapper.map(book, BookRequestDto.class);
    }

    public BookRequestDto readIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        // entity => dto
        return mapper.map(book, BookRequestDto.class);
    }

    public BookRequestDto readId(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();

        // entity => dto
        return mapper.map(book, BookRequestDto.class);

    }

    public List<BookRequestDto> readAll() {
        List<Book> books = bookRepository.findAll();

        // entity => dto
        return books.stream().map(book -> mapper.map(book, BookRequestDto.class)).collect(Collectors.toList());

    }

    public void update(BookRequestDto dto) {
        Book book = bookRepository.findById(dto.getId()).orElseThrow();
        book.changePrice(dto.getPrice());
        bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}

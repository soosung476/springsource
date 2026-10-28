package com.example.book.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDto;
import com.example.book.dto.PageRequestDto;
import com.example.book.dto.PageResultDto;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper mapper;
    // CRUD 호출

    public Long create(BookDto dto) {
        // dto => entity (ModelMapper)
        // Book book = mapper.map(dto, Book.class);
        // bookRepository.save(book)
        return bookRepository.save(mapper.map(dto, Book.class)).getId();
    }

    public PageResultDto<BookDto> list(PageRequestDto pageRequestDto) {
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(),
                Sort.by("id").descending());
        Page<Book> result = bookRepository
                .findAll(bookRepository.makePredicate(pageRequestDto.getType(), pageRequestDto.getKeyword()), pageable);

        // entity => dto 변경
        List<BookDto> dtoList = result.get().map(book -> mapper.map(book, BookDto.class)).collect(Collectors.toList());

        long totalCount = result.getTotalElements();

        return PageResultDto.<BookDto>withAll().dtoList(dtoList).totalCount(totalCount).pageRequestDto(pageRequestDto)
                .build();

    }

    public BookDto readTitle(String title) {
        Book book = bookRepository.findByTitle(title).orElseThrow();
        // entity => dto
        return mapper.map(book, BookDto.class);
    }

    public BookDto readIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn);
        // entity => dto
        return mapper.map(book, BookDto.class);
    }

    public BookDto readId(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();

        // entity => dto
        return mapper.map(book, BookDto.class);

    }

    public List<BookDto> readAll() {
        List<Book> books = bookRepository.findAll();

        // entity => dto
        return books.stream().map(book -> mapper.map(book, BookDto.class)).collect(Collectors.toList());

    }

    public void update(BookDto dto) {
        Book book = bookRepository.findById(dto.getId()).orElseThrow();
        book.changePrice(dto.getPrice());
        bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

}

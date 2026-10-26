package com.example.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    // where isbn = ''
    Book findByIsbn(String isbn);

    // where title = ''
    Optional<Book> findByTitle(String title);

    // List<Book> findByTitleContaining(String title);

    // List<Book> findByAuthor(String author);

    // // 도서 가격이 12000 이상 35000 이하
    // List<Book> findByPriceBetween(int startPrice, int endPrice);

    // // 도서명이 자바이거나, 작가이름이 자바인 도서 찾기
    // List<Book> findByTitleContainingOrAuthorContaining(String title, String
    // author);

    // // 도서명이 자바이고 정렬 아이디가 desc
    // List<Book> findByTitleContainingAndIdGraterThanOrderByIdDesc(String title,
    // Long id);

    // Entity
    @Query("SELECT b FROM Book b WHERE b.title like %?1%")
    List<Book> findByTitle1(String title);

    @Query(value = "SELECT * FROM Book b WHERE b.title like %?1%", nativeQuery = true)
    List<Book> findByTitle2(String title);

    @Query("SELECT b FROM Book b WHERE b.title like %?1% or b.author like %?2%")
    List<Book> findByTitleAuthor(String title, String author);
}

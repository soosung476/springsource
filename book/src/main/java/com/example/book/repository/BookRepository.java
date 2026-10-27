package com.example.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {

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

    // {}
    public default Predicate makePredicate(String type, String keyword) {
        QBook book = QBook.book;

        // title like '%자바%' or writer like '%자바%' or category like '%자바%'
        // where title like %자바% and id > 0
        // where author like %자바% and id > 0
        // where category like %자바% and id > 0
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(book.id.gt(0));

        if (type == null) {
            return builder;
        }
        if (type.equals("t")) {
            builder.and(book.title.contains(keyword));
        } else if (type.equals("w")) {
            builder.and(book.author.contains(keyword));
        } else if (type.equals("c")) {

        }
        return builder;
    }

}

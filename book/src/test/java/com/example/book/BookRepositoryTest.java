package com.example.book;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;
import com.example.book.repository.BookRepository;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void insert() {

        IntStream.rangeClosed(12, 62).forEach((i) -> {
            Book book = Book.builder()
                    .isbn("A101010" + i)
                    .title("파워자바" + i)
                    .author("홍길동" + i)
                    .price(36000)
                    .build();

            bookRepository.save(book);
        });

    }

    @Test
    public void read() {
        List<Book> books = bookRepository.findAll();
        System.out.println(books);
    };

    @Test
    public void read1() {
        Book book = bookRepository.findById(1L).get();
        System.out.println(book);

        bookRepository.findByIsbn("A1010101");
        System.out.println(book);
    };

    @Test
    public void read2() {
        // querydsl 적용 후
        QBook book = QBook.book;
        Pageable pageable = PageRequest.of(1, 20, Sort.by("id").descending());
        Page<Book> result = bookRepository.findAll(book.id.gt(0L), pageable);
        System.out.println(result);
        System.out.println(result.getTotalElements());
        System.out.println(result.getContent());
    };

    @Test
    public void read3() {
        // querydsl makePredicate 적용 후
        QBook book = QBook.book;
        Pageable pageable = PageRequest.of(2, 20, Sort.by("id").descending());
        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate("t", "파워"), pageable);
        System.out.println(result);
        System.out.println(result.getTotalElements());
        System.out.println(result.getContent().size());
        System.out.println(result.getNumber());
        System.out.println(result.getNumberOfElements());
        System.out.println(result.getSize());

    };

    @Test
    public void update() {
        Book book = bookRepository.findById(1L).get();
        book.changePrice(42000);
        bookRepository.save(book);
    };

    @Test
    public void delete() {
        bookRepository.deleteById(10L);

    };

}

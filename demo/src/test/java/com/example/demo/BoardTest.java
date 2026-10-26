package com.example.demo;

import java.util.List;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demo.entity.Board;
import com.example.demo.entity.QBoard;
import com.example.demo.repository.BoardRepository;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insert() {
        LongStream.rangeClosed(0, 100).forEach((b) -> {
            Board board = Board.builder().title("title " + b).content("content " + b).writer("writer " + b).build();
            boardRepository.save(board);
        });

    }

    @Test
    public void querydslTest() {
        QBoard board = QBoard.board;

        // title = title 1 조회
        System.out.println(boardRepository.findAll(board.title.eq("title 1")));

        // title like %title 1%
        System.out.println(boardRepository.findAll(board.title.contains("title 1")));

        // title like %title 1% and id >0 order by id desc
        System.out.println(boardRepository.findAll(board.title.contains("title 1")
                .and(board.id.gt(0L)), Sort.by("id").descending()));

        // page 나누기
        Pageable pageable = PageRequest.of(2, 20);
        Page<Board> result = boardRepository.findAll(board.id.gt(0L), pageable);

        System.out.println(result);
        System.out.println("page size " + result.getSize());
        System.out.println("page totalPage " + result.getTotalPages());
        System.out.println("page Elements " + result.getTotalElements());
        System.out.println("page Content" + result.getContent());
    }

    @Test
    public void aggreateTest() {
        List<Object[]> result = boardRepository.aggreate(); // 엔티티에 담기지 않는 것들은 오브젝트 배열에 담음.
        // System.out.println(result); // [[Ljava.lang.Object;@48f7f8cc]
        for (Object[] objects : result) {
            System.out.println(objects[0]); // 101
            System.out.println(objects[1]); // 51.0
            System.out.println(objects[2]); // 101
        }
    }

}

package com.example.board.Repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.MemberRepository;
import com.example.board.repository.ReplyRepository;

@Transactional
@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Commit
    @Test
    public void memberInsertTest() {

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password("1111")
                    .name("user" + i)
                    .build();
            memberRepository.save(member);
        });

    }

    @Commit
    @Test
    public void boardInsertTest() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            int no = (int) (Math.random() * 10) + 1;
            String email = "user" + no + "@gmail.com";

            Board board = Board.builder()
                    .title("board title" + i)
                    .content("board contents" + i)
                    .writer(Member.builder().email(email).build())
                    .build();

            boardRepository.save(board);
        });

    }

    @Commit
    @Test
    public void replyInsertTest() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            long no = (long) (Math.random() * 100) + 1;

            Reply reply = Reply.builder()
                    .text("reply" + i)
                    .replyer("replyer" + i)
                    .board(Board.builder().bno(no).build())
                    .build();

            replyRepository.save(reply);
        });

    }

    @Commit
    @Test
    public void boardUpdateTest() {

        Board board = boardRepository.findById(1L).get();

        board.changeTitle("change title");
    }

    @Commit
    @Test
    public void boardDeleteTest() {

        replyRepository.deleteByBno(3L);
        boardRepository.deleteById(3L);

    }

    @Transactional(readOnly = true)
    @Test
    public void getBoardWriter() {
        Board board = boardRepository.findById(92L).get();

        System.out.println(board);
        System.out.println(board.getWriter().getName());

        System.out.println(board.getReplies());
    }

    // 번호   제목    댓글개수   작성자   작성일
    // 92   title    (15)
}

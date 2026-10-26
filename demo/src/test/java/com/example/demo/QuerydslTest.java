package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.QStudent;
import com.example.demo.repository.StudentRepository;

@SpringBootTest
public class QuerydslTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void QuerydslTest() {
        QStudent student = QStudent.student;

        // 학생 이름이 홍길동인 학생 모두 찾기
        // where s.name = '홍길동'
        // @Query()
        studentRepository.findAll(student.name.eq("홍길동"));
        // %홍길동%
        studentRepository.findAll(student.name.contains("홍길동"));
        //
        studentRepository.findAll(student.name.contains("홍길동").and(student.id.gt(0L)));

        studentRepository.findAll(student.name.contains("홍길동").and(student.id.gt(0L)), Sort.by("id").descending());

    }

}

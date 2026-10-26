package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Child;
import com.example.demo.entity.Parent;
import com.example.demo.repository.ChildRepository;
import com.example.demo.repository.ParentRepository;

@Transactional
@SpringBootTest
public class ParentTest {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    @Commit
    @Test
    public void insert() {
        Parent parent = Parent.builder().name("parent1").build();
        parentRepository.save(parent);

        Child child1 = Child.builder().name("child1").parent(parent).build();
        childRepository.save(child1);

        Child child2 = Child.builder().name("child2").parent(parent).build();
        childRepository.save(child2);

    }

    // dirty check 안함
    @Transactional(readOnly = true)
    @Test
    public void read() {
        Parent p = parentRepository.findById(4L).get();
        System.out.println(p);
        p.getChildren().forEach(child -> System.out.println(child));
    }

    @Commit
    @Test
    public void update() {
        Parent p = parentRepository.findById(4L).get();
        p.changeName("change parent");
        // dirty check 해줌 => save() 명시적으로 부리지 않아도 됨.
    }

    @Commit
    @Test
    public void insertCascade() {
        Parent parent = Parent.builder().name("parent2").build();

        Child child1 = Child.builder().name("child1").parent(parent).build();
        Child child2 = Child.builder().name("child2").parent(parent).build();

        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        parentRepository.save(parent);
    }

    @Commit
    @Test
    public void insertCascade2() {
        Parent parent = Parent.builder().name("parent3").build();

        Child child1 = Child.builder().name("child1").build();
        Child child2 = Child.builder().name("child2").build();

        child1.setParent(parent);
        child2.setParent(parent);

        parentRepository.save(parent);
    }

    @Commit
    @Test
    public void delete() {

        childRepository.deleteById(4L);
        childRepository.deleteById(5L);
        parentRepository.deleteById(4L);
    }

    @Commit
    @Test
    public void deleteCascade() {
        parentRepository.deleteById(5L);
    }

    @Commit
    @Test
    public void deleteOrphanTest() {
        Parent p = parentRepository.findById(7L).get();
        p.getChildren().remove(0);

    }


    

}


package com.example.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "board_member")
// @Setter
@Getter
@Entity
public class Member extends BaseEntity {
    @Id
    private String email;

    private String password;

    private String name;

}

package ch1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString(exclude = "team")
@Setter
@Getter
public class TeamMember {
    private Long id;
    private String name;
    private Team team;
}

package pl.edu.pg.eti.kask.rpg.character.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity class describing single skill. Skill has its flavour text for name and description.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Skill implements Comparable<Skill> {

    /**
     * Name of the skill.
     */
    private String name;

    /**
     * Flavour text description for users.
     */
    private String description;

    @Override
    public int compareTo(Skill other) {
        int ret = name == null
                ? (other.name == null ? 0 : 1)
                : name.compareTo(other.name);
        if (ret == 0) {
            ret = description == null
                    ? (other.description == null ? 0 : 1)
                    : description.compareTo(other.description);
        }
        return ret;
    }

}

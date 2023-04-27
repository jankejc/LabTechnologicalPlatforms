package pl.edu.pg.eti.kask.rpg.character.repository;

import pl.edu.pg.eti.kask.rpg.character.entity.Skill;
import pl.edu.pg.eti.kask.rpg.repository.InMemoryRepository;

import java.util.Comparator;

/**
 * Repository for storing {@link Skill} entities.
 */
public class SkillRepository extends InMemoryRepository<Skill> {

    public SkillRepository() {
    }

    public SkillRepository(boolean sorted) {
        super(sorted);
    }

    public SkillRepository(Comparator<Skill> comparator) {
        super(comparator);
    }

}

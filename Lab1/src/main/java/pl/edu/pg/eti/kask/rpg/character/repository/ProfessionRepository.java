package pl.edu.pg.eti.kask.rpg.character.repository;

import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.repository.InMemoryRepository;

import java.util.Comparator;

/**
 * Repository for storing {@link Profession} entities.
 */
public class ProfessionRepository extends InMemoryRepository<Profession> {

    public ProfessionRepository() {
    }

    public ProfessionRepository(boolean sorted) {
        super(sorted);
    }

    public ProfessionRepository(Comparator<Profession> comparator) {
        super(comparator);
    }

}

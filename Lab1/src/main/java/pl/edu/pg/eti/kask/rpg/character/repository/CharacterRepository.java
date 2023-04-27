package pl.edu.pg.eti.kask.rpg.character.repository;

import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.repository.InMemoryRepository;

import java.util.Comparator;

/**
 * Repository for storing {@link Character} entities.
 */
public class CharacterRepository extends InMemoryRepository<Character> {

    public CharacterRepository() {
    }

    public CharacterRepository(boolean sorted) {
        super(sorted);
    }

    public CharacterRepository(Comparator<Character> comparator) {
        super(comparator);
    }

}

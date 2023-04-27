package pl.edu.pg.eti.kask.rpg.character.service;

import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.character.repository.CharacterRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Service for managing {@link pl.edu.pg.eti.kask.rpg.character.entity.Character} entities.
 */
public class CharacterService {

    /**
     * Repository for {@link pl.edu.pg.eti.kask.rpg.character.entity.Character}.
     */
    private final CharacterRepository repository;

    /**
     * @param repository repository for {@link pl.edu.pg.eti.kask.rpg.character.entity.Character}
     */
    public CharacterService(CharacterRepository repository) {
        this.repository = repository;
    }

    /**
     * @return all characters
     */
    public List<Character> findAllCharacters() {
        return repository.findAll();
    }

    /**
     * @param character character to be removed
     */
    public void delete(Character character) {
        repository.delete(character);
    }

    /**
     * @param character new character to be saved
     */
    public void create(Character character) {
        repository.add(character);
    }

    /**
     * Creates statistics how many characters are assigned to different classes.
     *
     * @return mapping of profession to number of characters
     */
    public SortedMap<Profession, Integer> createCharacterProfessionsStatistics() {
        SortedMap<Profession, Integer> map = new TreeMap<>();
        for (Character character : repository.findAll()) {
            if (map.containsKey(character.getProfession())) {
                map.put(character.getProfession(), map.get(character.getProfession()) + 1);
            } else {
                map.put(character.getProfession(), 1);
            }
        }
        return map;
    }

}

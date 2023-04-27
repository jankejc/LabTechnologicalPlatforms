package pl.edu.pg.eti.kask.rpg.character.service;

import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.character.repository.ProfessionRepository;

import java.util.List;

/**
 * Service for managing {@link pl.edu.pg.eti.kask.rpg.character.entity.Profession} entities.
 */
public class ProfessionService {

    /**
     * Repository for {@link Profession}.
     */
    private final ProfessionRepository repository;

    /**
     * @param repository repository for {@link Profession}
     */
    public ProfessionService(ProfessionRepository repository) {
        this.repository = repository;
    }

    /**
     * @return all professions
     */
    public List<Profession> findAllProfessions() {
        return repository.findAll();
    }

    /**
     * @param profession profession to be removed
     */
    public void delete(Profession profession) {
        repository.delete(profession);
    }

    /**
     * @param profession new profession to be saved
     */
    public void create(Profession profession) {
        repository.add(profession);
    }

}

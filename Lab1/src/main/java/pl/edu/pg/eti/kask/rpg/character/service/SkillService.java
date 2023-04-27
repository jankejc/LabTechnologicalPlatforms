package pl.edu.pg.eti.kask.rpg.character.service;

import pl.edu.pg.eti.kask.rpg.character.entity.Skill;
import pl.edu.pg.eti.kask.rpg.character.repository.SkillRepository;

import java.util.List;

/**
 * Service for managing {@link pl.edu.pg.eti.kask.rpg.character.entity.Skill} entities.
 */
public class SkillService {

    /**
     * Repository for {@link Skill}.
     */
    private final SkillRepository repository;

    /**
     * @param repository repository for {@link Skill}
     */
    public SkillService(SkillRepository repository) {
        this.repository = repository;
    }

    /**
     * @return all professions
     */
    public List<Skill> findAllSkills() {
        return repository.findAll();
    }

    /**
     * @param skill skill to be removed
     */
    public void delete(Skill skill) {
        repository.delete(skill);
    }

    /**
     * @param skill new skill to be saved
     */
    public void create(Skill skill) {
        repository.add(skill);
    }

}

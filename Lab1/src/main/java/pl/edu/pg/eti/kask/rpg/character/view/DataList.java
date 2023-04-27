package pl.edu.pg.eti.kask.rpg.character.view;

import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.character.service.CharacterService;
import pl.edu.pg.eti.kask.rpg.character.service.ProfessionService;
import pl.edu.pg.eti.kask.rpg.character.service.SkillService;
import pl.edu.pg.eti.kask.rpg.view.View;

import java.util.Map;

/**
 * View for presenting all data regarding characters and their professions.
 */
public class DataList implements View {

    /**
     * Service for {@link pl.edu.pg.eti.kask.rpg.character.entity.Character}.
     */
    private final CharacterService characterService;

    /**
     * Service for {@link pl.edu.pg.eti.kask.rpg.character.entity.Profession}.
     */
    private final ProfessionService professionService;

    /**
     * Service for {@link pl.edu.pg.eti.kask.rpg.character.entity.Skill}.
     */
    private final SkillService skillService;

    /**
     * @param characterService  service for {@link pl.edu.pg.eti.kask.rpg.character.entity.Character}
     * @param professionService service for {@link pl.edu.pg.eti.kask.rpg.character.entity.Profession}
     * @param skillService      service for {@link pl.edu.pg.eti.kask.rpg.character.entity.Skill}
     */
    public DataList(CharacterService characterService, ProfessionService professionService,
                    SkillService skillService) {
        this.characterService = characterService;
        this.professionService = professionService;
        this.skillService = skillService;
    }

    @Override
    public void display() {
        System.out.println("Characters:");
        for (Character character : characterService.findAllCharacters()) {
            System.out.println(character);
        }
        System.out.println();

        System.out.println("Professions:");
        for (Profession profession : professionService.findAllProfessions()) {
            System.out.println(profession);
        }
        System.out.println();

        System.out.println("Professions statistics:");
        for (Map.Entry<Profession, Integer> entry : characterService.createCharacterProfessionsStatistics().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println();
    }
}

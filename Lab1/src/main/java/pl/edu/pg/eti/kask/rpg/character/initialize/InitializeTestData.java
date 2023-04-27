package pl.edu.pg.eti.kask.rpg.character.initialize;

import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.character.service.CharacterService;
import pl.edu.pg.eti.kask.rpg.character.service.ProfessionService;
import pl.edu.pg.eti.kask.rpg.character.service.SkillService;

/**
 * Create test data and save it to repositories.
 */
public class InitializeTestData {

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
    public InitializeTestData(CharacterService characterService, ProfessionService professionService,
                              SkillService skillService) {
        this.characterService = characterService;
        this.professionService = professionService;
        this.skillService = skillService;
    }

    /**
     * Initialize test data. Should be called after dependency injection.
     */
    public void init() {
        Profession bard = Profession.builder().name("Bard").build();
        Profession cleric = Profession.builder().name("Cleric").build();
        Profession warrior = Profession.builder().name("Warrior").build();
        Profession rogue = Profession.builder().name("Rogue").build();
        Profession duplicate = Profession.builder().name("Rogue").build();//Should be ignored.

        professionService.create(bard);
        professionService.create(cleric);
        professionService.create(warrior);
        professionService.create(rogue);
        professionService.create(duplicate);

        Character calvian = Character.builder()
                .name("Calvian")
                .age(18)
                .background("A yong bard with some infernal roots.")
                .experience(0)
                .level(1)
                .profession(bard)
                .charisma(16)
                .constitution(12)
                .strength(8)
                .build();

        Character uhlbrecht = Character.builder()
                .name("Uhlbrecht")
                .age(37)
                .background("Quite experienced half-orc warrior.")
                .experience(0)
                .level(1)
                .profession(warrior)
                .charisma(8)
                .constitution(10)
                .strength(18)
                .build();

        Character eloise = Character.builder()
                .name("Eloise")
                .age(32)
                .background("Human cleric.")
                .experience(0)
                .level(1)
                .profession(cleric)
                .charisma(8)
                .constitution(12)
                .strength(14)
                .build();

        Character zereni = Character.builder()
                .name("Zereni")
                .age(20)
                .background("Half elf rogue.")
                .experience(0)
                .level(1)
                .profession(rogue)
                .charisma(14)
                .constitution(12)
                .strength(10)
                .build();

        characterService.create(calvian);
        characterService.create(uhlbrecht);
        characterService.create(eloise);
        characterService.create(zereni);
    }
}

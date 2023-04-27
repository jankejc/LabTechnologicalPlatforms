package pl.edu.pg.eti.kask.rpg;

import pl.edu.pg.eti.kask.rpg.character.entity.comparator.CharacterByProfessionComparator;
import pl.edu.pg.eti.kask.rpg.character.initialize.InitializeTestData;
import pl.edu.pg.eti.kask.rpg.character.repository.CharacterRepository;
import pl.edu.pg.eti.kask.rpg.character.repository.ProfessionRepository;
import pl.edu.pg.eti.kask.rpg.character.repository.SkillRepository;
import pl.edu.pg.eti.kask.rpg.character.service.CharacterService;
import pl.edu.pg.eti.kask.rpg.character.service.ProfessionService;
import pl.edu.pg.eti.kask.rpg.character.service.SkillService;
import pl.edu.pg.eti.kask.rpg.character.view.DataList;
import pl.edu.pg.eti.kask.rpg.view.View;

/**
 * Application main class. Contains application main entry point.
 */
public class SimpleRpgApplication {

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        boolean sorted = args.length >= 1 && Boolean.parseBoolean(args[0]);
        boolean alternativeCriteria = args.length == 2 && Boolean.parseBoolean(args[1]);
        SimpleRpgApplication application = new SimpleRpgApplication();

        ProfessionService professionService = application.createProfessionService(sorted);
        CharacterService characterService = application.createCharacterService(sorted, alternativeCriteria);
        SkillService skillService = application.createSkillService(sorted);

        InitializeTestData initializeTestData = new InitializeTestData(characterService, professionService, skillService);
        initializeTestData.init();

        View entryView = new DataList(characterService, professionService, skillService);
        entryView.display();
    }

    /**
     * @param sorted true if use sorted structure
     * @return configures service
     */
    private ProfessionService createProfessionService(boolean sorted) {
        return new ProfessionService(new ProfessionRepository(sorted));
    }

    /**
     * @param sorted      true if use sorted structure
     * @param alternative true if use alternative criteria
     * @return configures service
     */
    private CharacterService createCharacterService(boolean sorted, boolean alternative) {
        return (sorted && alternative)
                ? new CharacterService(new CharacterRepository(new CharacterByProfessionComparator()))
                : new CharacterService(new CharacterRepository(sorted));
    }

    /**
     * @param sorted true if use sorted structure
     * @return configures service
     */
    private SkillService createSkillService(boolean sorted) {
        return new SkillService(new SkillRepository(sorted));
    }

}

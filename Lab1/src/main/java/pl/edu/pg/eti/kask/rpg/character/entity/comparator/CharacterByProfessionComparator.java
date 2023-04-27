package pl.edu.pg.eti.kask.rpg.character.entity.comparator;

import pl.edu.pg.eti.kask.rpg.character.entity.Character;

import java.util.Comparator;

/**
 * Comparator for comparing {@link Character} entities firstly by {@link pl.edu.pg.eti.kask.rpg.character.entity.Profession}.
 */
public class CharacterByProfessionComparator implements Comparator<Character> {

    @Override
    public int compare(Character c1, Character c2) {
        int ret = c1.getProfession() == null
                ? (c2.getProfession() == null ? 0 : 1)
                : c1.getProfession().compareTo(c2.getProfession());
        if (ret == 0) {
            ret = c1.getName() == null
                    ? (c2.getName() == null ? 0 : 1)
                    : c1.getName().compareTo(c2.getName());
        }
        return ret;
    }

}

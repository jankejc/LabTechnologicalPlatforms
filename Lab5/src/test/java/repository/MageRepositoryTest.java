package repository;

import character.Mage;
import org.junit.Test;

import java.util.Optional;

public class MageRepositoryTest
{
    // Then:
    @Test(expected = IllegalArgumentException.class)
    public void delete_deleteNonexistedMage_throwIllegalArgumentException()
    {
        // Given:
        MageRepository repository = new MageRepository();
        repository.save(new Mage("Janko", 15));

        // When:
        repository.delete("Oknaj");
    }

    @Test
    public void find_tryToFindNonexistMage_emptyOptional()
    {
        // Given:
        MageRepository repository = new MageRepository();
        Optional<Mage> mageOpt;

        // When:
        mageOpt = repository.find("Janko");

        // Then:
        assert mageOpt.isEmpty() : "should be empty";
    }

    @Test
    public void find_tryToFindExistMage_optionalWithValue()
    {
        // Given:
        MageRepository repository = new MageRepository();
        repository.save(new Mage("Janko", 15)); //??????
        Optional<Mage> mageOpt;

        // When:
        mageOpt = repository.find("Janko");

        // Then:
        assert mageOpt.isPresent() : "should be value in optional";
    }

    // Then:
    @Test(expected = IllegalArgumentException.class)
    public void save_tryToSaveMageWithTheNameAlreadyExisted_throwIllegalArgumentException()
    {
        // Given:
        MageRepository repository = new MageRepository();
        repository.save(new Mage("Janko", 15)); //??????

        // When:
        repository.save(new Mage("Janko", 15));
    }
}

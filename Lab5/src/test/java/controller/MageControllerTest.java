package controller;

import character.Mage;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import repository.MageRepository;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;



public class MageControllerTest
{
    @Test
    public void save_alreadyExistingMageByName_returnStringBadRequest()
    {
        // Given:
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        doThrow(IllegalArgumentException.class).when(repository).save(any());

        // When:
        String returnValue = controller.save("Janek", 15);

        // Then:
        assertThat(returnValue).isEqualTo("bad request");
    }

    @Test
    public void save_correctParameter_returnStringDone()
    {
        // Given:
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        String name = "Janek";
        int level = 15;
        doNothing().when(repository).save(any());

        // When:
        String returnValue = controller.save(name, level);

        // Then:
        ArgumentCaptor<Mage> argumentCaptor = ArgumentCaptor.forClass(Mage.class);
        verify(repository, times(1)).save(argumentCaptor.capture());
        Mage mageFromCaptor = argumentCaptor.getValue();
        assertThat(mageFromCaptor.getName()).isEqualTo(name);
        assertThat(mageFromCaptor.getLevel()).isEqualTo(level);
        assertThat(returnValue).isEqualTo("done");
    }

    @Test
    public void find_existedMage_returnStringDone()
    {
        // Given:
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        Optional<Mage> mageOpt = Optional.of(new Mage("Janko", 15));
        when(repository.find("Janko")).thenReturn(mageOpt);

        // When:
        String returnValue = controller.find("Janko");

        // Then:
        assertThat(returnValue).isEqualTo(mageOpt.toString());
    }

    @Test
    public void find_nonexistedMage_returnStringNotFound()
    {
        // Given:
        MageRepository repository = Mockito.mock(MageRepository.class);
        MageController controller = new MageController(repository);
        when(repository.find(any())).thenReturn(Optional.empty());

        // When:
        String returnValue = controller.find("Janko");

        // Then:
        assertThat(returnValue).isEqualTo("not found");
    }

    @Test
    public void delete_existedMage_returnStringDone()
    {
        // Given:
        MageRepository repository = Mockito.mock(MageRepository.class);
        doNothing().when(repository).delete("Janek");
        MageController controller = new MageController(repository);

        // When:
        String returnValue = controller.delete("Janek");

        // Then:
        assertThat(returnValue).isEqualTo("done");
    }

    @Test
    public void delete_nonexistedMage_returnStringNotFound()
    {
        // Given:
        MageRepository repository = Mockito.mock(MageRepository.class);
        doThrow(IllegalArgumentException.class).when(repository).delete(any());
        MageController controller = new MageController(repository);

        // When:
        String returnValue = controller.delete("Janek");

        // Then:
        assertThat(returnValue).isEqualTo("not found");
    }
}

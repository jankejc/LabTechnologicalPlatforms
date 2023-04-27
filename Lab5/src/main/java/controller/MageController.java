package controller;

import character.Mage;
import lombok.AllArgsConstructor;
import repository.MageRepository;

import java.util.Optional;

@AllArgsConstructor
public class MageController
{
    private MageRepository repository;

    public String find(String name)
    {
        Optional<Mage> mageOpt = repository.find(name);
        if(mageOpt.isPresent())
            return mageOpt.toString();
        else
            return "not found";
    }

    public String delete(String name)
    {
        try {
            repository.delete(name);
            return "done";
        } catch (IllegalArgumentException e) {
            return "not found";
        }
    }

    public String save(String name, int level)
    {
        try {
            repository.save(new Mage(name, level));
            return "done";
        } catch (IllegalArgumentException e) {
            return "bad request";
        }
    }
}

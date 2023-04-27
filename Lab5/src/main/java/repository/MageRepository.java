package repository;

import character.Mage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

public class MageRepository
{
    private Collection<Mage> collection;

    public MageRepository()
    {
        collection = new ArrayList<>();
    }

    public Optional<Mage> find(String name)
    {
        Optional<Mage> mageOpt = Optional.empty();
        Iterator<Mage> i = collection.iterator();
        while(i.hasNext())
        {
            Mage mage = i.next();
            if(name.equals(mage.getName()))
            {
                mageOpt = Optional.of(mage);
                break;
            }
        }

        return mageOpt;
    }

    public void delete(String name) throws IllegalArgumentException
    {
        boolean mageExisted = false;
        Iterator<Mage> i = collection.iterator();
        while(i.hasNext())
        {
            Mage mage = i.next();
            if(name.equals(mage.getName()))
            {
                i.remove();
                mageExisted = true;
                break;
            }
        }

        if(!mageExisted)
            throw new IllegalArgumentException("There is no mage called \"" + name + "\"!");
    }

    public void save(Mage mage) throws IllegalArgumentException
    {
        boolean mageExist = false;
        if(collection.size() != 0)
        {
            Iterator<Mage> i = collection.iterator();
            while(i.hasNext())
            {
                Mage mageFromCollection = i.next();
                if(mageFromCollection.getName().equals(mage.getName()))
                {
                    mageExist = true;
                    break;
                }
            }
        }

        if(mageExist)
            throw new IllegalArgumentException("The same mage already exists in repository!");
        else
            collection.add(mage);
    }
}

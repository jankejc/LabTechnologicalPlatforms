/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab4_maven_pt;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Janek
 */
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Tower
{
    @Getter
    @Id
    private String name;
    
    @Getter
    @Setter
    private int height;
    
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tower")
    private List<Mage> mages;
    
    public Tower(String name, int height)
    {
        this.name = name;
        this.height = height;
    }
    
    public void addMageToTower(Mage mage)
    {
        mages.add(mage);
    }
    
    public String magesNamesToString()
    {
        String names = "";
        for(Mage mage : mages)
        {
            names += mage.getName();
            if(!mage.equals(mages.get(mages.size() - 1)))
            {
               names += ", "; 
            }
        }
        
        return names;
    }
    
    @Override
    public String toString()
    {
        return "Tower: " + name + ", height: " + height + " m, its mages: " + magesNamesToString();
    }
}

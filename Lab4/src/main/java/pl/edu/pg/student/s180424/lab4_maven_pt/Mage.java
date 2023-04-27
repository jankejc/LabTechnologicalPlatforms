/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab4_maven_pt;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Entity
public class Mage
{
    @Getter
    @Id
    private String name;
    
    @Getter
    @Setter
    private int level;
 
    @Getter
    @Setter
    @ManyToOne
    private Tower tower;
    
    public Mage(String name, int level, Tower tower)
    {
        this.name = name;
        this.level = level;
        this.tower = tower;
        assignToTower(tower);
    }
    
    public void assignToTower(Tower tower)
    {
        tower.addMageToTower(this);
    }
    
    @Override
    public String toString()
    {
        return "Mage: " + name + ", level: " + level + ", its tower: " + tower.getName();
    }
}

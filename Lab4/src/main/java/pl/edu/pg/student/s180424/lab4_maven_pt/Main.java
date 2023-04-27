/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab4_maven_pt;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Janek
 */
public class Main
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("labPu");
        Scanner scanner = new Scanner(System.in);
        
        boolean running = true;
        
        while (running)
        {
            System.out.println("command: ");
            String command = scanner.next();
            List<Tower> towers = getTowersList(emf.createEntityManager());
            List<Mage> mages = getMagesList(emf.createEntityManager());
            String towerName = "";
            String mageName = "";
            
            switch (command)
            {
                case "add_mage":
                    // Check if any tower exists.
                    if(towers.isEmpty())
                    {
                        System.out.println("There is no tower to add mage to.");
                        break;
                    }
                    
                    Tower leastMageTower = towers.get(0);
                    for(Tower tower : towers)
                    {
                        if(tower.getMages().size() < leastMageTower.getMages().size())
                        {
                            leastMageTower = tower;
                        }
                    }
                    
                    // Check if mage with the same name doesn't exist. 
                    if(!mages.isEmpty())
                    {
                        boolean mageExists = true;
                        while(mageExists)
                        {
                            System.out.println("mageName: ");
                            mageName = scanner.next();

                            for(Mage mage : mages)
                            {
                                if(mageName.equals(mage.getName()))
                                    break;

                                if(mage.equals(mages.get(mages.size() - 1)))
                                    mageExists = false;
                            }
                        }
                    }
                    else
                    {
                        System.out.println("mageName: ");
                        mageName = scanner.next();
                    }
                    
                    System.out.println("level: ");
                    int level = scanner.nextInt();
                    
                    // Add mage with unique name and level.
                    addMage(mageName, level, leastMageTower, emf.createEntityManager());
                    System.out.println("Mage added.");
                    break;
                    
                case "add_tower":
                    // Check if tower with the same name doesn't exist.
                    if(!towers.isEmpty())
                    {
                        boolean towerExists = true;
                        while(towerExists)
                        {
                            System.out.println("towerName: ");
                            towerName = scanner.next();

                            for(Tower tower : towers)
                            {
                                if(towerName.equals(tower.getName()))
                                    break;

                                if(tower.equals(towers.get(towers.size() - 1)))
                                    towerExists = false;
                            }
                        }
                    }
                    else
                    {
                        System.out.println("towerName: ");
                        towerName = scanner.next();
                    }

                    System.out.println("height in metres: ");
                    int height = scanner.nextInt();
                    
                    // Add tower with unique name and height.
                    addTower(towerName, height, emf.createEntityManager());
                    System.out.println("Tower added.");
                    break;
                    
                case "delete_mage":                   
                    // Check if mage exists.
                    boolean mageExists = false;
                    System.out.println("mageName: ");
                    mageName = scanner.next();

                    for(Mage mage : mages)
                    {
                        if(mageName.equals(mage.getName()))
                        {
                            mageExists = true;
                            break;
                        }                     
                    }
                    
                    if(mageExists)
                    {
                        if(deleteMageByName(mageName, emf.createEntityManager()) == 1)
                            System.out.println("Mage deleted.");
                        else
                            System.out.println("Something went wrong.");  
                    }
                    else
                    {
                        System.out.println("Mage does not exist.");
                    }
                    
                    break;
                    
                case "print_all_mages":
                    printAllMages(emf.createEntityManager());
                    break; 
                    
                case "print_all_towers_and_their_mages":
                    printAllTowersAndTheirMages(emf.createEntityManager());
                    break; 
                
                case "get_and_print_mages_with_level_higher_than":
                    System.out.println("level: ");
                    int higherThanLevel = scanner.nextInt();
                    getAndPrintMagesWithLevelHigherThan(higherThanLevel, emf.createEntityManager());
                    break; 
                    
                case "exit":
                    running = false;
                    break;
                    
                default:
                    System.out.println("add_mage, add_tower, print_all_mages, "
                            + "print_all_towers_and_their_mages, delete_mage, "
                            + "get_and_print_mages_with_level_higher_than, exit");
                    break;
            }
        }
        emf.close();
    }
    
    public static void getAndPrintMagesWithLevelHigherThan(int higherThanLevel, EntityManager em)
    {
        String queryString = "SELECT m FROM Mage m WHERE m.level > :higherThanLevel";
        Query query = em.createQuery(queryString);
        query.setParameter("higherThanLevel", higherThanLevel);
        List<Mage> mages = query.getResultList();
        em.close();
        
        System.out.println("Mages with level over " + higherThanLevel + ":");
        for(Mage mage : mages)
        {
            System.out.println(mage.toString());
        }
    }
    
    public static int deleteMageByName(String name, EntityManager em)
    {
        em.getTransaction().begin();
        String queryString = "DELETE FROM Mage m WHERE m.name LIKE :name";
        Query query = em.createQuery(queryString);
        query.setParameter("name", name);
        int changedRows = query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        return changedRows;
    }
    
    public static void printAllMages(EntityManager em)
    {
        List<Mage> mages = getMagesList(em);
        System.out.println("ALL MAGES:");
        for(Mage mage : mages)
        {
            System.out.println(mage.toString());
        }
        System.out.println("");
    }
    
    public static void printAllTowersAndTheirMages(EntityManager em)
    {
        List<Tower> towers = getTowersList(em);
        System.out.println("ALL TOWERS AND THEIR MAGES:");
        for(Tower tower : towers)
        {
            System.out.println(tower.toString());
        }
        System.out.println("");
    }
    
    public static List<Tower> getTowersList(EntityManager em)
    {
        String queryString = "SELECT t FROM Tower t";
        Query query = em.createQuery(queryString, Tower.class);
        List<Tower> towers = query.getResultList();
        em.close();
        return towers;
    }
    
    public static List<Mage> getMagesList(EntityManager em)
    {
        String queryString = "SELECT m FROM Mage m";
        Query query = em.createQuery(queryString, Mage.class);
        List<Mage> mages = query.getResultList();
        em.close();
        return mages;
    }
       
    public static void addMage(String name, int level, Tower leastMageTower, EntityManager em)
    {
        em.getTransaction().begin();
        em.persist(new Mage(name, level, leastMageTower));
        em.getTransaction().commit();
        em.close();
    }
    
    public static void addTower(String name, int height, EntityManager em)
    {
        em.getTransaction().begin();
        em.persist(new Tower(name, height));
        em.getTransaction().commit();
        em.close();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab2_maven_pt;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Janek
 */
public class ResultResource
{
    private Map<Integer, Boolean> listOfResults = new HashMap<>();
    
    public synchronized void printResults()
    {
         listOfResults.entrySet().forEach(entry->{
            if(entry.getValue() == false)
            {
                System.out.println(entry.getKey() + " is not prime.");
            }
            else
            {
                System.out.println(entry.getKey() + " is prime.");
            } 
         });
    }
    
    
    public void put(Integer value, Boolean result) throws InterruptedException 
    {
        synchronized (this) 
        {
            listOfResults.put(value, result);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab2_maven_pt;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Janek
 */
public class ExerciseResource
{
    private List<Integer> listOfExercises = new ArrayList<>();
    
    public synchronized Integer take() throws InterruptedException 
    {
        while(listOfExercises.size() == 0)
        {
            wait();
        }
        return listOfExercises.remove(0);
    }
    
    public synchronized void put(Integer value) 
    {
        listOfExercises.add(value);
        notifyAll();
    }
}

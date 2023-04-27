/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab2_maven_pt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Janek
 */
public class Main
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException
    {
        // Check parameters.
        if(args.length == 0)
        {
            System.out.print("WPISZ ILOSC WATKOW JAKO PARAMETR");
            return;
        }
        
        // Program variables.
        int numberOfThreads = Integer.parseInt(args[0]);
        ExerciseResource taskResource = new ExerciseResource();
        ResultResource resultResource = new ResultResource();
        List<Mathematician> mathematicians = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        Scanner myScanner = new Scanner(System.in);
        Boolean isExit = false;
        
        // Starting threads.
        for(int i = 0; i < numberOfThreads; i++)
        {
            Mathematician newMathematician = new Mathematician(taskResource, resultResource);
            mathematicians.add(newMathematician);
            Thread newThread = new Thread(newMathematician);
            threads.add(newThread);
            newThread.start();
        }
        
        // User input.
        while(isExit == false)
        {
            String userInput = myScanner.nextLine();
            if(userInput.equals("exit"))
            {
                isExit = true;
            } 
            else 
            {
                String exercisesString[] = userInput.split(",");
                for(String elem : exercisesString)
                {
                    taskResource.put(Integer.parseInt(elem));
                }
            }
        }
        
        for(Mathematician elem : mathematicians)
        {
            elem.setIsRunning(false);
        }
        
        for(Thread elem : threads)
        {
            elem.interrupt();
            elem.join();
        }
        
        resultResource.printResults();
        
        System.exit(0);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab2_maven_pt;

import static java.lang.Math.sqrt;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janek
 */
public class Mathematician implements Runnable
{
    private Boolean isRunning;
    private Boolean isPrime;
    private ExerciseResource taskResource;
    private ResultResource resultResource;

    public Mathematician(ExerciseResource taskResource, ResultResource resultResource)
    {
        isRunning = true;
        isPrime = true;
        this.taskResource = taskResource;
        this.resultResource = resultResource;
    }
    
    public void setIsRunning(Boolean option)
    {
        this.isRunning = option;
    }
    
    @Override
    public void run()
    {
        while(isRunning)
        {
            Integer task = -1;
            isPrime = true;
            try
            {
                task = taskResource.take();
            }
            catch (InterruptedException ex)
            {
                //Logger.getLogger(Mathematician.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(task != -1)
            {
                for (int i = 2; i <= sqrt(task); i++)
                {
                    if(task % i == 0)
                    {
                        isPrime = false;
                        break;
                    }
                }
                
                try
                {
                    resultResource.put(task, isPrime);
                }
                catch (InterruptedException ex)
                {
                    //Logger.getLogger(Mathematician.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}

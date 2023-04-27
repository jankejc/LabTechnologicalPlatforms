/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab3_maven_pt;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Janek
 */
public class WaitingSockets
{
    private List<Socket> listOfSockets = new ArrayList<>();
    private int takenNumber = 0;
    
    public synchronized Socket take() throws InterruptedException 
    {
        takenNumber += 1;
        return listOfSockets.remove(0);
    }
    
    public synchronized void imDone()
    {
        takenNumber -= 1;
        notifyAll();
    }
    
    public synchronized void put(Socket value) throws InterruptedException 
    {
        while(takenNumber == 4)
        {
            wait();
        }
        listOfSockets.add(value);
    }
}

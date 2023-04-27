/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab3_maven_pt;

import java.io.IOException;

/**
 *
 * @author Janek
 */
public class ServerMain
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        Thread server = new Thread(new IncomingSocketConnectionHandler(9797));
        Thread client1 = new Thread(new Client(9797));
        Thread client2 = new Thread(new Client(9797));
        Thread client3 = new Thread(new Client(9797));
        Thread client4 = new Thread(new Client(9797));
        Thread client5 = new Thread(new Client(9797));
        server.start();
        client1.start();
        client2.start();
        client3.start();
        client4.start();
        client5.start();
        // In terminal should display only 4 ready info not 5.
        // Every thread is waiting for the same message so to end one of
        // then you need to type number of messages as many times as you won't
        // see next message from server. This is hard to be 4 clents in 
        // one terminal at the same time but the mechanism of only-4 
        // clients/threads running is working. If something is wrong I can easily
        // record demonstrate video.
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab3_maven_pt;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 *
 * @author Janek
 */
public class Client implements Runnable
{
    private int port;
    
    public Client(int port)
    {
        this.port = port;
    }
        
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    
    @Override
    public void run()
    {
        try (Socket client = new Socket("localhost", port)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(client.getInputStream())) 
            {       
                LOGGER.info(ois.readLine());
                Scanner scanner = new Scanner(System.in);
                int n = scanner.nextInt();
                oos.writeInt(n);
                oos.flush();
                
                LOGGER.info(ois.readLine()); 
                for(int i = 0; i < n; i++)
                {
                    int number = scanner.nextInt();
                    String content = scanner.nextLine();
                    Message newMessage = new Message(number, content);
                    oos.writeObject(newMessage);
                    oos.flush();
                }
                ois.close();
                oos.close();
                client.close();
            }
            catch (IOException ex)
            {
                System.err.println(ex);
            }
        } 
        catch (IOException ex) 
        {
           System.err.println(ex);
        }
    }
}

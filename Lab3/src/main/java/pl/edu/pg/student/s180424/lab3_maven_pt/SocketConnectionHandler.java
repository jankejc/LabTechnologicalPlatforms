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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janek
 */
public class SocketConnectionHandler implements Runnable
{
    private Socket socket;
    private WaitingSockets waitingSockets;
    private static final Logger LOGGER = Logger.getLogger(SocketConnectionHandler.class.getName());

    public SocketConnectionHandler(Socket socket, WaitingSockets waitingSockets)
    {
        this.socket = socket;
        this.waitingSockets = waitingSockets;
    }

    @Override
    public void run()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            
            oos.writeChars("ready\n");
            oos.flush();
            
           //while(ois.available() == 0);
            
            int n = ois.readInt();
            //ois.readLine();
            LOGGER.info("Pobrano: " + n);
            oos.writeChars("ready for message\n");
            oos.flush();

            Message mess;
            for(int i = 0; i < n; i++)
            {
                mess = (Message)ois.readObject();
                mess.printMessage();
            } 
            ois.close();
            oos.close();
            socket.close();
            
            waitingSockets.imDone();
        }
        catch (IOException ex)
        {
            Logger.getLogger(SocketConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(SocketConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}

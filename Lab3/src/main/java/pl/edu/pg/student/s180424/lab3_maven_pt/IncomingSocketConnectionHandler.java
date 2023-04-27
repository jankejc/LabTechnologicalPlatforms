/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab3_maven_pt;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Janek
 */
public class IncomingSocketConnectionHandler implements Runnable
{
    
    private static final Logger LOGGER = Logger.getLogger(SocketConnectionHandler.class.getName());
    
    private ServerSocket server;
    private WaitingSockets waitingSockets;
    
    public IncomingSocketConnectionHandler(){}

    // Initializing socket for server in server thread.
    public IncomingSocketConnectionHandler(int port) throws IOException
    {
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000);
            waitingSockets = new WaitingSockets();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    //Start using initialized in contructor socket.
    @Override 
    public void run()
    {
            while(!Thread.interrupted())
            {
                try {
                    // ServerSocket is listening and when user incomes it says: 
                    // Yea, for sure, plug into this new socket which I have 
                    // created for you. But the user don't know that ServerSocket 
                    // delegate this job to a new Thread bacuse he must wait for next 
                    // users.
                    Socket newSocket = server.accept();
                    waitingSockets.put(newSocket);
                    new Thread(new SocketConnectionHandler(waitingSockets.take(), waitingSockets)).start();
                } catch (SocketTimeoutException ex) {
                    LOGGER.log(Level.FINEST, ex.getMessage(), ex);
                } catch (IOException ex) {
                    LOGGER.log(Level.WARNING, ex.getMessage(), ex);
                }
                catch (InterruptedException ex)
                {
                    LOGGER.log(Level.WARNING, ex.getMessage(), ex);
                }
            }
    }
}

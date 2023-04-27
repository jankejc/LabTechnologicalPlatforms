/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.pg.student.s180424.lab3_maven_pt;

import java.io.Serializable;

/**
 *
 * @author Janek
 */
public class Message implements Serializable {
    private int number;
    private String content;
    //setters and getters

    public Message(int number, String content)
    {
        this.number = number;
        this.content = content;
    }
    
    public void printMessage()
    {
        System.out.println("Message number: " + number + ", content: " + content);
    }
}

    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.classes;

import ec.edu.espol.print.Print;
import ec.edu.espol.tools.LinkedList;
import java.util.Iterator;

/**
 *
 * @author centro
 */
public class Register {
    private User us;

    public Register(User us) {
        this.us = us;
    }
    
    public void saveRegister(){
        Print p = new Print();
        LinkedList<User> users = p.readRegister();
        users.addLast(us);
        p.printRegister(users);
    }
    
    public boolean verifyRegister(){
        Print pt = new Print();
        LinkedList<User> us2 = pt.readRegister();
        Iterator<User> it = us2.iterator();
        for(User user : us2){
            if(us.compareTo(user) == 0) return true;
        }
        return false;
    }
    
    public boolean verifyToRegister(){
        Print pt = new Print();
        LinkedList<User> us2 = pt.readRegister();
        for(User user : us2){
            if(us.getEmail().compareTo(user.getEmail()) == 0) return true;
        }
        return false;
    }
}

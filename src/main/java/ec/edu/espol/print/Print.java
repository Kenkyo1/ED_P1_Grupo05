
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.print;

import ec.edu.espol.Interfaces.List;
import ec.edu.espol.classes.User;
import ec.edu.espol.tools.LinkedList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author centro
 */
public class Print {
    private AES aes;
    
    public Print() {
        aes = new AES();
        try {
            aes.init();
        } catch (Exception ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void printRegister(List<User> user){
        try(BufferedWriter bf = new BufferedWriter(new FileWriter("src/main/resources/doc/Register.txt"))){
            for(User user1 : user){
                bf.write(aes.encrypt(user.toString()));
                bf.newLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public LinkedList<User> readRegister(){
        LinkedList<User> us = new LinkedList();
        try(BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/doc/Register.txt"))){
            String st;
            while((st = bf.readLine()) != null){
                String[] tk = aes.decrypt(st).split("\\|\\|");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LinkedList<String> favoritos = new LinkedList<>();
                if(tk.length == 7){
                    String[] tk2 = tk[6].split(":");
                    for(String str : tk2){
                        favoritos.addLast(str);
                    }
                }
                System.out.println(aes.decrypt(st));
                User user = new User(tk[0], tk[1], tk[2], tk[3], LocalDate.parse(tk[4], dtf), tk[5], favoritos);
                us.addLast(user);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        }
        return us;
    }
}

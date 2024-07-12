/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.print;

import ec.edu.espol.Interfaces.List;
import ec.edu.espol.classes.Car;
import ec.edu.espol.classes.TipoVehiculo;
import ec.edu.espol.print.Print;
import ec.edu.espol.tools.DoublyCircularLinkedList;
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
 * @author Alejandro Diez
 */
public class PrintCar {
    private AES aes;
    
    public PrintCar() {
        aes = new AES();
        try {
            aes.init();
        } catch (Exception ex) {
            Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void printCars(List<Car> car){
            try(BufferedWriter bf = new BufferedWriter(new FileWriter("src/main/resources/doc/Cars.txt"))){
                for(Car carp : car){
                    bf.write(aes.encrypt(carp.toString()));
                    bf.newLine();
                }
            } catch (IOException ex) {
                Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Print.class.getName()).log(Level.SEVERE, null, ex);
            }
 
    }
    
    public LinkedList<Car> readCars(){
        LinkedList<Car> us = new LinkedList();
        try(BufferedReader bf = new BufferedReader(new FileReader("src/main/resources/doc/Cars.txt"))){
            String st;
            while((st = bf.readLine()) != null){
                String[] tk = aes.decrypt(st).split("\\|\\|");
                String[] tk1 = tk[10].split(":");
                DoublyCircularLinkedList<String> rutas = new DoublyCircularLinkedList<>();
                for(String st2 : tk1){
                    rutas.addLast(st2);
                }
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                Car car = new Car(tk[0],TipoVehiculo.valueOf(tk[1].toUpperCase()), tk[2], tk[3], Integer.parseInt(tk[4]), tk[5], tk[6], Integer.parseInt(tk[7]),tk[8]
                        ,LocalDate.parse(tk[9], dtf), rutas);
                us.addLast(car);
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

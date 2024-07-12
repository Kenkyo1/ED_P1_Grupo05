/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.classes;

import ec.edu.espol.print.PrintCar;
import ec.edu.espol.tools.DoublyCircularLinkedList;
import ec.edu.espol.tools.LinkedList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author centro
 */
public class Car implements Comparable<Car>{
    private String email;
    private TipoVehiculo tipo;
    private String brand;
    private String model;
    private int kilometers;
    private String plate;
    private String color;
    private String desperfectos;
    private LocalDate mantenimiento;
    private int price;
    private DoublyCircularLinkedList<String> rutas;

    public Car(String email, TipoVehiculo tipo, String brand, String model, int kilometers, String plate, String color, int price,String desperfectos, LocalDate mantenimiento ,DoublyCircularLinkedList<String> rutas) {
        this.email = email;
        this.tipo = tipo;
        this.brand = brand;
        this.model = model;
        this.kilometers = kilometers;
        this.plate = plate;
        this.color = color;
        this.price = price;
        this.desperfectos = desperfectos;
        this.mantenimiento = mantenimiento;
        this.rutas = rutas;
    }

    public TipoVehiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVehiculo tipo) {
        this.tipo = tipo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String Brand) {
        this.brand = Brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesperfectos() {
        return desperfectos;
    }

    public void setDesperfectos(String desperfectos) {
        this.desperfectos = desperfectos;
    }

    public LocalDate getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(LocalDate mantenimiento) {
        this.mantenimiento = mantenimiento;
    }
    
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return email+"||"+tipo.toString()+"||"+brand+"||"+model+"||"+kilometers+"||"+plate+"||"+color+"||"+price+"||"+desperfectos+"||"+mantenimiento.format(dtf)+"||"+rutas.toString();
    }

    public DoublyCircularLinkedList<String> getRutas() {
        return rutas;
    }

    
    
    public boolean savecar(){
        PrintCar pc = new PrintCar();
        LinkedList<Car> lc = pc.readCars();
        boolean verify = true;
        for(Car car : lc){
            if(car.equals(this)) verify = false;
        }
        if(verify == true){
            lc.addLast(this);
            pc.printCars(lc);
        }
        return verify;
    }

    @Override
    public int compareTo(Car o) {
        return this.plate.compareTo(o.getPlate());
    }

}

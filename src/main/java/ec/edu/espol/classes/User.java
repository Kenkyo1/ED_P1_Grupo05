/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.classes;

import ec.edu.espol.print.PrintCar;
import ec.edu.espol.print.Print;
import ec.edu.espol.tools.LinkedList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author centro
 */
public class User implements Comparable<User>{
    private String name;
    private String lastName;
    private String email;
    private String password;
    private LocalDate date;
    private String gender;
    private LinkedList<Car> sellCars;
    private LinkedList<String> favoritos;

    public User(String name, String lastName, String email, String password, LocalDate date, String gender, LinkedList<String> favoritos) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.date = date;
        this.gender = gender;
        this.favoritos = favoritos;
        setSellCars();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        Print p = new Print();
        LinkedList<User> users = p.readRegister();
        for(User us : users){
            if(us.equals(this)){
                this.name = us.getName();
                this.lastName = us.getLastName();
                this.date = us.getDate();
                this.gender = us.getGender();
                this.name = us.getName();
                this.favoritos = us.getFavoritos();
                setSellCars();
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return name + "||" + lastName + "||" + email + "||" + password + "||" + date.format(dtf) + "||" + gender+"||"+favoritos.toString();
    }

    public LinkedList<Car> getSellCars() {
        return sellCars;
    }

    public void setSellCars() {
        LinkedList<Car> lc1 = new LinkedList<>();
        PrintCar pc = new PrintCar();
        LinkedList<Car> lc = pc.readCars();
        for(Car car : lc){
            if(this.email.equals(car.getEmail())) lc1.addLast(car);
        }
        this.sellCars = lc1;
    }

    @Override
    public int compareTo(User o) {
        int i = this.getEmail().compareTo(o.getEmail());
        if(i == 0)
            return this.getPassword().compareTo(o.getPassword());
        return i;
    }
    
    public void addFavoritos(String st){
        if(favoritos == null) favoritos = new LinkedList<>();
        this.favoritos.addLast(st);
    }
    
    public void removeFavoritos(String st){
        int con = 0;
        if(favoritos == null) favoritos = new LinkedList<>();
        for(String str : favoritos){
            if(st.compareTo(str) == 0) favoritos.remove(con);
            con++;
        }
    }

    public LinkedList<String> getFavoritos() {
        return favoritos;
    }
    
    public LinkedList<Car> getFavoritosCars() {
        LinkedList<Car> cars = new LinkedList<>();
        PrintCar pt = new PrintCar();
        LinkedList<Car> cars2 = pt.readCars();
        for(Car car : cars2){
            for(String st : this.favoritos){
                if(st.compareTo(car.getPlate()) == 0) cars.addLast(car);
            }
        }
        return cars;
    }
    
    public boolean favoritoisEmpty(){
        return favoritos != null;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bscars;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ec.edu.espol.classes.Car;
import ec.edu.espol.classes.User;
import ec.edu.espol.print.Print;
import ec.edu.espol.tools.DoublyCircularLinkedList;
import ec.edu.espol.tools.DoublyCircularNodeList;
import ec.edu.espol.tools.LinkedList;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alejandro Diez
 */
public class ShowCarController implements Initializable {

    private HBox first;
    private DoublyCircularLinkedList<String> rutas;
    private DoublyCircularNodeList<String> cursor;
    @FXML
    private HBox all;
    private Car car;
    private User logIn;
    @FXML
    private Label tipo;
    @FXML
    private Label marca;
    @FXML
    private Label modelo;
    @FXML
    private Label kilometraje;
    @FXML
    private Label placa;
    @FXML
    private Label color;
    @FXML
    private Label precio;
    @FXML
    private FontAwesomeIconView right;
    @FXML
    private FontAwesomeIconView left;
    @FXML
    private ImageView imv;
    @FXML
    private TextArea desperfectos;
    @FXML
    private Label mantenimiento;
    @FXML
    private CheckBox fav;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desperfectos.setEditable(false);
    }    

    @FXML
    private void goBack(MouseEvent event) {
            FontAwesomeIconView fa = (FontAwesomeIconView) event.getSource();
            Stage cur = (Stage) fa.getScene().getWindow();
            cur.close();

    }
    
    public void setCars(Car car){
        this.car = car;
        this.rutas = car.getRutas();
        this.cursor = rutas.getFirst();
    }
    
    public void setLogIn(User user){
        this.logIn = user;
        if(logIn.favoritoisEmpty())
            for(Car car : logIn.getFavoritosCars()){
                if(car.compareTo(this.car) == 0) fav.setSelected(true);
            }
    }
    
    public void showCar(){
        Image img = new Image(getClass().getResourceAsStream("/img/"+cursor.getContent()));
        imv.setImage(img);
        imv.setFitHeight(160);
        imv.setFitWidth(232);   
    }
    
    public void setData(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        tipo.setText("Tipo de Vehiculo: "+car.getTipo().toString());
        marca.setText("Marca: "+car.getBrand());
        modelo.setText("Modelo: "+car.getModel());
        kilometraje.setText("Kilometraje: "+car.getKilometers());
        placa.setText("Placa : "+car.getPlate());
        color.setText("Color: "+car.getColor());
        precio.setText("Precio: "+car.getPrice());
        desperfectos.setText("Desperfectos: "+ car.getDesperfectos());
        mantenimiento.setText("Ultimo mantenimiento :"+ car.getMantenimiento().format(dtf));
    }

    @FXML
    private void buttonRight(MouseEvent event) {
        cursor = cursor.getNext();
        showCar();
    }

    @FXML
    private void buttonLeft(MouseEvent event) {
        cursor = cursor.getPrevius();
        showCar();
    }

    @FXML
    private void editFav(MouseEvent event) {
        if(fav.isSelected())logIn.addFavoritos(car.getPlate());
        else logIn.removeFavoritos(car.getPlate());
        Print pt = new Print();
        LinkedList<User> us = pt.readRegister();
        int con = 0;
        for(User user : us){
            if(logIn.compareTo(user) == 0) us.set(con, logIn);
            con++;
        }
        pt.printRegister(us);
    }
}

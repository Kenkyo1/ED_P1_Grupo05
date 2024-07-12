/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bscars;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ec.edu.espol.classes.Car;
import ec.edu.espol.classes.TipoVehiculo;
import ec.edu.espol.classes.User;
import ec.edu.espol.print.PrintCar;
import ec.edu.espol.tools.DoublyCircularLinkedList;
import ec.edu.espol.tools.DoublyCircularNodeList;
import ec.edu.espol.tools.LinkedList;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
public class EditCarsController implements Initializable {

    private int count;
    private DoublyCircularLinkedList<Car> cars;
    private DoublyCircularNodeList<Car> cursorCar;
    private DoublyCircularLinkedList<String> rutas;
    private DoublyCircularNodeList<String> cursorRuta;
    @FXML
    private Label con;
    private User logIn;
    @FXML
    private TextField color;
    @FXML
    private ComboBox<TipoVehiculo> tip;
    @FXML
    private TextField brand;
    @FXML
    private TextField model;
    @FXML
    private TextField kilos;
    @FXML
    private TextField plate;
    @FXML
    private TextField price;
    @FXML
    private HBox all;
    @FXML
    private ImageView imv;
    @FXML
    private FontAwesomeIconView right;
    @FXML
    private FontAwesomeIconView left;
    @FXML
    private Button edit;
    @FXML
    private FontAwesomeIconView nextCar;
    @FXML
    private FontAwesomeIconView previusCar;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private TextArea desperfectos;
    @FXML
    private DatePicker mantenimiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cars = new DoublyCircularLinkedList();
        tip.getItems().addAll(TipoVehiculo.AUTOS, TipoVehiculo.ACUATICOS, TipoVehiculo.MOTOS, TipoVehiculo.PESADOS, TipoVehiculo.MAQUINARIAS, TipoVehiculo.OTROS);
        count = 0;
    }    

    @FXML
    private void buttonRight(MouseEvent event) {
        cursorRuta = cursorRuta.getNext();
        Image img = new Image(getClass().getResourceAsStream("/img/"+cursorRuta.getContent()));
        imv.setImage(img);
        imv.setFitHeight(160);
        imv.setFitWidth(232);  
    }

    @FXML
    private void buttonLeft(MouseEvent event) {
        cursorRuta = cursorRuta.getPrevius();
        Image img = new Image(getClass().getResourceAsStream("/img/"+cursorRuta.getContent()));
        imv.setImage(img);
        imv.setFitHeight(160);
        imv.setFitWidth(232);  
    }

    @FXML
    private void removeCar(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Desea eliminar el vehiculo");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            cursorCar = cursorCar.getNext();
            rutas = cursorCar.getContent().getRutas();
            cursorRuta = rutas.getFirst();
            cars.remove(count);
            if(count > cars.size()-1) count = 0;
            showCar();
            PrintCar ptcar = new PrintCar();
            ptcar.printCars(cars);
        }
    }

    @FXML
    private void rewriteCar(MouseEvent event) {
        if(verifyfields() == true){
            Car car = cursorCar.getContent();
            car.setTipo(tip.getValue());
            car.setBrand(brand.getText());
            car.setModel(model.getText());
            car.setPrice(Integer.parseInt(price.getText()));
            car.setKilometers(Integer.parseInt(kilos.getText()));
            car.setColor(color.getText());
            car.setPlate(plate.getText());
            car.setDesperfectos(desperfectos.getText());
            car.setMantenimiento(mantenimiento.getValue());
            cars.set(count, car);
            PrintCar ptcar = new PrintCar();
            ptcar.printCars(cars);
            showCar();
        }
    }

    @FXML
    private void showNextCar(MouseEvent event) {
        cursorCar = cursorCar.getNext();
        rutas = cursorCar.getContent().getRutas();
        cursorRuta = rutas.getFirst();
        count++;
        if(count > cars.size()-1) count = 0;
        showCar();

    }

    @FXML
    private void showPreviusCar(MouseEvent event) {
        cursorCar = cursorCar.getPrevius();
        rutas = cursorCar.getContent().getRutas();
        cursorRuta = rutas.getFirst();
        count--;
        if(count < 0)count = cars.size()-1;
        showCar();
    }

    public void setLogIn(User logIn) {
        this.logIn = logIn;
        upload();
        showCar();
    }
    
    private void upload(){
        PrintCar pt = new PrintCar();
        LinkedList<Car> printcars = pt.readCars();
        for(Car car : printcars){
            if(logIn.getEmail().compareTo(car.getEmail()) == 0)
                cars.addLast(car);
        }
        cursorCar = cars.getFirst();
        rutas = cursorCar.getContent().getRutas();
        cursorRuta = rutas.getFirst();
    }
    
    private void showCar(){
        Car car = cursorCar.getContent();
        tip.setValue(TipoVehiculo.AUTOS);
        brand.setText(car.getBrand());
        model.setText(car.getModel());
        kilos.setText(""+car.getKilometers());
        price.setText(""+car.getPrice());
        plate.setText(car.getPlate());
        color.setText(car.getColor());
        mantenimiento.setValue(car.getMantenimiento());
        desperfectos.setText(car.getDesperfectos());
        Image img = new Image(getClass().getResourceAsStream("/img/"+cursorRuta.getContent()));
        imv.setImage(img);
        imv.setFitHeight(160);
        imv.setFitWidth(232);
        con.setText(""+(count+1));
    }

    @FXML
    private void goBack(MouseEvent event) {
        try {
            FXMLLoader fml;
            fml = App.loadFXML("principalPlatform");
            Scene sc = new Scene(fml.load());
            PrincipalPlatformController ppc = fml.getController();
            ppc.setLogIn(logIn);
            Stage st = new Stage();
            st.setScene(sc);
            st.show();

            FontAwesomeIconView fai = (FontAwesomeIconView) event.getSource();
            Stage cur = (Stage) fai.getScene().getWindow();
            cur.close();
        } catch (IOException ex) {
            Logger.getLogger(SellController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean verifyfields(){
        Alert a3 = new Alert(AlertType.ERROR, "El kilometraje debe ser un número");
        Alert a4 = new Alert(AlertType.ERROR, "El precio debe ser un número");
        Alert al = new Alert(AlertType.ERROR, "Existen campos vacios");
        if(tip.getValue() == null) {al.show(); return false;}
        if(brand.getText().isBlank()) {al.show(); return false;}
        if(model.getText().isBlank()) {al.show(); return false;}
        if(kilos.getText().isBlank()) {al.show(); return false;}
        if(model.getText().isBlank()) {al.show(); return false;}
        if(price.getText().isBlank()) {al.show(); return false;}
        if(plate.getText().isBlank()) {al.show(); return false;}
        try{
            Integer.parseInt(kilos.getText());
        }
        catch(Exception ex){
            a3.show();
            return false;
        }
        try{
            Integer.parseInt(price.getText());
        }
        catch(Exception ex){
            a4.show();
            return false;
        }
        return true;
    }
}

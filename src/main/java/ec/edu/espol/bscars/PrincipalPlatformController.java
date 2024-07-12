/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bscars;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ec.edu.espol.classes.Car;
import ec.edu.espol.classes.Filtro;
import ec.edu.espol.tools.LinkedList;
import ec.edu.espol.print.PrintCar;
import ec.edu.espol.classes.TipoVehiculo;
import ec.edu.espol.classes.User;
import ec.edu.espol.tools.NodeList;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author centro
 */
public class PrincipalPlatformController implements Initializable {

    private User logIn;
    @FXML
    private Button sell;
    @FXML
    private ComboBox<TipoVehiculo> tip;
    @FXML
    private ComboBox<String> brand;
    @FXML
    private ComboBox<String> model;
    @FXML
    private ComboBox<Integer> startPrice;
    @FXML
    private ComboBox<Integer> endPrice;
    @FXML
    private Button search;
    @FXML
    private HBox highlights;
    private LinkedList<Car> allcars;
    private LinkedList<Car> filtredcars;
    private LinkedList<Car> filtredcars2;
    @FXML
    private Label user;
    @FXML
    private Button yourcars;
    @FXML
    private ComboBox<Integer> startkilo;
    @FXML
    private ComboBox<Integer> endekilo;
    @FXML
    private CheckBox desperfectos;
    @FXML
    private ComboBox<String> mantenimiento;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filtredcars2 = new LinkedList();
        PrintCar pc = new PrintCar();
        allcars = pc.readCars();
        filtredcars = new LinkedList<>();
        tip.getItems().addAll(TipoVehiculo.AUTOS, TipoVehiculo.ACUATICOS, TipoVehiculo.MOTOS, TipoVehiculo.PESADOS, TipoVehiculo.MAQUINARIAS, TipoVehiculo.OTROS);
        showHighlights();
        highlights.setSpacing(5);
        mantenimiento.getItems().addAll("Menos de 6 meses","Menos de 1 año");
    }    

    @FXML
    private void goSell(MouseEvent event) {
        try {
            FXMLLoader fml;
            fml = App.loadFXML("sell");
            Scene sc = new Scene(fml.load());
            SellController sec = fml.getController();
            sec.setLogIn(logIn);
            Stage st = new Stage();
            st.setScene(sc);
            st.show();
            
            Button b = (Button) event.getSource();
            Stage cur = (Stage) b.getScene().getWindow();
            cur.close();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalPlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goSearch(MouseEvent event) {
        try {
            FXMLLoader fml;
            fml = App.loadFXML("search");
            Scene sc = new Scene(fml.load());
            SearchController sear = fml.getController();
            filte();
            sear.setCars(filte());
            sear.setLogIn(logIn);
            Stage st = new Stage();
            st.setScene(sc);
            st.show();
            
            Button b = (Button) event.getSource();
            Stage cur = (Stage) b.getScene().getWindow();
            cur.close();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalPlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goYourcars(MouseEvent event) {
        try {
            FXMLLoader fml;
            fml = App.loadFXML("editCars");
            Scene sc = new Scene(fml.load());
            EditCarsController sear = fml.getController();
            sear.setLogIn(logIn);
            Stage st = new Stage();
            st.setScene(sc);
            st.show();
            
            Button b = (Button) event.getSource();
            Stage cur = (Stage) b.getScene().getWindow();
            cur.close();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalPlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void filter1(ActionEvent event) {
        brand.getItems().clear();
        model.getItems().clear();
        startPrice.getItems().clear();
        endPrice.getItems().clear();
        startkilo.getItems().clear();
        endekilo.getItems().clear();
        if(tip.getValue() != null ){
            for(Car car : allcars){
                if(car.getTipo().compareTo(tip.getValue()) == 0){
                    boolean add = true;
                    for(Car car2 : filtredcars){
                        if(car2.getBrand().equals(car.getBrand())) add = false;
                    }
                    filtredcars.addLast(car);
                    if(add == true) brand.getItems().add(car.getBrand());
                }
            }
        }
    }

    @FXML
    private void filter2(ActionEvent event) {
        if(brand.getValue() != null){
            int con = 0;
            for(Car car : filtredcars){
                if(car.getBrand().compareTo(brand.getValue()) != 0){
                    filtredcars.remove(con);
                }
                else{
                    model.getItems().add(car.getModel());
                }
                con++;
            }
        }     
    }

    @FXML
    private void filter3(ActionEvent event) {
        filtredcars2.clear();
        if(model.getValue() != null){
            int con = 0;
            for(Car car : filtredcars){
                if(car.getModel().compareTo(model.getValue()) == 0){
                    filtredcars2.addLast(car);
                }
                con++;
            }
            setStartPrice();
            setEndPrice();
        }   
    }

    public void setStartPrice() {
        startPrice.getItems().clear();
        for(Car car : filtredcars2){
            startPrice.getItems().add(car.getPrice());
            startkilo.getItems().add(car.getKilometers());
        }
    }

    public void setEndPrice() {
        for(Car car : filtredcars2){
            endPrice.getItems().add(car.getPrice());
            endekilo.getItems().add(car.getKilometers());
        }
    }

    public void setLogIn(User logIn) {
        this.logIn = logIn;
        user.setText(this.logIn.getName());
    }
    
    public void showHighlights(){
        BorderStroke borderStroke = new BorderStroke(Color.LIGHTGREY,BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(2));
        VBox vb;
        ImageView imv;
        Label lb;
        Label lb2;
        int con = 0;
        for(NodeList<Car> node = allcars.getHeader() ; node != null && con < 5 ; node = node.getNext()){
            Car car = node.getContent();
            vb = new VBox();
            String st = car.getRutas().getFirst().getContent();
            Image img = new Image(getClass().getResourceAsStream("/img/"+st));
            imv = new ImageView();
            imv.setImage(img);
            imv.setFitHeight(112);
            imv.setFitWidth(112);
            lb = new Label(car.getModel());
            lb2 = new Label("$"+car.getPrice());
            vb.getChildren().addAll(imv,lb,lb2);
            vb.setBorder(new Border(borderStroke));
            vb.setPadding(new Insets(5));
            vb.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent)-> {
                try {
                    FXMLLoader fml = App.loadFXML("showCar");
                    Scene sc = new Scene(fml.load());
                    ShowCarController scc = fml.getController();
                    scc.setCars(car);
                    scc.setLogIn(logIn);
                    scc.showCar();
                    scc.setData();
                    Stage sta = new Stage();
                    sta.setScene(sc);
                            
                    VBox vb1 = (VBox) MouseEvent.getSource();
                    Stage cur = (Stage) vb1.getScene().getWindow();
                    cur.hide();
                    sta.showAndWait();
                    cur.show();
                    } catch (IOException ex) {
                        Logger.getLogger(PrincipalPlatformController.class.getName()).log(Level.SEVERE, null, ex);
                    }    
            });
        highlights.getChildren().addAll( vb);
        con++;
        }
    }
    
    private LinkedList<Car> filte(){
        
        LinkedList<Car> filtredcars3 = Filtro.filterAndSortCars(
                    allcars,
                    tip.getValue(),
                    brand.getValue(),
                    model.getValue(),
                    startkilo.getValue(),
                    endekilo.getValue(),
                    startPrice.getValue(),
                    endPrice.getValue(),
                    mantenimiento.getValue(),
                    desperfectos.isSelected(),
                    Comparator.comparing(Car::getPrice)
            );
        return filtredcars3;
    }

    @FXML
    private void favoritos(MouseEvent event) {

        try {
            FXMLLoader fml;
            fml = App.loadFXML("search");
            Scene sc = new Scene(fml.load());
            SearchController sec = fml.getController();
            sec.setLogIn(logIn);
            sec.setCars(logIn.getFavoritosCars());
            Stage st = new Stage();
            st.setScene(sc);
            st.show();
            
            FontAwesomeIconView fa = (FontAwesomeIconView) event.getSource();
            Stage cur = (Stage) fa.getScene().getWindow();
            cur.close();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalPlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bscars;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ec.edu.espol.classes.Car;
import ec.edu.espol.tools.LinkedList;
import ec.edu.espol.classes.User;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alejandro Diez
 */
public class SearchController implements Initializable {
    
    private User logIn;
    private LinkedList<Car> cars;
    @FXML
    private FontAwesomeIconView desc;
    @FXML
    private FontAwesomeIconView asc;
    @FXML
    private ComboBox<String> filter;
    @FXML
    private FlowPane show;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cars = new LinkedList<>();
        show.setHgap(10); 
        show.setVgap(10);
        filter.getItems().addAll("Modelo","Kilometraje","Precio");
        
    }    
    
    public void setCars(LinkedList<Car> cars){
        this.cars = cars;
        Comparator<Car> com = ((o1, o2) -> o1.getModel().compareTo(o2.getModel()));
        priority(com);
    }

    public void setLogIn(User logIn) {
        this.logIn = logIn;
    }
    
    public void showOptions(PriorityQueue<Car> cars){
        show.getChildren().clear();
        BorderStroke borderStroke = new BorderStroke(Color.LIGHTGREY,BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(2));
        VBox vb;
        ImageView imv;
        Label lb;
        Label lb2;
        Label lb3;
        while(!cars.isEmpty()){
            Car car = cars.poll();
            vb = new VBox();
            String st = car.getRutas().getFirst().getContent();
            Image img = new Image(getClass().getResourceAsStream("/img/"+st));
            imv = new ImageView();
            imv.setImage(img);
            imv.setFitHeight(112);
            imv.setFitWidth(112);
            lb = new Label(car.getModel());
            lb2 = new Label(car.getKilometers()+"kms");
            lb3 = new Label("$"+car.getPrice());
            vb.getChildren().addAll(imv,lb,lb2,lb3);
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
        show.getChildren().addAll( vb);
        }
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
            
            FontAwesomeIconView fa = (FontAwesomeIconView) event.getSource();
            Stage cur = (Stage) fa.getScene().getWindow();
            cur.close();
        } catch (IOException ex) {
            Logger.getLogger(PrincipalPlatformController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }

    @FXML
    private void descFilter(MouseEvent event) {
        priority(comparator(filter.getValue()).reversed());
        asc.setDisable(false);
        desc.setDisable(true);
    }

    @FXML
    private void ascFilter(MouseEvent event) {
        priority(comparator(filter.getValue()));
        asc.setDisable(true);
        desc.setDisable(false);
        
    }

    @FXML
    private void filterBy(ActionEvent event) {
        priority(comparator(filter.getValue()));
        asc.setDisable(true);
        desc.setDisable(false);
        
    }
    
    private void priority(Comparator com){
        PriorityQueue<Car> filtercars = new PriorityQueue<>(com);
        for(Car car : cars){
            filtercars.offer(car);
        }
        showOptions(filtercars);
    }
    
    private Comparator<Car> comparator(String string){
            if(string.compareTo("Precio") == 0){
                Comparator<Car> com = ((o1, o2) -> Integer.compare(o1.getPrice(), o2.getPrice()));
                return com;
            }
            if(string.compareTo("Kilometraje") == 0){
                Comparator<Car> com = ((o1, o2) -> Integer.compare(o1.getKilometers(), o2.getKilometers()));
                return com;
            }
            Comparator<Car> com = ((o1, o2) -> o1.getModel().compareTo(o2.getModel()));
            return com;
    }
}

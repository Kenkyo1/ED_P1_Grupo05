/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bscars;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ec.edu.espol.classes.Car;
import ec.edu.espol.classes.TipoVehiculo;
import ec.edu.espol.classes.User;
import ec.edu.espol.tools.DoublyCircularLinkedList;
import ec.edu.espol.tools.DoublyCircularNodeList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alejandro Diez
 */
public class SellController implements Initializable {

    private DoublyCircularLinkedList<String> rutas;
    private DoublyCircularLinkedList<File> files;
    private DoublyCircularNodeList<File> cursor; 
    private User logIn;
    @FXML
    private ComboBox<TipoVehiculo> tip;
    @FXML
    private TextField color;
    @FXML
    private TextField kilometers;
    @FXML
    private TextField plate;
    @FXML
    private TextField price;
    @FXML
    private Button Confirm;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private TextField brand;
    @FXML
    private TextField model;
    @FXML
    private Button update;
    @FXML
    private ImageView firstView;
    @FXML
    private FontAwesomeIconView right;
    @FXML
    private FontAwesomeIconView left;
    @FXML
    private DatePicker mantenimiento;
    @FXML
    private TextArea desperfectos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tip.getItems().addAll(TipoVehiculo.AUTOS, TipoVehiculo.ACUATICOS, TipoVehiculo.MOTOS, TipoVehiculo.PESADOS, TipoVehiculo.MAQUINARIAS, TipoVehiculo.OTROS);
        files = new DoublyCircularLinkedList<>();
        rutas = new DoublyCircularLinkedList<>();
        firstView.setFitHeight(156);
        firstView.setFitWidth(260);
        left.setDisable(true);
        right.setDisable(true);
    }    

    @FXML
    private void saveAndBack(MouseEvent event) {
        Car car;
        Alert a3 = new Alert(AlertType.ERROR, "El vehiculo ya estaba registrado");
        Alert a4 = new Alert(AlertType.INFORMATION, "El vehiculo se registro con exito");
        int kilo;
        int pri;
        if(verifyfields() == true){
            pri = Integer.parseInt(price.getText());
            kilo = Integer.parseInt(kilometers.getText());
            setRutas();
            car = new Car("hola", tip.getValue(), brand.getText(), model.getText(), kilo, plate.getText(), color.getText(), pri,desperfectos.getText(), mantenimiento.getValue(), rutas);
            boolean very = car.savecar();
            if(very == false) a3.show();
            else {saveImages(); a4.showAndWait();}
            try {
                FXMLLoader fml;
                fml = App.loadFXML("principalPlatform");
                Scene sc = new Scene(fml.load());
                PrincipalPlatformController ppc = fml.getController();
                ppc.setLogIn(logIn);
                Stage st = new Stage();
                st.setScene(sc);
                st.show();
                
                Button b = (Button) event.getSource();
                Stage cur = (Stage) b.getScene().getWindow();
                cur.close();
            } catch (IOException ex) {
                Logger.getLogger(SellController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void goPrincipalPlatform(MouseEvent event) {
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
            Logger.getLogger(SellController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean verifyfields(){
        Alert a3 = new Alert(AlertType.ERROR, "El kilometraje debe ser un número");
        Alert a4 = new Alert(AlertType.ERROR, "El precio debe ser un número");
        Alert al = new Alert(AlertType.ERROR, "Existen campos vacios");
        Alert a2 = new Alert(AlertType.ERROR, "Agrege minimo una imagen");
        if(tip.getValue() == null) {al.show(); return false;}
        if(brand.getText().isBlank()) {al.show(); return false;}
        if(model.getText().isBlank()) {al.show(); return false;}
        if(kilometers.getText().isBlank()) {al.show(); return false;}
        if(model.getText().isBlank()) {al.show(); return false;}
        if(price.getText().isBlank()) {al.show(); return false;}
        if(plate.getText().isBlank()) {al.show(); return false;}
        if(files.isEmpty()){a2.show(); return false;};
        if(mantenimiento.getValue() == null) {al.show(); return false;}
        try{
            Integer.parseInt(kilometers.getText());
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

    public void setLogIn(User logIn) {
        this.logIn = logIn;
    }

    @FXML
    private void updateImages(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif", ".jfif"));
        Button b = (Button) event.getSource();
        File f = fc.showOpenDialog((Stage) b.getScene().getWindow());
        
        if(f != null){
            try {
                files.addLast(f);
                Image image = new Image(new FileInputStream(f));
                if((firstView != null && firstView.getImage() == null)){
                    firstView.setImage(image);
                    firstView.setFitHeight(260);
                    firstView.setFitWidth(156);
                    cursor = files.getFirst();
                    left.setDisable(false);
                    right.setDisable(false);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void saveImages(){ 
        for(File file : files){
                String ruta = file.getName();
                Path projectdir = Paths.get("").toAbsolutePath();
                Path rutaDestino = projectdir.resolve(Paths.get("src/main/resources/img",ruta));
            try {
                Files.copy(file.toPath(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {

            }
        }
    }
    
    public void setRutas(){
        for(File file : files){
//            Alert al = new Alert(AlertType.ERROR,file.getName());
//            al.show();
            String ruta = file.getName();
            rutas.addLast(ruta);
        }
    }

    @FXML
    private void buttonRight(MouseEvent event) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(cursor.getNext().getContent()));    
        firstView.setImage(image);
        firstView.setFitHeight(156);
        firstView.setFitWidth(260);
        cursor = cursor.getNext();
    }

    @FXML
    private void buttonLeft(MouseEvent event) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(cursor.getPrevius().getContent()));    
        firstView.setImage(image);
        firstView.setFitHeight(156);
        firstView.setFitWidth(260);
        cursor = cursor.getPrevius();
    }
}

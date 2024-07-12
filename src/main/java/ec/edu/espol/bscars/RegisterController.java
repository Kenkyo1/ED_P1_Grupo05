/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bscars;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import ec.edu.espol.classes.Register;
import ec.edu.espol.classes.User;
import ec.edu.espol.tools.LinkedList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alejandro Diez
 */
public class RegisterController implements Initializable {
    
    Register reg;
    @FXML
    private TextField email;
    @FXML
    private Button create;
    @FXML
    private FontAwesomeIconView cancel;
    @FXML
    private PasswordField pass;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> gender;
    @FXML
    private TextField name;
    @FXML
    private TextField lastname;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gender.getItems().addAll("Masculino", "Femenino","Otros");
        gender.setValue("Masculino");
    }    

    @FXML
    private void createAccount(MouseEvent event) {
        Alert a1 = new Alert(AlertType.INFORMATION, "Su cuenta a sido creada con exito");
        if(validation()){
            reg.saveRegister();
            a1.showAndWait();
            try {
                FXMLLoader fml;
                fml = App.loadFXML("LogIn");
                Scene sc = new Scene(fml.load());
                Stage st = new Stage();
                st.setScene(sc);
                st.show();
            
                Button bt = (Button) event.getSource();
                Stage cur = (Stage) bt.getScene().getWindow();
                cur.close();
            } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @FXML
    private void exit(MouseEvent event) {
        FXMLLoader fml;
        try {
            fml = App.loadFXML("logIn");
            Scene sc = new Scene(fml.load());
            Stage st = new Stage();
            st.setScene(sc);
            st.show();
            
            FontAwesomeIconView fa = (FontAwesomeIconView) event.getSource();
            Stage cur = (Stage) fa.getScene().getWindow();
            cur.close();
        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean validation(){
        LinkedList<String> str = new LinkedList<>();
        reg = new Register(new User(name.getText(), lastname.getText(), email.getText(),pass.getText(), date.getValue(), gender.getValue(), str));
        Alert a1 = new Alert(AlertType.ERROR, "Existen campos vacios");
        Alert a2 = new Alert(AlertType.ERROR, "Ya existe una cuenta con ese correo");
        if(name.getText() == null || name.getText().isBlank()){ 
            a1.show();
            return false;
        }
        if(lastname.getText() == null || lastname.getText().isBlank()){ 
            a1.show();
            return false;
        }
        if(email.getText() == null || email.getText().isBlank()){ 
            a1.show();
            return false;
        }
        if(pass.getText() == null || pass.getText().isBlank()){ 
            a1.show();
            return false;
        }
        if(gender.getValue() == null || gender.getValue().isBlank()){ 
            a1.show();
            return false;
        }
        if(reg.verifyToRegister()){
            a2.show();
            return false;
        }
        return true;
    }
    
}

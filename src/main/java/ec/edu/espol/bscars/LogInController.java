/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bscars;

import ec.edu.espol.classes.Register;
import ec.edu.espol.classes.User;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alejandro Diez
 */
public class LogInController implements Initializable {
    
    private User logIn;
    private Register reg;
    @FXML
    private TextField Email;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Label link;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void GoPlatform(MouseEvent event) {
        if(validation()){
            FXMLLoader fml;
            try {
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
                Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void goRegister(MouseEvent event) {
            FXMLLoader fml;
            try {
            fml = App.loadFXML("register");
            Scene sc = new Scene(fml.load());
            Stage st = new Stage();
            st.setScene(sc);
            st.show();
            
            Label lb = (Label) event.getSource();
            Stage cur = (Stage) lb.getScene().getWindow();
            cur.close();
        }catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean validation(){
        Alert al1 = new Alert(AlertType.ERROR, "Rellene los campos faltantes");
        Alert al2 = new Alert(AlertType.ERROR, "Contraseña o usuario incorrectos");
        if(Email.getText() == null || Email.getText().isBlank()){ 
            al1.show();
            return false;
        }
        if(password.getText() == null || password.getText().isBlank()){ 
            al1.show();
            return false;
        }
        reg = new Register(new User(Email.getText(), password.getText()));
        if(reg.verifyRegister() == false){ 
            al2.show();
            return false;
        }
        logIn = new User(Email.getText(), password.getText());
        return true;
    }
}

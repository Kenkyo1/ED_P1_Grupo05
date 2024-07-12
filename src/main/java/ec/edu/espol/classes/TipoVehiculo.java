/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package ec.edu.espol.classes;

/**
 *
 * @author Alejandro Diez
 */
public enum TipoVehiculo {
    AUTOS("Autos"), MOTOS("Motos"), PESADOS("Pesados"), MAQUINARIAS("Maquinarias"), ACUATICOS("Acuaticos"), OTROS("Otros");
    
    private final String displayName;
    
    TipoVehiculo(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

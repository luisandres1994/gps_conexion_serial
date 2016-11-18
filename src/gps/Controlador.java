/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gps;


import giovynet.nativelink.SerialPort;
import giovynet.serial.Baud;
import giovynet.serial.Com;
import giovynet.serial.Parameters;
import java.util.List;
/**
 *
 * @author Andres
 */
public class Controlador {
    
    private SerialPort serial;
    private Home Principal;
    private Conexion con;
    public Controlador() {
        serial = new SerialPort();
        Principal = new Home();
        Principal.setVisible(true);
    }
    
    
    
    
    
}

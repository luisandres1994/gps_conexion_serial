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
import java.io.IOException;
import java.util.List;
/**
 *
 * @author Andres
 */


public class Controlador {
    
    
    
    
    
    private SerialPort serial;
    private Home Principal;
    private Conexion con;
    public String gprmc;
    private Read_GPS RG;
    public Controlador() throws IOException {
        serial = new SerialPort();
        gprmc="$GPRMC,194509.000,A,4042.6142,N,07400.4168,W,2.03,221.11,160412,,,A*77";
        RG = new Read_GPS(this);
        RG.start();
        Principal = new Home(this);
        Principal.setVisible(true);
        
    }
    
   
    
    public List <String> get_puertos() throws Exception
    {
        List <String> portsfree = serial.getFreeSerialPort();
        return portsfree;
    }
    
    
    
    
    
    
    
}

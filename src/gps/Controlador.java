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
    private String data_rmc[];
    public VentanaFinal VF;
    public Controlador() throws IOException {
        serial = new SerialPort();
        gprmc="$GPRMC,194509.000,A,4042.6142,N,07400.4168,W,2.03,221.11,160412,,,A*77";
        RG = new Read_GPS(this);
        
        //VF = new VentanaFinal(this);
        //VF.setVisible(true);
        RG.start();
        Principal = new Home(this);
        Principal.setVisible(true);
        
    }
    
    public void Matarhilo(){RG.interrupt();}
    
    public void codificar()
    {
        
        data_rmc = gprmc.split(",");
    }
    
    
    
    public List <String> get_puertos() throws Exception
    {
        List <String> portsfree = serial.getFreeSerialPort();
        return portsfree;
    }
    
    public String get_latitud()
    {
        String aux = data_rmc[3];
        String latitud = "";
        if("S".equals(data_rmc[4]))
        {
            latitud+="-";
        }
        
        latitud+=aux.charAt(0);
        latitud+=aux.charAt(1);
        latitud+='.';
        for(int i=2; i<aux.length(); i++)
        {
            if(aux.charAt(i)!='.')
                latitud+=aux.charAt(i);
        }
        return latitud;
        
    }
    
    public String get_longitud()
    {
        String aux = data_rmc[5];
        String longitud = "";
        if("W".equals(data_rmc[6]))
        {
            longitud+="-";
        }
        if(aux.charAt(0)!='0')
            longitud+=aux.charAt(0);
        
        longitud+=aux.charAt(1);
        longitud+=aux.charAt(2);
        longitud+='.';
        for(int i=3; i<aux.length(); i++)
        {
            if(aux.charAt(i)!='.')
                longitud+=aux.charAt(i);
        }
        return longitud;
    }
    
    
    
    
    
    
    
}

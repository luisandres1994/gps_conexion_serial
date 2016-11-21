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
    public String gprmc,gpgga,gpgsv,gpgsa;
    private Read_GPS RG;
    private String data_rmc[];
    private String data_gga[];
    private String data_gsv[];
    private String data_gsa[];
    public VentanaFinal VF;
    public boolean modo;
    private Parameters com;
    private Com conection;
    public String puerto,baudios;
    
    public Controlador()  {
     modo=false;
        serial = new SerialPort();
        gprmc="";
        gpgga="";
        gpgsv="";
        Principal = new Home(this);
        Principal.setVisible(true);
    }
    
    public void iniciarlectura() throws IOException
    {
        RG = new Read_GPS(this,modo);
        RG.start();
    }
    
    public void Matarhilo(){RG.interrupt();}
    
    public void codificar()
    {
        data_rmc = gprmc.split(",");
        data_gga = gpgga.split(",");
        data_gsv = gpgsv.split(",");
        data_gsa = gpgsa.split(",");
    }
    
    public String[] getDataGGA(){
        return data_gga;
    }
    
    public String[] getDataGSA(){
        return data_gsa;
    }
    
    public String[] getDataGSV(){
        return data_gsv;
    }
    
    public String[] getDataRMC(){
        return data_rmc;
    }
    
    
    
    public boolean probarconexion(String p,String b) throws Exception
    {
        puerto=p;
        baudios=b;
        boolean exito=false;
        int seg=0;
        com = new Parameters();
        com.setPort(puerto);
        com.setBaudRate(Baud.valueOf(baudios));
        conection= new Com(com);
        String line="";
        String x;
        while(seg<5)
        {
            x= conection.receiveSingleString();
            if(!"".equals(x))
            {
                exito=true;
                break;
            }
            Thread.sleep(1000);
            seg++;                    
        }
        conection.close();
        return exito;
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

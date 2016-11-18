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
public class Gps {

    /**
     * @param args the command line arguments
     * 
     */
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        SerialPort serial= new SerialPort();
        Interfaz it;
        Home h;
        Controlador Control=new Controlador();
        //h=new Home();
        //h.setVisible(true);
        
        //it=new Interfaz();
        //it.setVisible(true);
        /*
        List <String> portsfree = serial.getFreeSerialPort();
        for(String free: portsfree)
        {
            System.out.println(free);
        }
        
        Parameters com1 = new Parameters();
        com1.setPort("COM5");
        com1.setBaudRate(Baud.valueOf("_9600"));
        Com conection = new Com(com1);
        boolean gp;
        gp=false;
        String line,x;
        line="";
        while(true)
        {
            x= conection.receiveSingleString();
           if(!"\n".equals(x))
            {
                line+=x;
                if("C".equals(x)) gp=true;
            }else
            {
                
                if(gp) 
                {
                    gprmc=line;
                    gp=false;
                }
               // System.out.println(line);
                
                line="";
            }
            
        }
        
        */
        
        
    }
    
}

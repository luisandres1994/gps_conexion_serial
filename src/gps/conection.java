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
public class conection {
     
    public Com conection;
    private String data;

    public conection(String puerto, String velocidad) throws Exception {
        Parameters com1 = new Parameters();
        com1.setPort(puerto);
        com1.setBaudRate(Baud.valueOf("_"+velocidad));
        conection = new Com(com1);   
    }
    
    public String read_line() throws Exception
    {
        data="";
        String x;
        while(!"\n".equals(x= conection.receiveSingleString()))
        {
            data+=x;
        }
        return data;
    }
    
    
    
    
    
}

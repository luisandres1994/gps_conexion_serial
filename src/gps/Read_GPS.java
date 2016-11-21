/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gps;

import giovynet.serial.Baud;
import giovynet.serial.Com;
import giovynet.serial.Parameters;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedList;

/**
 *
 * @author Andres
 */
public class Read_GPS extends Thread{
    
    private Controlador C;
    private File F;
    private FileReader f;
    private BufferedReader b;
    private LinkedList cola;
    private boolean modo;
    
    public Read_GPS(Controlador ct, boolean m) throws FileNotFoundException, IOException {
        C=ct;
        modo=m;
        
    }

    public void run()
    {
        String aux;
        try{
            
            if(modo==false)
            {
                F= new File("log.txt");
                f = new FileReader(F);
                String cadena;
                cola = new LinkedList();
                BufferedReader b = new BufferedReader(f);
                 while((cadena = b.readLine())!=null) {
                  cola.addFirst(cadena);
                }
                b.close();
                f.close();
                
                while(!cola.isEmpty())
                {


                    aux=(String) cola.peekLast();
                    if(aux.charAt(5)=='C')
                    {
                        C.gprmc=aux;
                        Thread.sleep(1000);
                    }
                    cola.removeLast();
                    cola.addFirst(aux);
                }
            }else
            {
             
                Parameters com1 = new Parameters();
                com1.setPort(C.puerto);
                com1.setBaudRate(Baud.valueOf(C.baudios));
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
                            C.gprmc=line;
                            gp=false;
                        }

                        line="";
                    }
            
                }
                
            }
        } catch (Exception ex) {}
        
    }
    
    
    
    
}

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
    private VentanaFinal VF;
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
        boolean rmc,gga,gsv,gsa;
        try{
            
            if(modo==false)
            {
                rmc=gga=gsv=gsa=false;
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
                //Thread.sleep(5000);
                while(!cola.isEmpty())
                {
                    aux=(String) cola.peekLast();
                    if(aux.charAt(5)=='C')
                    {
                        C.gprmc=aux;
                        rmc=true;
                    }else if(aux.charAt(4)=='G'){
                        C.gpgga=aux;
                        gga=true;
                    }else if(aux.charAt(5)=='V'){
                        C.gpgsv=aux;
                        gsv=true;
                        System.out.println(C.gpgsv);
                    }else if(aux.charAt(4)=='S'&&aux.charAt(5)=='A'){
                        C.gpgsa=aux;
                        gsa=true;
                    }
                    
                    
                    /*if(rmc && gga && gsv && gsa){
                        System.out.println("Entro ");
                        VF.InformacionGeneral();
                        VF.InformacionSatelites();
                        rmc=gga=gsv=gsa=false;
                        Thread.sleep(1000);
                    }*/
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
                String validador [];
                rmc=gga=gsv=gsa=false;
                while(true)
                {
                    x= conection.receiveSingleString();
                    if(!"\n".equals(x))
                    {
                        line+=x;
                        if("C".equals(x)) gp=true;
                    }else
                    {
                         if(line.charAt(5)=='C')
                            {
                                C.gprmc=line;
                                validador= line.split(",");
                                if(validador[2].equals("A")) rmc = true;
                                //Thread.sleep(5000);
                            }else if(line.charAt(4)=='G'){
                                C.gpgga=line;
                                gga=true;

                            }else if(line.charAt(5)=='V'){
                                C.gpgsv=line;
                                gsv=true;

                            }else if(line.charAt(4)=='S'&&line.charAt(5)=='A'){
                                C.gpgsa=line;
                                gsa=true;
                            }
                        line="";
                        if(rmc && gga && gsv && gsa){
                            C.VF.InformacionGeneral();
                            VF.InformacionSatelites();
                            rmc=gga=gsv=gsa=false;
                            Thread.sleep(100);
                        }
                    }
            
                }
                
            }
        } catch (Exception ex) {}
        
    }
      
}

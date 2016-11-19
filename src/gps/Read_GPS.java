/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gps;

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
    
    
    public Read_GPS(Controlador ct) throws FileNotFoundException, IOException {
        C=ct;
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
    }
    
    public void run()
    {
        String aux;
        try{
            
            while(!cola.isEmpty())
            {
                
                aux=(String) cola.peekLast();
                if(aux.charAt(5)=='C')
                {
                    C.gprmc=aux;
                    
                }
                cola.removeLast();
                cola.addFirst(aux);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Read_GPS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
}

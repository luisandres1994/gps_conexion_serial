/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gps;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Andres
 */
public class StatusBar implements MouseListener{
    
    private JPanel Status;
    private JLabel Info=new JLabel("Listo");

    StatusBar(JPanel s)
    {
        s.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.Status=s;
        this.Info.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        this.Status.add(this.Info);
        this.Status.revalidate();
        this.Status.repaint();
        
        
    }

   
    
    
    

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        settext(e.getComponent().getAccessibleContext().getAccessibleName());
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    private void settext(String Control)
    {
        if(Control!=null)
        {
            String message;
            switch(Control)
            {
                case "MapaLocalizacion":
                    message="Modulo de muestra de mapa";
                    break;
                case "Satelites":
                    message="Modulo de muestra de satelites a la vista del gps";
                    break;
                case "Informacion general":
                    message="Modulo de Informacion General";
                    break;
                case "mapagoogle":
                    message="Mapa Estatico de Google Maps";
                    break;
                case "Escala:":
                    message="Escala";
                    break;
                case "EscalaSlider":
                    message="Escala";
                    break;
                case "ZoomSlider":
                    message="Zoom";
                    break;
                case "Zoom:":
                    message="Zoom";
                    break;
                case "Formato":
                    message="Formato";
                    break;
                case "Formato:":
                    message="Formato";
                    break;
                case "Tipo de Mapa:":
                    message="Tipo de mapa";
                    break;
                case "TypeMapa":
                    message="Tipo de mapa a generar";
                    break;
                case "Recargar mapa":
                    message="Generar mapa con las propiedades arriba seleccionadas";
                    break;
                case "Direccion":
                    message="Direccion completa del punto posicionado";
                    break;
                case "direcciones":
                    message="Lista de direcciones acortadas de la direccion encontrada";
                    break;
                
                default:
                message="Listo";
            }
            this.Info.setText(message);
        }
        
    }
    
}

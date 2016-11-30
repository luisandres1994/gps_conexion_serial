/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gps;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import maps.java.*;
/**
 *
 * @author Andres
 */
public class VentanaFinal extends javax.swing.JFrame {

    /**
     * Creates new form VentanaFinal
     */
    public static void error(String funcionError){
        System.err.println("Algo ocurrió, no se pudo ejecutar la función: " + funcionError);
    }
    
    private Image imagen;
    private StaticMaps Map;
    private Geocoding direcciones;
    private Elevation elevacion;
    private Controlador C;
    private int escala,zoom;
    private StatusBar bar;
    private javax.swing.JLabel fondo,fondo2,fondo3;
    public VentanaFinal(Controlador Ct) {
        C=Ct;
        imagen=null;
        escala=1;
        zoom=12;
        Map= new StaticMaps();
        fondo  = new javax.swing.JLabel();
        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo2.png")));
        fondo3  = new javax.swing.JLabel();
        fondo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo4.png")));
        fondo2  = new javax.swing.JLabel();
        fondo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo5.png")));
        initComponents();
        capturareventos();
        this.setVisible(true);
        //InformacionSatelites();
        //InformacionGeneral();
        fondo.setFocusable(false);
        fondo.setSize(jPanel2.getSize());
        fondo3.setFocusable(false);
        fondo3.setSize(jPanel4.getSize());
        fondo2.setFocusable(false);
        fondo2.setSize(jPanel3.getSize());
        jPanel2.add(fondo).setLocation(0, 0);
        jPanel4.add(fondo3).setLocation(0, 0);
        jPanel3.add(fondo2);
        
    }
    
    private void capturareventos()
    {
        bar = new StatusBar(this.jPanel5);
        this.recorrerComponentes(jPanel1.getComponents());
        this.recorrerComponentes(jTabbedPane1.getComponents());
        this.recorrerComponentes(jPanel2.getComponents());
        this.recorrerComponentes(jPanel4.getComponents());
    }
    
    private void recorrerComponentes(Component[] componentes){
        for(int i=0; i<componentes.length;i++){ 
            componentes[i].addMouseListener(bar);
        }
    }

   
    
    
    public void hora(String h)
    {
        String hh = h.substring(0, 2);
        HH.setText(hh);
        int ih= Integer.parseInt(HH.getText());
        if(ih>4)
        {ih-=4;}
        else
        {ih=24+ih; ih-=4;}
        if(ih>12)
        {
            ih-=12;
            jLabel51.setText("PM");
        }else jLabel51.setText("AM");
        HH1.setText(String.valueOf(ih));
        MM.setText(h.substring(2, 4));
        MM1.setText(h.substring(2, 4));
        SS.setText(h.substring(4, 6));
        SS1.setText(h.substring(4, 6));
    }
    
    public void fecha(String f)
    {
        String meses []= {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        int finmes [] = {31,28,31,30,31,30,31,31,30,31,30,31};
        DIA.setText(f.substring(0, 2));
        int mes = Integer.parseInt(f.substring(2, 4));
        MES.setText(meses[mes-1]);
        Anno.setText("20"+f.substring(4, 6));
        int dia = Integer.parseInt(DIA.getText());
        if(dia==1)
        {
            MES.setText(meses[mes-2]);
            DIA.setText(Integer.toString(finmes[mes-2]));
            if("Diciembre".equals(MES.getText()))
            {
                int a = Integer.parseInt(Anno.getText());
                a -=1;
                Anno.setText(Integer.toString(a));
            }
        }
    }
    
    
    public void InformacionGeneral(){ 
        C.codificar();
        String[] dataGGA=C.getDataGGA();
        String[] dataGSA=C.getDataGSA();
        String[] dataRMC=C.getDataRMC();
        
        if("A".equals(dataRMC[2]))
        {
            DatosValidos.setText("Datos válidos");
            botonact.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/boton2.png")));
            jLabel43.setForeground(new java.awt.Color(0, 0, 0));
            jLabel43.setText("Activo");
        }
        else
            DatosValidos.setText("Datos invalidos");
        
       
        fecha(dataRMC[9]);
        hora(dataRMC[1]);
        CalidadGPS.setText(dataGGA[6]);
        Latitud.setText(dataRMC[3]);
        if(dataRMC[4]=="N")
            NoS.setText("Norte");
        else
           NoS.setText("Sur");
        Longitud.setText(dataRMC[5]);
        if(dataRMC[6]=="E")
           EoO.setText("Este");
        else
           EoO.setText("Oeste");
        Altitud.setText(dataGGA[9]+" Mts");
        HOPD.setText(dataGGA[8]);
        Velocidad.setText(dataRMC[7]+ " Km.h.");
        Rumbo.setText(dataRMC[8]);
        VariacionMagnetica.setText(dataRMC[10]);
        NroSatelites.setText(dataGGA[7]);
    }
    
    
    public void dataconexion()
    {
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText(C.puerto);
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText(C.baudios);
    }
    private StaticMaps.Format seleccionarFormato(){
        StaticMaps.Format formato= StaticMaps.Format.png;
        switch(Formato.getSelectedItem().toString()){
            case "png":
                formato= StaticMaps.Format.png;
                break;
            case "png32":
                formato= StaticMaps.Format.png32;
                break;
            case "gif":
                formato= StaticMaps.Format.gif;
                break;
            case "jpg":
                formato= StaticMaps.Format.jpg;
                break;
            case "jpg_baseline":
                formato= StaticMaps.Format.jpg_baseline;
                break;
        }
        return formato;
    }
    
    private StaticMaps.Maptype seleccionarTipoMapa(){
        StaticMaps.Maptype tipoMapa= StaticMaps.Maptype.roadmap;
        switch(TypeMapa.getSelectedItem().toString()){
            case "roadmap":
                tipoMapa= StaticMaps.Maptype.roadmap;
                break;
            case "satellite":
                tipoMapa= StaticMaps.Maptype.satellite;
                break;
            case "hybrid":
                tipoMapa= StaticMaps.Maptype.hybrid;
                break;
            case "terrain":
                tipoMapa= StaticMaps.Maptype.terrain;
                break;
        }
        return tipoMapa;
    }
    
    public void cargarmapa()
    {
      try {
            //C.codificar();
            String latitud =C.get_latitud();
            String longitud =C.get_longitud();
            imagen= Map.getStaticMap(latitud+","+longitud, zoom, new Dimension (600,600),escala,
                    this.seleccionarFormato(), this.seleccionarTipoMapa());

            Mapa_Google.setText(null);
            ImageIcon imgIcon=new ImageIcon(imagen);
            Icon iconImage=(Icon)imgIcon;
            Mapa_Google.setText(null);
            Mapa_Google.setIcon(iconImage);
            cargardirecciones(latitud,longitud);
            cargarelevacion(latitud,longitud);
        
        }catch(MalformedURLException | UnsupportedEncodingException e){}
    }
    public void cargarelevacion(String latitud, String longitud)
    {
        try {
            elevacion = new Elevation();
            double resultado = elevacion.getElevation(Double.valueOf(latitud), Double.valueOf(longitud));
            Elevacion.setText(String.valueOf(resultado));
            resolucion.setText(String.valueOf(elevacion.getResolution()));
        }catch(MalformedURLException e){}
    }
    
    public void cargardirecciones(String latitud,String longitud)
    {
        try {
            direcciones = new Geocoding();
            ArrayList <String> resultado = direcciones.getAddress(Double.valueOf(latitud), Double.valueOf(longitud));
            if(resultado.size()>0)
            {
                DireccionText.setText("");
                DireccionText.setText(resultado.get(0));
                Postal.setText(direcciones.getPostalcode());
                DefaultListModel listModel = new DefaultListModel();
                for(int i=0; i<resultado.size(); i++) 
                {
                    listModel.add(i, resultado.get(i));
                }
                Direcciones.setModel(listModel);
        }
        }catch(MalformedURLException | UnsupportedEncodingException e){}
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        botonact = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        HH = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        MM = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        SS = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        HH1 = new javax.swing.JLabel();
        MM1 = new javax.swing.JLabel();
        SS1 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        DIA = new javax.swing.JLabel();
        DIA1 = new javax.swing.JLabel();
        MES = new javax.swing.JLabel();
        DIA2 = new javax.swing.JLabel();
        Anno = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        CalidadGPS = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        DatosValidos = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        NroSatelites = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Latitud = new javax.swing.JTextField();
        NoS = new javax.swing.JTextField();
        Longitud = new javax.swing.JTextField();
        EoO = new javax.swing.JTextField();
        Altitud = new javax.swing.JTextField();
        HOPD = new javax.swing.JTextField();
        Velocidad = new javax.swing.JTextField();
        Rumbo = new javax.swing.JTextField();
        VariacionMagnetica = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Mapa_Google = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        EscalaSlider = new javax.swing.JSlider();
        EscalaText = new javax.swing.JTextField();
        Jlabel2 = new javax.swing.JLabel();
        ZoomSlider = new javax.swing.JSlider();
        ZoomText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Formato = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        TypeMapa = new javax.swing.JComboBox();
        MapaRecargar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Direcciones = new javax.swing.JList();
        jLabel5 = new javax.swing.JLabel();
        Postal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        Elevacion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        resolucion = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        DireccionText = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Explo GPS");
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(823, 613));
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Fecha:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Hora:");

        botonact.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/boton.png"))); // NOI18N

        jLabel43.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 51, 51));
        jLabel43.setText("Sin conexión");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setText("Status:");

        HH.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        HH.setText("12");

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel46.setText(":");

        MM.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        MM.setText("59");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel48.setText(":");

        SS.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SS.setText("59");

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel45.setText("(UTC/GMT)");

        HH1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        HH1.setText("12");

        MM1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        MM1.setText("59");

        SS1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        SS1.setText("59");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel49.setText(":");

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel50.setText(":");

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel47.setText("(UTC/GMT)-4");

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel51.setText("AM");

        DIA.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        DIA.setText("31");

        DIA1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        DIA1.setText("-");

        MES.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        MES.setText("Diciembre");

        DIA2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        DIA2.setText("-");

        Anno.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Anno.setText("2000");

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Calidad del GPS:");

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("Informacion General del Dispositivo");

        CalidadGPS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CalidadGPS.setText("0");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Datos válidos:");

        DatosValidos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DatosValidos.setText("No validos");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("Nro de satelites:");

        NroSatelites.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        NroSatelites.setText("Sin satelites");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Puerto de Conexion:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Baudios:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 255));
        jLabel15.setText("Ninguno Modo Prueba");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 255));
        jLabel16.setText("Ninguno Modo Prueba");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(NroSatelites))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(DatosValidos))
                    .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CalidadGPS, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(CalidadGPS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DatosValidos))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NroSatelites))
                .addGap(30, 30, 30)
                .addComponent(jLabel13)
                .addGap(13, 13, 13)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Latitud.setEditable(false);
        Latitud.setText("0000.000");
        Latitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LatitudActionPerformed(evt);
            }
        });

        NoS.setEditable(false);
        NoS.setText("Desconocida");

        Longitud.setEditable(false);
        Longitud.setText("0000.000");

        EoO.setEditable(false);
        EoO.setText("Desconocida");
        EoO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EoOActionPerformed(evt);
            }
        });

        Altitud.setEditable(false);
        Altitud.setText("0 mts");
        Altitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AltitudActionPerformed(evt);
            }
        });

        HOPD.setEditable(false);

        Velocidad.setEditable(false);
        Velocidad.setText("0.00 Km.h.");
        Velocidad.setToolTipText("");
        Velocidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VelocidadActionPerformed(evt);
            }
        });

        Rumbo.setEditable(false);
        Rumbo.setText("Desconocido");
        Rumbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RumboActionPerformed(evt);
            }
        });

        VariacionMagnetica.setEditable(false);
        VariacionMagnetica.setText("0");
        VariacionMagnetica.setToolTipText("");
        VariacionMagnetica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VariacionMagneticaActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Informacion General");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Precision Relativa de la posicion Horizontal:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(NoS, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Latitud, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Velocidad, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Altitud, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Rumbo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(EoO, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Longitud, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(VariacionMagnetica, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addGap(46, 46, 46)
                        .addComponent(HOPD, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Latitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Velocidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NoS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(VariacionMagnetica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(145, 145, 145))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(Longitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(EoO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(Altitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Rumbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)))
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(HOPD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addContainerGap())))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DIA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DIA1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MES)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(DIA2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Anno))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(HH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel45)
                        .addGap(49, 49, 49)
                        .addComponent(HH1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MM1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel50)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SS1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel51)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel47))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel43))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(botonact, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(103, 103, 103))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(jLabel11)
                            .addComponent(HH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MM, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SS, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45)
                            .addComponent(HH1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MM1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SS1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel47)
                            .addComponent(jLabel51))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(DIA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DIA1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MES)
                            .addComponent(DIA2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Anno)
                            .addComponent(jLabel43)))
                    .addComponent(botonact, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Informacion general", jPanel2);

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(420, 414));

        Mapa_Google.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Mapa_Google.setText("Mapa estatico");
        jScrollPane1.setViewportView(Mapa_Google);
        Mapa_Google.getAccessibleContext().setAccessibleName("mapagoogle");
        Mapa_Google.getAccessibleContext().setAccessibleDescription("");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Escala:");

        EscalaSlider.setMaximum(2);
        EscalaSlider.setMinimum(1);
        EscalaSlider.setValue(1);
        EscalaSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                EscalaSliderStateChanged(evt);
            }
        });

        EscalaText.setEditable(false);
        EscalaText.setBackground(new java.awt.Color(255, 255, 255));
        EscalaText.setText("1");

        Jlabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        Jlabel2.setText("Zoom:");

        ZoomSlider.setMaximum(20);
        ZoomSlider.setMinimum(1);
        ZoomSlider.setValue(11);
        ZoomSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ZoomSliderStateChanged(evt);
            }
        });

        ZoomText.setEditable(false);
        ZoomText.setBackground(new java.awt.Color(255, 255, 255));
        ZoomText.setText("12");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Formato:");

        Formato.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "png", "png32", "jpg", "jpg_baseline", "gif" }));
        Formato.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Tipo de Mapa:");

        TypeMapa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "roadmap", "satelite", "hybrid", "terrain" }));

        MapaRecargar.setText("Recargar mapa");
        MapaRecargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MapaRecargarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Direccion:");

        jScrollPane2.setViewportView(Direcciones);
        Direcciones.getAccessibleContext().setAccessibleName("direcciones");
        Direcciones.getAccessibleContext().setAccessibleDescription("");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Codigo Postal:");

        Postal.setEditable(false);
        Postal.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("Elevacion (m):");

        Elevacion.setEditable(false);
        Elevacion.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setText("Resolución (M):");

        resolucion.setEditable(false);
        resolucion.setBackground(new java.awt.Color(255, 255, 255));

        DireccionText.setEditable(false);
        DireccionText.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(DireccionText);
        DireccionText.getAccessibleContext().setAccessibleName("Direccion");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addComponent(Postal)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Elevacion, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(resolucion, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Jlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(EscalaSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EscalaText, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(ZoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ZoomText, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TypeMapa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Formato, 0, 0, Short.MAX_VALUE)))
                            .addComponent(jScrollPane3)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(MapaRecargar, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Postal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(Elevacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(resolucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EscalaSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EscalaText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Jlabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ZoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ZoomText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Formato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(TypeMapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(MapaRecargar)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.getAccessibleContext().setAccessibleName("mapagoogle");
        jScrollPane1.getAccessibleContext().setAccessibleDescription("");
        EscalaSlider.getAccessibleContext().setAccessibleName("EscalaSlider");
        ZoomSlider.getAccessibleContext().setAccessibleName("ZoomSlider");
        ZoomText.getAccessibleContext().setAccessibleName("ZoomText");
        Formato.getAccessibleContext().setAccessibleName("Formato");
        TypeMapa.getAccessibleContext().setAccessibleName("TypeMapa");
        jScrollPane2.getAccessibleContext().setAccessibleName("direcciones");
        jScrollPane2.getAccessibleContext().setAccessibleDescription("");
        Postal.getAccessibleContext().setAccessibleName("Postal");
        Elevacion.getAccessibleContext().setAccessibleName("Elevacion");
        resolucion.getAccessibleContext().setAccessibleName("resolucion");
        jScrollPane3.getAccessibleContext().setAccessibleName("Direccion");
        jScrollPane3.getAccessibleContext().setAccessibleDescription("");

        jTabbedPane1.addTab("Mapa localizacion", jPanel4);
        jPanel4.getAccessibleContext().setAccessibleName("MapaLocalizacion");
        jPanel4.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setFocusable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void MapaRecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MapaRecargarActionPerformed
        cargarmapa();
    }//GEN-LAST:event_MapaRecargarActionPerformed

    private void ZoomSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ZoomSliderStateChanged
        // TODO add your handling code here:
        zoom = ZoomSlider.getValue();
        ZoomText.setText(String.valueOf(zoom));
    }//GEN-LAST:event_ZoomSliderStateChanged

    private void EscalaSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_EscalaSliderStateChanged
        // TODO add your handling code here:
        escala = EscalaSlider.getValue();
        EscalaText.setText(String.valueOf(escala));
    }//GEN-LAST:event_EscalaSliderStateChanged

    private void RumboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RumboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RumboActionPerformed

    private void VariacionMagneticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VariacionMagneticaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VariacionMagneticaActionPerformed

    private void VelocidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VelocidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VelocidadActionPerformed

    private void LatitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LatitudActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LatitudActionPerformed

    private void EoOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EoOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EoOActionPerformed

    private void AltitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AltitudActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AltitudActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Altitud;
    private javax.swing.JLabel Anno;
    private javax.swing.JLabel CalidadGPS;
    private javax.swing.JLabel DIA;
    private javax.swing.JLabel DIA1;
    private javax.swing.JLabel DIA2;
    private javax.swing.JLabel DatosValidos;
    private javax.swing.JTextField DireccionText;
    private javax.swing.JList Direcciones;
    private javax.swing.JTextField Elevacion;
    private javax.swing.JTextField EoO;
    private javax.swing.JSlider EscalaSlider;
    private javax.swing.JTextField EscalaText;
    private javax.swing.JComboBox Formato;
    private javax.swing.JLabel HH;
    private javax.swing.JLabel HH1;
    private javax.swing.JTextField HOPD;
    private javax.swing.JLabel Jlabel2;
    private javax.swing.JTextField Latitud;
    private javax.swing.JTextField Longitud;
    private javax.swing.JLabel MES;
    private javax.swing.JLabel MM;
    private javax.swing.JLabel MM1;
    private javax.swing.JButton MapaRecargar;
    private javax.swing.JLabel Mapa_Google;
    private javax.swing.JTextField NoS;
    private javax.swing.JLabel NroSatelites;
    private javax.swing.JTextField Postal;
    private javax.swing.JTextField Rumbo;
    private javax.swing.JLabel SS;
    private javax.swing.JLabel SS1;
    private javax.swing.JComboBox TypeMapa;
    private javax.swing.JTextField VariacionMagnetica;
    private javax.swing.JTextField Velocidad;
    private javax.swing.JSlider ZoomSlider;
    private javax.swing.JTextField ZoomText;
    private javax.swing.JLabel botonact;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField resolucion;
    // End of variables declaration//GEN-END:variables
}

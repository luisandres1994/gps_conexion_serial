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
    public VentanaFinal(Controlador Ct) {
        C=Ct;
        imagen=null;
        escala=1;
        zoom=12;
        Map= new StaticMaps();
        initComponents();
        capturareventos();
        //InformacionGeneral();
        this.setVisible(true);

        //jPanel2.setBackground(Color.BLACK);

        
    }
    
    private void capturareventos()
    {
        bar = new StatusBar(this.jPanel5);
        this.recorrerComponentes(jPanel1.getComponents());
        this.recorrerComponentes(jTabbedPane1.getComponents());
        this.recorrerComponentes(jPanel2.getComponents());
        this.recorrerComponentes(jPanel3.getComponents());
        this.recorrerComponentes(jPanel4.getComponents());
    }
    
    private void recorrerComponentes(Component[] componentes){
        for(int i=0; i<componentes.length;i++){ 
            componentes[i].addMouseListener(bar);
        }
    }
    

    public void InformacionGeneral(){
        
        C.codificar();      
        String[] dataGGA=C.getDataGGA();
        String[] dataGSA=C.getDataGSA();
        String[] dataRMC=C.getDataRMC();
        if("A".equals(dataRMC[2]))
            DatosValidos.setText("Datos válidos");
        else
            DatosValidos.setText("Datos invalidos");
        Fecha.setText(dataRMC[9]);
        Hora.setText(dataRMC[1]);
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
        Altitud.setText(dataGGA[9]);
        HOPD.setText(dataGGA[8]);
        Velocidad.setText(dataRMC[7]);
        Rumbo.setText(dataRMC[8]);
        VariacionMagnetica.setText(dataRMC[10]);
        NroSatelites.setText(dataGGA[7]);
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
        DatosValidos = new javax.swing.JTextField();
        CalidadGPS = new javax.swing.JTextField();
        Fecha = new javax.swing.JTextField();
        Hora = new javax.swing.JTextField();
        Latitud = new javax.swing.JTextField();
        Longitud = new javax.swing.JTextField();
        Altitud = new javax.swing.JTextField();
        HOPD = new javax.swing.JTextField();
        Velocidad = new javax.swing.JTextField();
        VariacionMagnetica = new javax.swing.JTextField();
        NroSatelites = new javax.swing.JTextField();
        Rumbo = new javax.swing.JTextField();
        EoO = new javax.swing.JTextField();
        NoS = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        NroSatelitesVistos = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
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
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        CalidadGPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CalidadGPSActionPerformed(evt);
            }
        });

        Latitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LatitudActionPerformed(evt);
            }
        });

        Velocidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VelocidadActionPerformed(evt);
            }
        });

        VariacionMagnetica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VariacionMagneticaActionPerformed(evt);
            }
        });

        NroSatelites.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NroSatelitesActionPerformed(evt);
            }
        });

        Rumbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RumboActionPerformed(evt);
            }
        });

        jLabel9.setText("Datsos validos");

        jLabel8.setText("Calidad del GPS");

        jLabel10.setText("Fecha");

        jLabel11.setText("Hora");

        jLabel12.setText("Latitud");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(325, 325, 325)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(NroSatelites, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                            .addComponent(Rumbo)
                            .addComponent(Velocidad)
                            .addComponent(HOPD)
                            .addComponent(EoO)
                            .addComponent(Longitud)
                            .addComponent(NoS)
                            .addComponent(Latitud)
                            .addComponent(Hora)
                            .addComponent(Fecha)
                            .addComponent(CalidadGPS)
                            .addComponent(Altitud)
                            .addComponent(VariacionMagnetica)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(40, 40, 40)
                        .addComponent(DatosValidos, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(227, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(DatosValidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CalidadGPS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Latitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NoS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Longitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EoO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Altitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(HOPD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Velocidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Rumbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(VariacionMagnetica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NroSatelites, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Informacion general", jPanel2);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextField1.setText("Numero de satelites visibles");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(475, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(NroSatelitesVistos, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)))
                .addGap(153, 153, 153))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NroSatelitesVistos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(471, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Satelites", jPanel3);

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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setFocusable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void EscalaSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_EscalaSliderStateChanged
        // TODO add your handling code here:
        escala = EscalaSlider.getValue();
        EscalaText.setText(String.valueOf(escala));
    }//GEN-LAST:event_EscalaSliderStateChanged

    private void ZoomSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ZoomSliderStateChanged
        // TODO add your handling code here:
        zoom = ZoomSlider.getValue();
        ZoomText.setText(String.valueOf(zoom));
    }//GEN-LAST:event_ZoomSliderStateChanged

    private void MapaRecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MapaRecargarActionPerformed
        cargarmapa();
    }//GEN-LAST:event_MapaRecargarActionPerformed

    private void CalidadGPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CalidadGPSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CalidadGPSActionPerformed

    private void LatitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LatitudActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_LatitudActionPerformed

    private void VelocidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VelocidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VelocidadActionPerformed

    private void VariacionMagneticaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VariacionMagneticaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VariacionMagneticaActionPerformed

    private void NroSatelitesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NroSatelitesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NroSatelitesActionPerformed

    private void RumboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RumboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RumboActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Altitud;
    private javax.swing.JTextField CalidadGPS;
    private javax.swing.JTextField DatosValidos;
    private javax.swing.JTextField DireccionText;
    private javax.swing.JList Direcciones;
    private javax.swing.JTextField Elevacion;
    private javax.swing.JTextField EoO;
    private javax.swing.JSlider EscalaSlider;
    private javax.swing.JTextField EscalaText;
    private javax.swing.JTextField Fecha;
    private javax.swing.JComboBox Formato;
    private javax.swing.JTextField HOPD;
    private javax.swing.JTextField Hora;
    private javax.swing.JLabel Jlabel2;
    private javax.swing.JTextField Latitud;
    private javax.swing.JTextField Longitud;
    private javax.swing.JButton MapaRecargar;
    private javax.swing.JLabel Mapa_Google;
    private javax.swing.JTextField NoS;
    private javax.swing.JTextField NroSatelites;
    private javax.swing.JTextField NroSatelitesVistos;
    private javax.swing.JTextField Postal;
    private javax.swing.JTextField Rumbo;
    private javax.swing.JComboBox TypeMapa;
    private javax.swing.JTextField VariacionMagnetica;
    private javax.swing.JTextField Velocidad;
    private javax.swing.JSlider ZoomSlider;
    private javax.swing.JTextField ZoomText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField resolucion;
    // End of variables declaration//GEN-END:variables
}

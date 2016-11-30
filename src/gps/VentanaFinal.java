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
        this.setVisible(true);
        //InformacionSatelites();
        //InformacionGeneral();
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

    public void InformacionSatelites(){
        C.codificar();
        String[] dataGSV=C.getDataGSV();
        NroSatelitesVista.setText(dataGSV[3]);
        ID1.setText(dataGSV[4]);
        Elevacion1.setText(dataGSV[5]);
        Azimuth1.setText(dataGSV[6]);
        RSR1.setText(dataGSV[7]);
        ID2.setText(dataGSV[8]);
        Elevacion2.setText(dataGSV[9]);
        Azimuth2.setText(dataGSV[10]);
        RSR2.setText(dataGSV[11]);
        ID3.setText(dataGSV[12]);
        Elevacion3.setText(dataGSV[13]);
        Azimuth3.setText(dataGSV[14]);
        RSR3.setText(dataGSV[15]);
        ID4.setText(dataGSV[16]);
        Elevacion4.setText(dataGSV[17]);
        Azimuth4.setText(dataGSV[18]);
        RSR4.setText(dataGSV[19]);
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
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
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
        jPanel3 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        NroSatelitesVista = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        ID1 = new javax.swing.JTextField();
        ID2 = new javax.swing.JTextField();
        ID3 = new javax.swing.JTextField();
        ID4 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        Elevacion2 = new javax.swing.JTextField();
        Elevacion1 = new javax.swing.JTextField();
        Elevacion4 = new javax.swing.JTextField();
        Elevacion3 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        Azimuth1 = new javax.swing.JTextField();
        Azimuth2 = new javax.swing.JTextField();
        RSR3 = new javax.swing.JTextField();
        Azimuth4 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        RSR1 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        RSR2 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        Azimuth3 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        RSR4 = new javax.swing.JTextField();
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

        jLabel9.setText("Datos válidos");

        jLabel8.setText("Calidad del GPS");

        jLabel10.setText("Fecha");

        jLabel11.setText("Hora");

        jLabel12.setText("Latitud");

        jLabel13.setText("Dirección Latitud");

        jLabel14.setText("Longitud");

        jLabel15.setText("Dirección Longitud");

        jLabel16.setText("Altitud con respecto al mar");

        jLabel17.setText("Precision relativa de la horizontal ");

        jLabel18.setText("Velocidad sobre la tierra en nudos");

        jLabel19.setText("Rumbo en grados con respecto a la horizontal");

        jLabel20.setText("Variación Magnetica");

        jLabel21.setText("Nro de satelites ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(264, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DatosValidos, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel18))
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
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
                            .addComponent(VariacionMagnetica))))
                .addContainerGap(182, Short.MAX_VALUE))
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NoS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Longitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EoO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Altitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(HOPD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Velocidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Rumbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VariacionMagnetica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NroSatelites, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addContainerGap(119, Short.MAX_VALUE))
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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
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

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel22.setText("Nro de satelites a la vista");

        NroSatelitesVista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NroSatelitesVistaActionPerformed(evt);
            }
        });

        jLabel23.setText("SATELITE 1");

        jLabel24.setText("SATELITE 1");

        jLabel25.setText("SATELITE 3");

        jLabel26.setText("SATELITE 2");

        jLabel27.setText("Identificador");

        jLabel28.setText("Identificador");

        jLabel29.setText("Identificador");

        jLabel30.setText("Identificador");

        ID1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ID1ActionPerformed(evt);
            }
        });

        jLabel31.setText("Elevación");

        jLabel32.setText("Elevación");

        jLabel33.setText("Elevación");

        jLabel34.setText("Elevación");

        Elevacion2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Elevacion2ActionPerformed(evt);
            }
        });

        Elevacion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Elevacion1ActionPerformed(evt);
            }
        });

        Elevacion4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Elevacion4ActionPerformed(evt);
            }
        });

        Elevacion3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Elevacion3ActionPerformed(evt);
            }
        });

        jLabel35.setText("Azimuth");

        jLabel36.setText("Azimuth");

        jLabel37.setText("Azimuth");

        jLabel38.setText("Azimuth");

        Azimuth1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Azimuth1ActionPerformed(evt);
            }
        });

        Azimuth2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Azimuth2ActionPerformed(evt);
            }
        });

        RSR3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RSR3ActionPerformed(evt);
            }
        });

        Azimuth4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Azimuth4ActionPerformed(evt);
            }
        });

        jLabel39.setText("Relación señal a ruido");

        RSR1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RSR1ActionPerformed(evt);
            }
        });

        jLabel40.setText("Relación señal a ruido");

        RSR2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RSR2ActionPerformed(evt);
            }
        });

        jLabel41.setText("Relación señal a ruido");

        Azimuth3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Azimuth3ActionPerformed(evt);
            }
        });

        jLabel42.setText("Relación señal a ruido");

        RSR4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RSR4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel23)
                        .addGap(284, 284, 284)
                        .addComponent(jLabel25)
                        .addGap(151, 151, 151)
                        .addComponent(jLabel24))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel31)
                                        .addGap(274, 274, 274))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addGap(258, 258, 258)))
                                .addComponent(jLabel22)
                                .addGap(27, 27, 27))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addGap(273, 273, 273)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Elevacion2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Azimuth2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel33)
                                    .addComponent(jLabel29))))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NroSatelitesVista, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Elevacion3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(Azimuth3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(ID3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(Elevacion4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel36)
                                            .addComponent(jLabel28))
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(53, 53, 53)
                                                .addComponent(Azimuth4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(ID4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel39)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Azimuth1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel38))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Elevacion1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel34))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(ID1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel30)
                                .addGap(47, 47, 47)
                                .addComponent(ID2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel26)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(RSR1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel40)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RSR2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RSR3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(RSR4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(NroSatelitesVista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(ID1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ID2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ID3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ID4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)
                    .addComponent(jLabel32)
                    .addComponent(Elevacion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Elevacion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Elevacion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Elevacion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel38)
                    .addComponent(jLabel37)
                    .addComponent(jLabel36)
                    .addComponent(Azimuth4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Azimuth2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Azimuth1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Azimuth3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(RSR1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(RSR2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(RSR3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(RSR4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(326, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Satelites", jPanel3);

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

    private void NroSatelitesVistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NroSatelitesVistaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NroSatelitesVistaActionPerformed

    private void ID1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ID1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ID1ActionPerformed

    private void Elevacion2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Elevacion2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Elevacion2ActionPerformed

    private void Elevacion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Elevacion1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Elevacion1ActionPerformed

    private void Elevacion4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Elevacion4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Elevacion4ActionPerformed

    private void Elevacion3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Elevacion3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Elevacion3ActionPerformed

    private void Azimuth1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Azimuth1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Azimuth1ActionPerformed

    private void Azimuth2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Azimuth2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Azimuth2ActionPerformed

    private void RSR3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RSR3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RSR3ActionPerformed

    private void Azimuth4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Azimuth4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Azimuth4ActionPerformed

    private void RSR1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RSR1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RSR1ActionPerformed

    private void RSR2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RSR2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RSR2ActionPerformed

    private void Azimuth3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Azimuth3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Azimuth3ActionPerformed

    private void RSR4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RSR4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RSR4ActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Altitud;
    private javax.swing.JTextField Azimuth1;
    private javax.swing.JTextField Azimuth2;
    private javax.swing.JTextField Azimuth3;
    private javax.swing.JTextField Azimuth4;
    private javax.swing.JTextField CalidadGPS;
    private javax.swing.JTextField DatosValidos;
    private javax.swing.JTextField DireccionText;
    private javax.swing.JList Direcciones;
    private javax.swing.JTextField Elevacion;
    private javax.swing.JTextField Elevacion1;
    private javax.swing.JTextField Elevacion2;
    private javax.swing.JTextField Elevacion3;
    private javax.swing.JTextField Elevacion4;
    private javax.swing.JTextField EoO;
    private javax.swing.JSlider EscalaSlider;
    private javax.swing.JTextField EscalaText;
    private javax.swing.JTextField Fecha;
    private javax.swing.JComboBox Formato;
    private javax.swing.JTextField HOPD;
    private javax.swing.JTextField Hora;
    private javax.swing.JTextField ID1;
    private javax.swing.JTextField ID2;
    private javax.swing.JTextField ID3;
    private javax.swing.JTextField ID4;
    private javax.swing.JLabel Jlabel2;
    private javax.swing.JTextField Latitud;
    private javax.swing.JTextField Longitud;
    private javax.swing.JButton MapaRecargar;
    private javax.swing.JLabel Mapa_Google;
    private javax.swing.JTextField NoS;
    private javax.swing.JTextField NroSatelites;
    private javax.swing.JTextField NroSatelitesVista;
    private javax.swing.JTextField Postal;
    private javax.swing.JTextField RSR1;
    private javax.swing.JTextField RSR2;
    private javax.swing.JTextField RSR3;
    private javax.swing.JTextField RSR4;
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
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
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
    private javax.swing.JTextField resolucion;
    // End of variables declaration//GEN-END:variables
}

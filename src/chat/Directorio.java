/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Directorio.java
 *
 * Created on 17-abr-2010, 16:57:18
 */

package chat;

import java.awt.Color;
import java.awt.event.*;
import java.net.UnknownHostException;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author Julio Navarro Lara
 */
public class Directorio extends javax.swing.JFrame {

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    DefaultListModel lista;
    GestionEntrada hebra;

    String nombre;

    int puertoActivo;

    ArrayList<Chat> listaConvers;

    /** Creates new form Directorio */
    public Directorio(Socket socket, String nombre, int estado, String IP) {
        lista = new DefaultListModel();
        hebra = new GestionEntrada(IP, this);

        this.setLocation(300,200);

        this.nombre = nombre;
        this.socket = socket;

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Directorio.class.getName()).log(Level.SEVERE, null, ex);
        }

        initComponents();

        hebra.start();

        nombreText.setText(nombre);
        listaEstado.setSelectedIndex(estado);

        Jlista.setModel(lista);

        Renderer renderer = new Renderer(this);
        Jlista.setCellRenderer(renderer);

        listaConvers = new ArrayList<Chat>();

        puertoActivo = 26;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nombreText = new javax.swing.JLabel();
        listaEstado = new javax.swing.JComboBox();
        botonAdd = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Jlista = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QSMS");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        nombreText.setFont(new java.awt.Font("Tahoma", 0, 18));
        nombreText.setText("jLabel1");

        listaEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Conectado", "Ocupado", "No Conectado" }));
        listaEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listaEstadoActionPerformed(evt);
            }
        });

        botonAdd.setText("Añadir amigo");
        botonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAddActionPerformed(evt);
            }
        });

        Jlista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JlistaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Jlista);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .addComponent(botonAdd)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(nombreText, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(listaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreText)
                    .addComponent(listaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonAdd)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listaEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listaEstadoActionPerformed
        // TODO add your handling code here:
        String cadena = "CAMBIO ESTADO,"+listaEstado.getSelectedIndex();
        out.println(cadena);

        switch(listaEstado.getSelectedIndex()){
            case 0: nombreText.setForeground(Color.GREEN);
                    break;
            case 1: nombreText.setForeground(Color.RED);
                    break;
            case 2: nombreText.setForeground(Color.DARK_GRAY);
                    break;
        }
    }//GEN-LAST:event_listaEstadoActionPerformed

    private void botonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAddActionPerformed

        String s = (String)JOptionPane.showInputDialog(this,"Introduzca el nombre del nuevo contacto:",
                            "Nuevo contacto",JOptionPane.PLAIN_MESSAGE,null,null,null);

        if(buscarAmigo(s)!=-1){
            JOptionPane.showMessageDialog(this, "Ya has agregado al usuario",
                    "Contacto ya disponible", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //If a string was returned, say so.
        if ((s != null) && (s.length() > 0)) {
            out.println("ADD CONTACTO,"+s);
            try {
                String s2 = in.readLine();

                StringTokenizer s3 = new StringTokenizer(s2,",");
                String s4 = s3.nextToken();
                if(s2.equals("NO EXISTE")){
                    JOptionPane.showMessageDialog(this, "El usuario no está en nuestra base de datos.",
                    "Contacto no encontrado", JOptionPane.ERROR_MESSAGE);
                }else if(s4.equals("OK")){
                    addAmigo(s,Integer.parseInt(s3.nextToken()));
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error en la conexión",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_botonAddActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       try {
            for(Chat c: listaConvers){
                c.cerrar();
            }
            out.println("CIERRE");
            hebra.cerrarFlujos();
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Directorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void JlistaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JlistaMouseClicked

        Amigo friend = (Amigo)Jlista.getSelectedValue();

        if(evt.getClickCount()==2 && friend.getEstado()!=2){
            //URL url = null;
            String IP = "";

            /*
            try {
                url = new URL("http://checkip.dyndns.org/");
            } catch (MalformedURLException ex) {
                Logger.getLogger(Directorio.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                BufferedReader entrada = new BufferedReader(new InputStreamReader(url.openStream()));

                char[] linea = entrada.readLine().toCharArray();

                for(int i=0; i<linea.length; i++){
                    if(Character.isDigit(linea[i])){
                        do{
                            IP += linea[i];
                            i++;
                        }while(Character.isDigit(linea[i])||linea[i]=='.');
                        break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Directorio.class.getName()).log(Level.SEVERE, null, ex);
            }


             *
             */

            try {
                InetAddress direccion = InetAddress.getLocalHost();
                byte[] bIP = direccion.getAddress();

                for(int i=0; i<bIP.length; i++){
                    if(i>0){
                        IP += ".";
                    }

                    IP += bIP[i] & 255;
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(Directorio.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println(IP);

            out.println("HABLAR,"+friend.getNombre()+","+IP+","+puertoActivo);

            ServerSocket serverConver;
            try {
                serverConver = new ServerSocket(puertoActivo);
                puertoActivo++;
                Socket socketEntrada = serverConver.accept();
                Socket socketSalida = serverConver.accept();
                new GestionConversacion(nombre, friend.getNombre(), socketEntrada, socketSalida, this).start();
            } catch (IOException ex) {
                Logger.getLogger(Directorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_JlistaMouseClicked

    public void addConver(Chat chat){
        listaConvers.add(chat);
    }

    public String getNombre(){
        return nombre;
    }

    Amigo getAmigo(int i){
        return (Amigo)lista.get(i);
    }

    public void addAmigo(String nombre, int estado){
        lista.addElement(new Amigo(nombre,estado));
        ordenarContactos();
    }

    public void ordenarContactos(){
        if(!lista.isEmpty()){
            ArrayList<Amigo> array=new ArrayList<Amigo>();
            for(int i=0; i<lista.size(); i++){
                array.add((Amigo)lista.get(i));
            }
            Collections.sort(array, new compararAmigos());
            for(int i=0; i<array.size(); i++){
                lista.setElementAt(array.get(i), i);
            }
        }
    }

    public int buscarAmigo(String nombre){
        if(!lista.isEmpty()){
            ArrayList<Amigo> array = new ArrayList<Amigo>();
            for(int i=0; i<lista.size(); i++){
               if(((Amigo)lista.get(i)).getNombre().equals(nombre)){
                   return i;
               }
            }
        }
        return -1;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList Jlista;
    private javax.swing.JButton botonAdd;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox listaEstado;
    private javax.swing.JLabel nombreText;
    // End of variables declaration//GEN-END:variables

}

class Amigo{
    private String nombre;
    private int estado;

    public Amigo(String nombre, int estado){
        this.nombre = nombre;
        this.estado = estado;
    }

    public String getNombre(){
        return nombre;
    }

    public int getEstado(){
        return estado;
    }

    public void setEstado(int i){
        estado = i;
    }
}

class compararAmigos implements Comparator<Amigo>{
    public int compare(Amigo a, Amigo b){
        if(a.getEstado()!=b.getEstado()){
            return a.getEstado()-b.getEstado();
        }else{
            return a.getNombre().compareTo(b.getNombre());
        }
   }
}

class GestionEntrada extends Thread{

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    Directorio directorio;

    String mensaje;
    StringTokenizer mensaje2;
    String IP;

    boolean salir;

    public GestionEntrada(String IP, Directorio directorio){
        this.IP = IP;
        this.directorio = directorio;
    }

    public void cerrarFlujos(){
        try {
            this.interrupt();
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(GestionEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(){
        try {
            socket = new Socket(IP, 25);

            out = new PrintWriter(socket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            mensaje = in.readLine();
            mensaje2 = new StringTokenizer(mensaje, ",");

            if(mensaje2.nextToken().equals("CONTACTOS")){
                mensaje = mensaje2.nextToken();
                while(!mensaje.equals("&&&")){
                    directorio.addAmigo(mensaje, Integer.parseInt(mensaje2.nextToken()));
                    mensaje = mensaje2.nextToken();
                }
                directorio.ordenarContactos();
            }else{
                JOptionPane.showMessageDialog(directorio, "Error en la conexión",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }


            do{
                mensaje = in.readLine();
                mensaje2 = new StringTokenizer(mensaje, ",");

                mensaje = mensaje2.nextToken();

                if(mensaje.equals("CONECTADO")){
                    int num = directorio.buscarAmigo(mensaje2.nextToken());
                    if(num!=-1){
                        directorio.getAmigo(num).setEstado(Integer.parseInt(mensaje2.nextToken()));
                        directorio.ordenarContactos();
                    }
                }else if(mensaje.equals("HABLAR")){
                    String nombreB = mensaje2.nextToken();
                    String IP = mensaje2.nextToken();
                    int puerto = Integer.parseInt(mensaje2.nextToken());
                    new GestionConversacion(directorio.getNombre(), nombreB, IP, puerto, directorio).start();
                }
            }while(!salir);
        } catch (UnknownHostException ex) {
            Logger.getLogger(GestionEntrada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class GestionConversacion extends Thread{

    ServerSocket socketServidor;
    Socket socketEntrada;
    Socket socketSalida;

    Chat chat;
    String nombreA;
    String nombreB;
    Directorio directorio;

    String mensaje = null;

    public GestionConversacion(String nombreA, String nombreB, Socket socketEntrada, 
            Socket socketSalida, Directorio directorio){
        this.nombreA = nombreA;
        this.nombreB = nombreB;
        this.socketEntrada = socketEntrada;
        this.socketSalida = socketSalida;
        this.directorio = directorio;
    }

    public GestionConversacion(String nombreA, String nombreB, String IP, int puerto, Directorio directorio){
        try {
            this.nombreA = nombreA;
            this.nombreB = nombreB;
            this.directorio = directorio;
            socketSalida = new Socket(IP, puerto);
            socketEntrada = new Socket(IP, puerto);

            PrintWriter out = new PrintWriter(socketEntrada.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socketEntrada.getInputStream()));

            mensaje = in.readLine();
            StringTokenizer cadena = new StringTokenizer(mensaje, "$|#");
            mensaje = cadena.nextToken();

            if(mensaje.equals("MENSAJE")){
                  mensaje = cadena.nextToken();
            }

        } catch (UnknownHostException ex) {
            Logger.getLogger(GestionConversacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GestionConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(){
        chat = new Chat(nombreA, nombreB, socketEntrada, socketSalida);

        directorio.addConver(chat);

        if(mensaje != null){
            chat.mostrarMensaje(nombreB, mensaje);
            new HiloSonidos();
        }
    }
}

class Renderer extends JLabel
                       implements ListCellRenderer {
    Directorio directorio;

    protected DefaultListCellRenderer rendererPorDefecto = new DefaultListCellRenderer();

    public Renderer(Directorio directorio) {
        this.directorio = directorio;
        setOpaque(true);
    }

    /*
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     */
    public Component getListCellRendererComponent(JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus) {

        if (isSelected) {
            Color colorSeleccion = new Color(0x99CCFF);
            setBackground(colorSeleccion.brighter());
        } else {
            setBackground(list.getBackground());
        }


        //Set the icon and text.  If icon was null, say so.
        String nombre = ((Amigo)value).getNombre();

        //Set the icon and text.  If icon was null, say so.
        setText("  "+nombre);

        switch(((Amigo)value).getEstado()){
            case 0: setForeground(Color.GREEN);
                    break;
            case 1: setForeground(Color.RED);
                    break;
            case 2: setForeground(Color.DARK_GRAY);
                    break;
        }

        return this;
    }
}





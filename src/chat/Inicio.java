/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Inicio.java
 *
 * Created on 02-abr-2010, 12:27:04
 */

package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.StringTokenizer;

/**
 *
 * @author Julio Navarro Lara
 */
public class Inicio extends javax.swing.JFrame {

    Registro registro;

    /** Creates new form Inicio */
    public Inicio() {
        initComponents();
        this.setLocation(300,200);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nombreLabel = new javax.swing.JLabel();
        nickText = new javax.swing.JTextField();
        passLabel = new javax.swing.JLabel();
        ipLabel = new javax.swing.JLabel();
        ipText = new javax.swing.JTextField();
        passText = new javax.swing.JPasswordField();
        botonConectar = new javax.swing.JButton();
        botonSalir = new javax.swing.JButton();
        registroLabel = new javax.swing.JLabel();
        olvidoLabel = new javax.swing.JLabel();
        iniciarComo = new javax.swing.JComboBox();
        iniciarComoLabel = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();
        manual = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("QSMS");
        setResizable(false);

        nombreLabel.setText("Nick: ");

        nickText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConectarActionPerformed(evt);
            }
        });

        passLabel.setText("Password:");

        ipLabel.setText("Dir. IP:");

        ipText.setText("127.0.0.1");
        ipText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConectarActionPerformed(evt);
            }
        });

        passText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConectarActionPerformed(evt);
            }
        });

        botonConectar.setText("Conectar");
        botonConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConectarActionPerformed(evt);
            }
        });

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        registroLabel.setText("Registrarse en QSMS");
        registroLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        registroLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registroLabelMouseClicked(evt);
            }
        });

        olvidoLabel.setText("He olvidado mi password");
        olvidoLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        olvidoLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                olvidoLabelMouseClicked(evt);
            }
        });

        iniciarComo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Conectado", "Ocupado", "No Conectado" }));

        iniciarComoLabel.setText("Iniciar como:");

        infoLabel.setForeground(new java.awt.Color(255, 0, 0));

        manual.setForeground(new java.awt.Color(0, 0, 102));
        manual.setText("Manual de Usuario");
        manual.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manualMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nombreLabel)
                                    .addComponent(passLabel)
                                    .addComponent(ipLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nickText)
                                    .addComponent(passText)
                                    .addComponent(ipText, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(infoLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(iniciarComoLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(iniciarComo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(registroLabel)
                                    .addComponent(olvidoLabel)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(botonConectar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(10, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(66, Short.MAX_VALUE)
                .addComponent(manual)
                .addGap(63, 63, 63))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombreLabel)
                    .addComponent(nickText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passLabel)
                    .addComponent(passText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipLabel)
                    .addComponent(ipText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonConectar)
                    .addComponent(botonSalir))
                .addGap(11, 11, 11)
                .addComponent(registroLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(olvidoLabel)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iniciarComo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iniciarComoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(manual)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registroLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registroLabelMouseClicked
        // TODO add your handling code here:
        registro = new Registro(this);
        registro.setLocationRelativeTo(this);
        this.setEnabled(false);
        registro.setVisible(true);
        
    }//GEN-LAST:event_registroLabelMouseClicked

    private void olvidoLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_olvidoLabelMouseClicked
        PrintWriter out = null;
        try {
            String ip = ipText.getText();
            String nick = nickText.getText();
            if (ip.isEmpty() || nick.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe introducir la IP del servidor y su nick", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Socket socket = new Socket(ip, 24);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensaje = in.readLine();
            if (!mensaje.equals("CONECTADO")) {
                JOptionPane.showMessageDialog(this, "Error en la conexión", "Error", JOptionPane.ERROR_MESSAGE);
                out.close();
                in.close();
                socket.close();
                return;
            }
            out.println("OLVIDO," + nick);
            String s = in.readLine();
            
            if(s.equals("NO EXISTE")){
                JOptionPane.showMessageDialog(this, "No existe un usuario con ese nombre", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                out.close();
                in.close();
                socket.close();
                return;
            }
            
            String s2 = (String) JOptionPane.showInputDialog(this,s,
                            "Responda",JOptionPane.PLAIN_MESSAGE,null,null,null);
            //If a string was returned, say so.
            if ((s2 != null) && (s2.length() > 0)) {
                out.println(s2);
                s = in.readLine();
                StringTokenizer st = new StringTokenizer(s,",");
                s = st.nextToken();
                if (s.equals("BIEN")){
                    JOptionPane.showMessageDialog(this, "La contraseña es: "+st.nextToken(),
                            "Contraseña", JOptionPane.INFORMATION_MESSAGE);
                }else if(s.equals("MAL")){
                    JOptionPane.showMessageDialog(this, "Error en la introducción de la respuesta",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this, "Error en la conexión",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                    JOptionPane.showMessageDialog(this, "Error en la respuesta",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    out.println("ERROR");
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la conexión", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_olvidoLabelMouseClicked

    private void botonConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConectarActionPerformed
        String ip = ipText.getText();
        String nick = nickText.getText();
        String pass = new String(passText.getPassword());
        
        if(nick.isEmpty()){
            modificarInformacion("Debe introducir un nick", Color.RED);
        }else if(pass.isEmpty()){
            modificarInformacion("Debe introducir un password", Color.RED);
        }else if(ip.isEmpty()){
            modificarInformacion("Debe introducir la IP del servidor", Color.RED);
        }else{
            modificarInformacion("Conectando...", Color.BLACK);

            new HiloConexion(ip, nick, pass, this
                , iniciarComo.getSelectedIndex()).start();
        }
    }//GEN-LAST:event_botonConectarActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void manualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manualMouseClicked
        try{
              //Se ejecuta el manual en pdf correspondiente
          Runtime.getRuntime().exec("C:\\Program Files\\Adobe\\Acrobat 7.0\\Reader" +
                  "\\AcroRd32.exe "+new File("manual.pdf").getAbsolutePath());
          }catch(IOException e){
              JOptionPane.showMessageDialog(this, "Error en la apertura del manual", "Error", JOptionPane.ERROR_MESSAGE);
          }
    }//GEN-LAST:event_manualMouseClicked

    public void modificarInformacion(String texto, Color color){
        infoLabel.setForeground(color);
        infoLabel.setText(texto);
    }

    public void cerrarRegistro(){
        this.setEnabled(true);
        registro.dispose();
    }

    public void deshabilitarBotones(){
        botonConectar.setEnabled(false);
        botonSalir.setEnabled(false);
    }

    public void habilitarBotones(){
        botonConectar.setEnabled(true);
        botonSalir.setEnabled(true);
    }

    public int getEstado(){
        return iniciarComo.getSelectedIndex();
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonConectar;
    private javax.swing.JButton botonSalir;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JComboBox iniciarComo;
    private javax.swing.JLabel iniciarComoLabel;
    private javax.swing.JLabel ipLabel;
    private javax.swing.JTextField ipText;
    private javax.swing.JLabel manual;
    private javax.swing.JTextField nickText;
    private javax.swing.JLabel nombreLabel;
    private javax.swing.JLabel olvidoLabel;
    private javax.swing.JLabel passLabel;
    private javax.swing.JPasswordField passText;
    private javax.swing.JLabel registroLabel;
    // End of variables declaration//GEN-END:variables

}

class HiloConexion extends Thread{

    Socket socket;
    PrintWriter out;
    BufferedReader in;

    String IP;
    String nombre;
    String pass;
    Inicio inicio;
    int estado;

    Directorio directorio;

    public HiloConexion(String IP, String nombre, String pass, Inicio inicio, int estado){
        this.IP = IP;
        this.nombre = nombre;
        this.pass = pass;
        this.inicio = inicio;
        this.estado = estado;
    }

    public void run(){
        try {
            inicio.deshabilitarBotones();

            socket = new Socket(IP, 24);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String mensaje = in.readLine();

            if(!mensaje.equals("CONECTADO")){
                inicio.modificarInformacion("Error al conectar", Color.RED);
                inicio.habilitarBotones();
                socket.close();
                out.close();
                in.close();
                return;
            }

            out.println("INICIO," + nombre + "," + pass+","+estado);

            mensaje = in.readLine();

            if(mensaje.equals("LOGIN")){
                directorio = new Directorio(socket, nombre, inicio.getEstado(), IP);
                directorio.setVisible(true);
                inicio.dispose();


            }else if(mensaje.equals("NO EXISTE")){
                inicio.modificarInformacion("Nombre de usuario incorrecto", Color.RED);
            }else if(mensaje.equals("CLAVE INCORRECTA")){
                inicio.modificarInformacion("Contraseña incorrecta", Color.RED);
            }
        } catch (UnknownHostException ex) {
            inicio.modificarInformacion("Error en la conexión", Color.RED);
        } catch (IOException ex) {
            inicio.modificarInformacion("Error en la conexión", Color.RED);
        }

        inicio.habilitarBotones();
    }
}
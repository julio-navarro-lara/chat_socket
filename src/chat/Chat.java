/*
 * Chat.java
 *
 * Created on 28-abr-2010, 17:25:14
 *
 */

package chat;

import java.awt.Color;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import javax.swing.text.*;

/**
 *
 * @author Julio Navarro Lara
 */
public class Chat extends javax.swing.JFrame {

    Socket socket;

    PrintWriter out;
    BufferedReader in;

    SimpleAttributeSet att;

    String nombreA;
    String nombreB;

    boolean conectado;

    public Chat(String nombreA, String nombreB, Socket socketEntrada, Socket socketSalida){

       this.setLocation(310,210);

       this.socket = socketSalida;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }

       new entradaMensajes(this, socketEntrada).start();

       initComponents();

       this.nombreA = nombreA;
       this.nombreB = nombreB;
       this.setTitle(nombreB);

       att = new SimpleAttributeSet();

       conectado = true;
       //Detectar minimizada, iconified
       this.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mensajeArea = new javax.swing.JTextPane();
        mensajeField = new javax.swing.JTextField();
        enviar = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mensajeArea.setEditable(false);
        jScrollPane1.setViewportView(mensajeArea);

        mensajeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarActionPerformed(evt);
            }
        });

        enviar.setText("Enviar");
        enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(mensajeField, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(enviar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mensajeField)
                    .addComponent(enviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        try {
            if(conectado){
                desconectar();
            }
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    public void cerrar(){
        this.formWindowClosing(null);
    }

    public void desconectar(){
        out.println("DESCONEXION");
    }

    private void enviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarActionPerformed

        String mensaje = mensajeField.getText();
        mostrarMensaje(nombreA, mensaje);

        mensajeField.setText("");

        if(conectado){
            out.println("MENSAJE$|#"+mensaje);
        }
    }//GEN-LAST:event_enviarActionPerformed

    public void mostrarMensaje(String nombre, String mensaje){

        if(nombre.equals("DESCONEXION")){
            try {
                StyleConstants.setForeground(att, Color.RED);
                mensajeArea.getStyledDocument().insertString(mensajeArea.getStyledDocument().getLength(), "<DESCONECTADO>\n", att);
                StyleConstants.setForeground(att, Color.LIGHT_GRAY);
                mensajeArea.getStyledDocument().insertString(mensajeArea.getStyledDocument().getLength(), "(Puede cerrar la ventana)\n", att);
                conectado = false;
                return;
            } catch (BadLocationException ex) {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            StyleConstants.setBold(att, true);
            mensajeArea.getStyledDocument().insertString(mensajeArea.getStyledDocument().getLength(),
                    nombre+": " , att);
            StyleConstants.setBold(att, false);
            mensajeArea.getStyledDocument().insertString(mensajeArea.getStyledDocument().getLength(),
                    mensaje+"\n" , att);
        } catch (BadLocationException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getNombreB(){
        return nombreB;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton enviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane mensajeArea;
    private javax.swing.JTextField mensajeField;
    // End of variables declaration//GEN-END:variables

}

class entradaMensajes extends Thread{

    Chat chat;
    Socket socket;

    PrintWriter out;
    BufferedReader in;

    boolean salir;

    public entradaMensajes(Chat chat, Socket socket){
        this.chat = chat;
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(entradaMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        salir = false;
    }

    public void cerrarFlujos(){
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(entradaMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(){
        try {
            do{
                String mensaje = in.readLine();
                StringTokenizer mensaje2 = new StringTokenizer(mensaje, "$|#");
                mensaje = mensaje2.nextToken();

                if(mensaje.equals("MENSAJE")){
                    chat.mostrarMensaje(chat.getNombreB(), mensaje2.nextToken());
                    if(chat.getExtendedState()==chat.ICONIFIED){
                        new HiloSonidos();
                    }
                }else if(mensaje.equals("DESCONEXION")){
                    salir = true;
                    this.cerrarFlujos();
                    if(chat.isEnabled()){
                        chat.mostrarMensaje("DESCONEXION", null);
                    }
                }
            }while(!salir);
        } catch (IOException ex) {
            Logger.getLogger(entradaMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

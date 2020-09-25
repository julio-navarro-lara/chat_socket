/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidor;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
/**
 *
 * @author Julio Navarro Lara
 */
public class Servidor {

    private static Socket socket;
    private static ServerSocket socketServicio;
    private static ServerSocket socketServicio2;
    private static boolean salir;
    private static ArrayList<Cliente> listaUsuarios = new ArrayList<Cliente>();
    private static ArrayList<Cliente> usuariosConectados = new ArrayList<Cliente>();
    private static ArrayList<HebraSalida> listaHebras = new ArrayList<HebraSalida>();


    static void avisarHebras(HebraSalida hebra){
        Cliente cliente;
        String clienteNuevo = hebra.getCliente().getNombre();
        for(int i=0; i<listaHebras.size(); i++){
            cliente = listaHebras.get(i).getCliente();

            if(cliente.existeContacto(clienteNuevo)){
                listaHebras.get(i).nuevoContacto(clienteNuevo, hebra.getCliente().getEstado());
            }
        }   
    }

    static boolean hablarConHebra(String nombreA, String nombreB, String IP, String puerto){
        for(int i=0; i<listaHebras.size(); i++){
            if(listaHebras.get(i).getCliente().getNombre().equals(nombreB)){
                listaHebras.get(i).nuevaCharla(nombreA, IP, puerto);
                return true;
            }
        }
        return false;
    }

    static void addHebra(HebraSalida hebra){
        listaHebras.add(hebra);
    }

    static void eliminarHebra(HebraSalida hebra){
        listaHebras.remove(hebra);
    }


    public static int existeUsuario(String nombre){
        for(int i=0; i<listaUsuarios.size(); i++){
            if(listaUsuarios.get(i).getNombre().equals(nombre))
                return i;
        }
        return -1;
    }

    public static void addUsuario(String nombre, String pass, String email, 
            String pregunta, String respuesta, int estado){
        Cliente usuario = new Cliente(nombre, pass, email, pregunta, respuesta, estado);

        listaUsuarios.add(usuario);
    }

    static Cliente takeUsuario(String nombre){
        for(int i=0; i<listaUsuarios.size(); i++){
            if(listaUsuarios.get(i).getNombre().equals(nombre))
                return listaUsuarios.get(i);
        }
        return null;
    }

    static Cliente takeUsuario(int i){
        return listaUsuarios.get(i);
    }

    static ServerSocket getSS(){
        return socketServicio;
    }

    static ServerSocket getSS2(){
        return socketServicio2;
    }

    public static void guardarUsuarios(){

        File f = new File("usuarios");
        try{
          if(!f.exists()){
              f.createNewFile();
          }
        }catch(IOException exception){
          System.out.println("Error al crear el archivo");
        }

        FileOutputStream fos;
        ObjectOutputStream oos=null;
        try{
           fos=new FileOutputStream(f);
           oos=new ObjectOutputStream(fos);

           for(Cliente c:listaUsuarios){
               oos.writeObject(c);
           }
        }catch(IOException io){
           System.out.println("Problemas en la escritura de datos.");
        }
        try{
           oos.close();
        }catch(IOException e){
           System.out.println("Problema al cerrar el flujo");
        }
    }

    public static void cargarUsuarios(){

          File f=new File("usuarios");

          if(!f.exists()){
              return;
          }

          FileInputStream fis=null;
          ObjectInputStream ois=null;
          try{
              fis=new FileInputStream(f);
              ois=new ObjectInputStream(fis);
              Cliente cliente;
              while((cliente=(Cliente)ois.readObject())!=null){
                  listaUsuarios.add(cliente);
              }
          }catch(IOException e){
              System.out.println("Error en la lectura de datos");
          }catch(ClassNotFoundException e1){
              System.out.println("No se encuentra la clase en el archivo");
          }
          try{
              fis.close();
              ois.close();
          }catch(IOException e){
              System.out.println("Problema al cerrar el flujo");
          }catch(NullPointerException e2){
              System.out.println("Excepción no resuelta");
          }

          for(int i=0; i<listaUsuarios.size(); i++){
              listaUsuarios.get(i).setEstado(2);
          }

    }

    public static void main(String args[]){

        cargarUsuarios();

        try {
            socketServicio = new ServerSocket(24);
            socketServicio2 = new ServerSocket(25);

            do{
                socket = socketServicio.accept();

                new HebraPrincipal(socket).start();

                //new HebraEntrada(socket).start();

            }while(!salir);


        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}

class HebraSalida extends Thread{
    PrintWriter out;
    BufferedReader in;
    Socket socket;
    
    Cliente cliente;
    
    int estado;

    boolean salir;
    boolean usuarioConectado;
    boolean nuevaConver;

    String nuevoUsuario;

    String usuarioConver;
    String IP;
    String puerto;


    public HebraSalida(Cliente cliente){
        this.cliente = cliente;

        usuarioConectado = false;
        nuevaConver = false;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void nuevoContacto(String nuevoUsuario, int estado){
        this.nuevoUsuario = nuevoUsuario;
        this.estado = estado;
        usuarioConectado = true;
    }

    public void nuevaCharla(String nombreB, String IP, String puerto){
        this.usuarioConver = nombreB;
        this.IP = IP;
        this.puerto = puerto;
        nuevaConver = true;
    }


    public void cerrarFlujos(){
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(HebraSalida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run(){
        try {
            socket = Servidor.getSS2().accept();

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(HebraSalida.class.getName()).log(Level.SEVERE, null, ex);
        }

        Cliente contacto;
        String cadena = "CONTACTOS,";
        //Enviamos la lista de contactos
        for(int i = 0; i<cliente.numContactos(); i++){
            contacto = Servidor.takeUsuario(cliente.getContacto(i));
            cadena = cadena + contacto.getNombre() + "," + contacto.getEstado() + ",";
        }

        cadena = cadena + "&&&";

        out.println(cadena);

        do{
           if(usuarioConectado){
               out.println("CONECTADO,"+nuevoUsuario+","+estado);
               usuarioConectado = false;
           }
           if(nuevaConver){
               out.println("HABLAR,"+usuarioConver+","+IP+","+puerto);
               nuevaConver = false;
           }
           
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                //No pasa nada si se interrumpe
            }
        }while(!salir);
    }
}

class HebraPrincipal extends Thread{
    PrintWriter out;
    BufferedReader in;
    Socket socket;

    HebraSalida salida;

    boolean salir;
    int numero;

    public HebraPrincipal(Socket socket){
        try {
            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


        } catch (IOException ex) {
            Logger.getLogger(HebraPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void inicioUsuario(String nombre, String pass, int estado){
        numero = Servidor.existeUsuario(nombre);
        if(numero!=-1){
            Cliente cliente = Servidor.takeUsuario(nombre);
            if(!cliente.getPass().equals(pass)){
                out.println("CLAVE INCORRECTA");
            }else{
                salida = new HebraSalida(cliente);
                salida.start();
                cliente.setEstado(estado);
                Servidor.avisarHebras(salida);
                Servidor.addHebra(salida);
                out.println("LOGIN");
                solicitudesUsuario();
            }
        }else{
            out.println("NO EXISTE");
        }
    }

    private void solicitudesUsuario(){
        String mensaje;
        StringTokenizer mensaje2;
        int num;
        do{
            try {
                mensaje = in.readLine();
                if(mensaje!=null){
                    mensaje2 = new StringTokenizer(mensaje, ",");
                    mensaje = mensaje2.nextToken();
                    if(mensaje.equals("ADD CONTACTO")){
                        mensaje = mensaje2.nextToken();
                        num = Servidor.existeUsuario(mensaje);
                        if(num!=-1){
                            Servidor.takeUsuario(numero).agregarContacto(mensaje);
                            int estadoAmigo = Servidor.takeUsuario(num).getEstado();
                            out.println("OK,"+estadoAmigo);
                            Servidor.guardarUsuarios();
                        }else{
                            out.println("NO EXISTE");
                        }
                    }else if(mensaje.equals("CIERRE")){
                        Servidor.takeUsuario(numero).setEstado(2);
                        Servidor.guardarUsuarios();
                        Servidor.avisarHebras(salida);
                        Servidor.eliminarHebra(salida);
                        salida.interrupt();
                        salida.cerrarFlujos();
                        in.close();
                        out.close();
                        socket.close();
                        salir = true;
                    }else if(mensaje.equals("CAMBIO ESTADO")){
                        int est = Integer.parseInt(mensaje2.nextToken());
                        Servidor.takeUsuario(numero).setEstado(est);
                        Servidor.guardarUsuarios();
                        Servidor.avisarHebras(salida);
                    }else if(mensaje.equals("HABLAR")){
                        Servidor.hablarConHebra(Servidor.takeUsuario(numero).getNombre(), mensaje2.nextToken(),
                                mensaje2.nextToken(), mensaje2.nextToken());
                    }
                }else {
                    out.println("FALLO");
                }
            } catch (IOException ex) {
                Servidor.takeUsuario(numero).setEstado(2);
                Servidor.guardarUsuarios();
                Servidor.avisarHebras(salida);
                Servidor.eliminarHebra(salida);
                salida.interrupt();
                salida.cerrarFlujos();
                try {
                    in.close();
                } catch (IOException ex1) {
                    Logger.getLogger(HebraPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
                }
                out.close();
                try {
                    socket.close();
                } catch (IOException ex1) {
                    Logger.getLogger(HebraPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
                }
                salir = true;
            }


        }while(!salir);
    }

    private void registroUsuario(String nombre, String pass, String email,
            String pregunta, String respuesta){
        int num = Servidor.existeUsuario(nombre);
        if(num!=-1){
            //El usuario existe
            out.println("EXISTE");
        }else{
            Servidor.addUsuario(nombre, pass, email, pregunta, respuesta, 2);
            out.println("OK");
            Servidor.guardarUsuarios();
        }
    }

    private void olvidoUsuario(String nick){
        int num = Servidor.existeUsuario(nick);
        if(num==-1){
            out.println("NO EXISTE");
        }else{
            try {
                Cliente usuario = Servidor.takeUsuario(num);
                out.println(usuario.getPregunta());
                String s = in.readLine();

                if (!s.equals("ERROR")) {
                    if(s.equalsIgnoreCase(usuario.getRespuesta())){
                        out.println("BIEN,"+usuario.getPass());
                    }else{
                        out.println("MAL");
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(HebraPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run(){
        try {
            out.println("CONECTADO");

            String mensaje = in.readLine();
            StringTokenizer mensajeACachos = new StringTokenizer(mensaje, ",");

            mensaje = mensajeACachos.nextToken();

            if(mensaje.equals("INICIO")){
                inicioUsuario(mensajeACachos.nextToken(),mensajeACachos.nextToken(),
                        Integer.parseInt(mensajeACachos.nextToken()));
            }else if(mensaje.equals("REGISTRO")){
                registroUsuario(mensajeACachos.nextToken(),mensajeACachos.nextToken(),
                        mensajeACachos.nextToken(),mensajeACachos.nextToken(),mensajeACachos.nextToken());
            }else if(mensaje.equals("OLVIDO")){
                olvidoUsuario(mensajeACachos.nextToken());
            }

            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(HebraPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class Cliente implements Serializable{
    private String nombre;
    private String pass;
    private String preguntaSecreta;
    private String respuestaSecreta;
    private String email;
    private int estado;
    private ArrayList<String> listaContactos;

    public Cliente(String nombre, String pass, String email, 
            String preguntaSecreta, String respuestaSecreta, int estado){
        this.nombre = nombre;
        this.pass = pass;
        this.preguntaSecreta = preguntaSecreta;
        this.respuestaSecreta = respuestaSecreta;
        this.email = email;
        this.estado = estado;

        listaContactos = new ArrayList<String>();
    }

    public int getEstado(){
        return estado;
    }

    public String getNombre(){
        return nombre;
    }

    public String getPass(){
        return pass;
    }

    public String getPregunta(){
        return preguntaSecreta;
    }

    public String getRespuesta(){
        return respuestaSecreta;
    }

    public void setEstado(int i){
        estado = i;
    }

    public void agregarContacto(String nombre){
        listaContactos.add(nombre);
    }

    public int numContactos(){
        return listaContactos.size();
    }

    public String getContacto(int i){
        return listaContactos.get(i);
    }

    public boolean existeContacto(String nombre){
        for(int i=0; i<listaContactos.size(); i++){
            if(nombre.equals(listaContactos.get(i))){
                return true;
            }
        }
        return false;
    }
}

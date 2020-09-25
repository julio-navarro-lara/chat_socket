package chat;

import javax.sound.sampled.*;
import javax.sound.midi.*;
import java.io.*;
import java.awt.event.*;
/**
 * La clase <code>HiloSonidos</code> crea el hilo que controla los sonidos que realiza la aplicaciÃ³n durante su ejecuciÃ³n.
 * @version 1.0
 * @author Julio Navarro Lara
 */
public class HiloSonidos implements Runnable,LineListener {
    /**
     * Hilo que reproduce los sonidos.
     */
    private Thread t;
    /**
     * Archivo de sonido.
     */
    private File f;
    /**
     * Permite reproducir los sonidos.
     */
    private Clip clip;

    /**
     * Crea e inicia el hilo de sonidos.
     */
    public HiloSonidos(){
        t=new Thread(this,"HiloGuiMouse");
        t.start();
    }

    /**
     * Ejecuta el hilo de sonidos. Reproduce el sonido asociado al objeto que llama al mÃ©todo.
     * @see #reproducir()
     */
    public void run(){
        try{
           this.reproducir();
        }catch(LineUnavailableException e2){
            System.out.println("Mala lectura de linea");
        }catch(IOException e3){
            System.out.println("Error en la entrada");
        }catch(UnsupportedAudioFileException e4){
            System.out.println("Tipo de archivo no soportado");
        }
    }

    /**
     * Reproduce los sonidos de la aplicaciÃ³n. Una vez seleccionado el tipo de sonido asociado al objeto que llama al
     * mÃ©todo, comienza su reproducciÃ³n.
     * @throws LineUnavailableException
     * @throws IOException Si se ha producido un error en la entrada de audio
     * @throws UnsupportedAudioFileException Si la extensiÃ³n del archivo no es vÃ¡lida
     */
    public void reproducir() throws LineUnavailableException,IOException,
                UnsupportedAudioFileException{
        f = new File("sonido.wav");
        //Se inician las gestiones necesarias y se reproduce el sonido.
        Line.Info linfo=new Line.Info(Clip.class);
        Line line=AudioSystem.getLine(linfo);
	clip=(Clip)line;
        clip.addLineListener(this);
	AudioInputStream ais=AudioSystem.getAudioInputStream(f);
	clip.open(ais);
        clip.start();
    }

    /**
     * Actualiza la informaciÃ³n del LineEvent. Indica el estado de reproducciÃ³n de la canciÃ³n en la salida estÃ¡ndar.
     */
    public void update(LineEvent le){

                LineEvent.Type type;type=le.getType();
		if(type==LineEvent.Type.OPEN){
			System.out.println("OPEN");
		}else if(type==LineEvent.Type.CLOSE){
			System.out.println("CLOSE");
			System.exit(0);
		}else if(type==LineEvent.Type.START){
			System.out.println("START");
		}else if(type==LineEvent.Type.STOP){
			System.out.println("STOP");
		}
	}

}

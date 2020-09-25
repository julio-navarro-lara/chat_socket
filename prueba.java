import java.net.*;
import java.io.*;
public class prueba{
  
  public static void main(String args[]){
            URL url = null;
            String IP = "";

            try {
                url = new URL("http://checkip.dyndns.org/");
            } catch (MalformedURLException ex) {
                
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
                
            }

            System.out.println(IP);
  }
}
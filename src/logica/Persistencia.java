/**
 * Paquete en el que se encuentra las clases de logica del proyecto
 */
package logica;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * Clase persistente para la agenda
 * @author Daniel Parra - Andres Felipe Mosquera
 *
 */
public class Persistencia  {
	
	public static String ruta ="persistencia.bin";
	public Persistencia(){
		
	}
	
	/**
	 * Escribe un objeto en un .bin
	 * @param a objeto escrito
	 */
	public static void escribirObjeto(Object a){
		ObjectOutputStream escribir=null;
		try{
		escribir=new ObjectOutputStream(new FileOutputStream(ruta));
		}catch(Exception e){
			e.printStackTrace();
		}
		try {
			escribir.writeObject(a);
			escribir.flush();
			escribir.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Lee los datos de un objeto
	 * @return objeto con los datos de un .bin leidos
	 */
	public static Object leerObjeto(){
		ObjectInputStream leer;
		Object obj=null;
		try{
        FileInputStream file= new FileInputStream(ruta);
		leer= new ObjectInputStream(file);
		obj=leer.readObject();
		leer.close();
		}catch(Exception e){
			System.out.println("No hay archivo");
		}
		return obj;
		
	}
}

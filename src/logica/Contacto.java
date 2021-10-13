package logica;

import java.io.Serializable;
import java.util.UUID;

import listaEnlazada.ListaEnlazada;
/**
 * Clase con los atributoss correspondientes a un contacto
 * @author Daniel Parra
 *
 */
public class Contacto implements Serializable {

	private String nombre, correo, id, sex;// True men, false woman
	private int edad;
	private long numeroTel;
	private Fecha fecha;

	public Contacto(String nombre, String correo, String sex, int edad, long numeroTel, Fecha fecha) {
		id = UUID.randomUUID().toString();
		this.nombre = nombre;
		this.correo = correo;
		this.sex = sex;
		if (edad >= 0) {

			this.edad = edad;

		} else {
			this.edad = -1 * (edad);
		}
		if(numeroTel>=0){
			this.numeroTel=numeroTel;
		}else{
			this.numeroTel=-1*(numeroTel);
		}
		this.fecha = fecha;
	}

	/**
	 * Para saber los atributos de un contacto en String
	 * 
	 * @return los atributos del contacto en una lista enlazada
	 */
	public ListaEnlazada<String> atributosArreglo() {
		ListaEnlazada<String> atributos = new ListaEnlazada<>();
		atributos.add(nombre);
		atributos.add("" + edad);
		atributos.add(correo);
		atributos.add(numeroTel + "");

		return atributos;
	}

	@Override
	public String toString() {
		return nombre + " => " + numeroTel + " - " + correo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public void setEdad(int edad) {
		if (edad >= 0) {

			this.edad = edad;

		} else {
			this.edad = -1 * (edad);
		}
	}

	public void setNumeroTel(long numeroTel) {
		this.numeroTel = numeroTel;
	}

	public void setFecha(Fecha fecha) {
		this.fecha = fecha;
	}

	public String getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public int getEdad() {
		return edad;
	}

	public long getNumeroTel() {
		return numeroTel;
	}

	public Fecha getFecha() {
		return fecha;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	
	
	

}

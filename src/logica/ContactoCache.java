package logica;

import java.io.Serializable;

import listaEnlazada.ListaEnlazada;
/**
 * Contacto diseñado especialmente para deshacer y rehacer
 * @author Daniel Parra - Andres Mosquera
 *
 */
public class ContactoCache implements Serializable{
	private Contacto contacto;
	private boolean accion;
	
	
	public ContactoCache(Contacto contacto,boolean accion){
		this.contacto=contacto;
		this.accion=accion;
	}


	public Contacto getContacto() {
		return contacto;
	}


	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}


	public boolean isAccion() {
		return accion;
	}


	public void setAccion(boolean accion) {
		this.accion = accion;
	}
	
	
}

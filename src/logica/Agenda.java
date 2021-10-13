package logica;

import java.io.Serializable;

import pila.Pila;

/**
 * Clase Agenda, donde estaran los atributos de la agenda
 * 
 * @author Daniel Parra - Andres Mosquera
 *
 */
public class Agenda implements Serializable {

	private listaEnlazada.ListaEnlazada<Contacto> contactos;
	private Pila<ContactoCache> desHacer;
	private Pila<ContactoCache> reHacer;

	public Agenda() {
		contactos = new listaEnlazada.ListaEnlazada<Contacto>();
		desHacer = new Pila<>();
		reHacer = new Pila<>();
	}
	
	/**
	 * Determina si un contacto ya esta en la agenda
	 * @param contacto a evaluar 
	 * @return true si ya esta en la agenda, de lo contrario false
	 */
	public boolean contieneEsteContacto(Contacto contacto) {

		for (Contacto contacto1 : contactos) {
			if (contacto1.getNombre().equalsIgnoreCase(contacto.getNombre())
					&& contacto1.getCorreo().equalsIgnoreCase(contacto.getCorreo())
					&& contacto1.getEdad() == contacto.getEdad() 
					&& contacto1.getNumeroTel() == contacto.getNumeroTel()
					&& contacto1.getSex().equalsIgnoreCase(contacto.getSex())
					&& contacto1.getFecha().evaluaFecha(contacto.getFecha()) == 0) {
				
				return true;
				
			}

		}

		return false;
		
	}

	/**
	 * Busca contacto por id, si no se encuentra retorna -1
	 * 
	 * @param id
	 *            del contacto
	 * @return posicion del contacto
	 */

	public int buscarPorId(String id) {
		for (int i = 0; i < contactos.size(); i++) {

			try {
				if (contactos.get(i).getId().equals(id)) {
					return i;
				}

			} catch (Exception a) {
				a.printStackTrace();
			}

		}

		return -1;

	}

	public listaEnlazada.ListaEnlazada<Contacto> getContactos() {
		return contactos;
	}

	public void setContactos(listaEnlazada.ListaEnlazada<Contacto> contactos) {
		this.contactos = contactos;
	}

	public Pila<ContactoCache> getDesHacer() {
		return desHacer;
	}

	public void setDesHacer(Pila<ContactoCache> desHacer) {
		this.desHacer = desHacer;
	}

	public Pila<ContactoCache> getReHacer() {
		return reHacer;
	}

	public void setReHacer(Pila<ContactoCache> reHacer) {
		this.reHacer = reHacer;
	}
}

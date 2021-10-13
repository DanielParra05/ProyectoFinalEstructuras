package listaEnlazada;

import java.io.Serializable;
/**
 * Nodo de la lista enlazada
 * @author Daniel Parra - Andres Mosquera
 *
 * @param <T> dato de tipo generico
 */
public class Nodo<T> implements Serializable {
	
	private T dato;
	private Nodo<T> siguiente;
	
	public Nodo(T dato) {
		this.dato=dato;
		siguiente=null;
	}

	public T getDato() {
		return dato;
	}

	public void setDato(T dato) {
		this.dato = dato;
	}

	public Nodo<T> getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Nodo<T> siguiente) {
		this.siguiente = siguiente;
	}
	
}

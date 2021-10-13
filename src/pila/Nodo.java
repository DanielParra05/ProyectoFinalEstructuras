package pila;

import java.io.Serializable;
/**
 * Nodo para la clase pila 
 * @author Daniel Parra _ Andres felipe mosquera
 *
 * @param <T> parametro de tipo generico 
 */
public class Nodo<T> implements Serializable{
	private Nodo<T> siguiente;
	private T dato;
	
	public Nodo(T dato){
		siguiente=null;
		this.dato=dato;
	}

	public Nodo<T> getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(Nodo<T> siguiente) {
		this.siguiente = siguiente;
	}

	public T getDato() {
		return dato;
	}

	public void setDato(T dato) {
		this.dato = dato;
	}
	
	
	


}

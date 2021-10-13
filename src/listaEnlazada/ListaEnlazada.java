package listaEnlazada;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
/**
 * Estructura de datos usada en el proyecto
 * @author Daniel Parra-Andres Mosquera
 *
 * @param <T> de tipo generico
 */
public class ListaEnlazada<T> implements Collection<T>, Serializable{
	
	private Nodo<T> primero;
	private Nodo<T> ultimo;
	private int tamanio;

	public ListaEnlazada() {
		ultimo=null;
		tamanio=0;
		primero=null;
	}
	
	public Nodo<T> getPrimero() {
		return primero;
	}

	public void setPrimero(Nodo<T> primero) {
		this.primero = primero;
	}

	public Nodo<T> getUltimo() {
		return ultimo;
	}

	public void setUltimo(Nodo<T> ultimo) {
		this.ultimo = ultimo;
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamaño) {
		this.tamanio = tamaño;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new MiIteradorLE<>(this);
	}
	
	@Override
	public boolean add(T dato) {
		if(tamanio==0){ //Lista Vacia
			Nodo<T> nodo=new Nodo<T>(dato);
			primero=nodo;
			ultimo=nodo;
			tamanio++;
		}else{
			Nodo<T> aux=primero;
			while(aux.getSiguiente()!=null){
				aux=aux.getSiguiente();
			}
			Nodo<T> n=new Nodo<T>(dato);
			aux.setSiguiente(n);
			ultimo=n;
			n.setSiguiente(null);
			tamanio++;
		}
		return true;
	}
	
	@Override
	public boolean remove(Object obj) {
		Nodo<T> aux=primero;
		Nodo<T> anterior=null;
		while(aux!=null){
			if(aux.getDato().equals(obj)){
				if(anterior==null){//primero
					aux=aux.getSiguiente();
					this.primero=aux;
				}else{
					anterior.setSiguiente(aux.getSiguiente());
					aux=null;
				}
				tamanio--;
				return true;
			}else{
				anterior=aux;
				aux=aux.getSiguiente();
			}
		}
		return false;
	}
	
	@Override
	public boolean contains(Object obj) {
		Nodo<T> aux=primero;
		while(aux!=null){
			if(aux.getDato().equals(obj)){
				return true;
			}
			aux=aux.getSiguiente();
		}
		return false;
	}
	
	public String toString(){
		String cad="[";
		Nodo<T> aux=primero;
		while(aux!=null){
			if(aux.equals(primero)){
				cad+=aux.getDato().toString();
			}else{
				cad+=","+aux.getDato().toString();
			}
			aux=aux.getSiguiente();
		}
		cad+="]";
		return cad;
	}
	
	public T get(int indice) throws Exception {
		int posicion = 0;
		if (indice < 0 || indice >= tamanio)  {
			throw new Exception("error");
		} else {
			Nodo<T> aux = primero;
			while (aux != null) {
				if (posicion == indice) {
					return aux.getDato();
				}
				aux = aux.getSiguiente();
				posicion++;
			}
		}
		return null;
	}
	
	public void limpiarLista(){
		primero=null;
		tamanio=0;
	}

	@Override
	public boolean addAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (tamanio == 0);
	}

	@Override
	public boolean removeAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return tamanio;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}
		
}

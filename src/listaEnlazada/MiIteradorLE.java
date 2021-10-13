package listaEnlazada;

import java.io.Serializable;
import java.util.Iterator;
/**
 * Iterador de la ListaEnlazada
 * @author Daniel Parra - Andres Mosquera
 *
 * @param <T> generico 
 */
public class MiIteradorLE<T> implements Iterator<T>, Serializable{
	
	private ListaEnlazada<T> miLista;
	private int indice;
	
	public MiIteradorLE(ListaEnlazada<T> lista) {
		this.miLista=lista;
		indice=0;
	}

	@Override
	public boolean hasNext() {
		if(indice<miLista.getTamanio()){
			return true;
		}
		return false;
	}

	@Override
	public T next() {
		T elem=null;
		try {
			elem=miLista.get(indice);
		} catch (Exception e) {
		}						
		indice++;
		return elem;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
		
}

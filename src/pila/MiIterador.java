package pila;
import java.io.Serializable;
import java.util.Iterator;
/**
 * Iterador de la clase Pila
 * @author Daniel Parra  - Andrees Felipe Mosquera
 *
 * @param <T> parametro de tipo generico 
 */
public class MiIterador<T> implements Iterator<T>,Serializable{
	private Pila<T> pila;
	private int indice;
	
	public MiIterador(Pila<T> pila){
		this.pila=pila;
		indice=0;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return pila.size()>0;
	}

	@Override
	public T next() {
		T elemento=null;
		elemento=pila.pop();
		return elemento;
		
	}

	
}

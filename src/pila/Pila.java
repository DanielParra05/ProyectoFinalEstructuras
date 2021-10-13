package pila;
import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.Iterator;

/**
 * Estructura de datos tipo Pila
 * @author Daniel Parra - Andres Mosquera
 *
 * @param <T> generico
 */
public class Pila<T> implements Iterable<T>, Serializable{
	private Nodo<T> primero;
	private int length;
	
	public Pila(){
		primero=null;
		length=0;
	}
	
	/**
	 * Agrega un elemento a la pila
	 * @param dato elemento a agregar
	 */
	public void push(T dato){
		Nodo<T> nodo=new Nodo<T>(dato);
		if(isEmpty()){
			primero=nodo;		
		}
		else{
			nodo.setSiguiente(primero);
			primero=nodo;
		}
		length++;
	
	}
	
	/**
	 * Remueve el objeto que esta en la cima de la pila
	 * @return el objeto cima de la pila
	 */
	
	public T pop(){
		T dato=primero.getDato();
		if(isEmpty()){
			return null;
		}
		primero=primero.getSiguiente();
		length--;
		return dato;
	}
	
	public void clear(){
		length=0;
		primero=null;
	}
	public boolean isEmpty(){
		return length<=0;
	}
	
	public T peek() throws EmptyStackException{
		T dato= primero.getDato();
		if (dato != null) {
			return dato;
			}else {
				throw new EmptyStackException();
			}
	}
	
	public int size(){
		return length;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new MiIterador<T>(this);
	}
}

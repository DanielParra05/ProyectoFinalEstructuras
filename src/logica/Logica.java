package logica;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import listaEnlazada.ListaEnlazada;

/**
 * Logica correspondiente para la agenda
 * 
 * @author Daniel Parra - Felipe Mosquera
 * @version Noviembre 2016
 *
 */
public class Logica {

	private Agenda agenda;

	public Logica(Agenda agenda) {
		this.agenda = agenda;
	}

	/**
	 * Carga contactos en la agenda desde un .txt
	 * @param fc JFileChooser que se mostrara para cargar el .txt
	 * @param frame ventana window
	 */
	public void cargarAgenda(JFileChooser fc, JFrame frame) {
		// Abrimos la ventana, guardamos la opcion seleccionada por el usuario
		int seleccion = fc.showOpenDialog(frame);
		boolean indicador = false;
		ListaEnlazada<Contacto> contactos = new ListaEnlazada<Contacto>();
		// Si el usuario, pincha en aceptar
		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File fichero = fc.getSelectedFile();
			try {
				Scanner sc = new Scanner(fichero);
				while (sc.hasNextLine()) {
					String nombre = sc.nextLine();
					String[] atributos = sc.nextLine().split(" ");

					Contacto contacto = new Contacto(nombre, atributos[0], atributos[1],
							(Integer.parseInt(atributos[3])) - 2016, Long.parseLong(atributos[2]),
							new Fecha(Integer.parseInt(atributos[3]), atributos[4], Integer.parseInt(atributos[5])));

					if (!(agenda.contieneEsteContacto(contacto))) {
						agenda.getContactos().add(contacto);
					} else {
						indicador = true;
					}

				}
				sc.close();

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Formato de .txt no valido", "Error", JOptionPane.ERROR_MESSAGE);
				
			}

			if (indicador) {
				JOptionPane.showMessageDialog(null, "No se cargaron algunos contactos \n que estaban repetidos",
						"Informacion", JOptionPane.WARNING_MESSAGE);

			}

		}
	}

	/**
	 * Deshace acciones que se hayan hecho
	 * 
	 * @param contacto proporciona el contacto y que accion se debe tomar
	 */
	public void deshacer(ContactoCache contacto) {
		agenda.getReHacer().push(contacto);
		if (contacto.isAccion()) {
			agenda.getContactos().remove(contacto.getContacto());
		} else {
			if(!agenda.contieneEsteContacto(contacto.getContacto())){
			agenda.getContactos().add(contacto.getContacto());
			}
		}

	}

	/**
	 * Rehace todo accion que se haya deshecho
	 * 
	 * @param contacto
	 *            proporciona el contacto y que accion se debe tomar
	 */
	public void reHacer(ContactoCache contacto) {
		agenda.getDesHacer().push(contacto);
		if (contacto.isAccion()&& !agenda.contieneEsteContacto(contacto.getContacto())) {
			agenda.getContactos().add(contacto.getContacto());
		} else {
			agenda.getContactos().remove(contacto.getContacto());
		}

	}

	/**
	 * Ordena la lista de la agenda por edad
	 * 
	 * @return una nueva lista ordenada
	 */
	public ListaEnlazada<Contacto> ordenarEdad() {

		Contacto[] nueva = toArreglo(agenda.getContactos());

		Contacto aux;
		int min;
		for (int i = 0; i < nueva.length - 1; i++) {
			min = i;
			for (int j = i + 1; j < nueva.length; j++) {
				if (nueva[j].getEdad() < nueva[min].getEdad()) {
					min = j;
				}
			}
			if (i != min) {
				aux = nueva[i];
				nueva[i] = nueva[min];
				nueva[min] = aux;
			}
		}

		return toLista(nueva);

	}

	/**
	 * Ordena una lista de contactos por nombre
	 * 
	 * @return una nueva lista ordenada por nombre
	 */
	public ListaEnlazada<Contacto> ordenarNombre() {
		Contacto[] nueva = toArreglo(agenda.getContactos());

		Contacto aux;
		int min;
		for (int i = 0; i < nueva.length - 1; i++) {
			min = i;
			for (int j = i + 1; j < nueva.length; j++) {
				if (nueva[j].getNombre().compareToIgnoreCase(nueva[min].getNombre()) < 0) {
					min = j;
				}
			}
			if (i != min) {
				aux = nueva[i];
				nueva[i] = nueva[min];
				nueva[min] = aux;
			}
		}

		return toLista(nueva);
	}

	/**
	 * Ordena una lista por orden de fecha
	 * 
	 * @return lista ordenana por fecha
	 */
	public ListaEnlazada<Contacto> ordenarFecha() {
		Contacto[] nueva = toArreglo(agenda.getContactos());

		Contacto aux;
		int min;
		for (int i = 0; i < nueva.length - 1; i++) {
			min = i;
			for (int j = i + 1; j < nueva.length; j++) {
				if (nueva[j].getFecha().evaluaFecha(nueva[min].getFecha()) < 0) {
					min = j;
				}
			}
			if (i != min) {
				aux = nueva[i];
				nueva[i] = nueva[min];
				nueva[min] = aux;
			}
		}

		return toLista(nueva);
	}

	/**
	 * Ordena una lista de contactos por correo
	 * 
	 * @return una lista ordenada por correo
	 */
	public ListaEnlazada<Contacto> ordenarCorreo() {
		Contacto[] nueva = toArreglo(agenda.getContactos());

		Contacto aux;
		int min;
		for (int i = 0; i < nueva.length - 1; i++) {
			min = i;
			for (int j = i + 1; j < nueva.length; j++) {
				if (nueva[j].getCorreo().compareToIgnoreCase(nueva[min].getCorreo()) < 0) {
					min = j;
				}
			}
			if (i != min) {
				aux = nueva[i];
				nueva[i] = nueva[min];
				nueva[min] = aux;
			}
		}

		return toLista(nueva);
	}

	/**
	 * Convierte una lista de contactos a arreglo de contactos
	 * 
	 * @param contactos
	 *            lista a convertir
	 * @return arreglo de contactos
	 */
	public Contacto[] toArreglo(ListaEnlazada<Contacto> contactos) {

		Contacto[] nueva = new Contacto[contactos.size()];

		for (int i = 0; i < contactos.size(); i++) {
			try {
				nueva[i] = contactos.get(i);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return nueva;

	}

	/**
	 * Convierte un arreglo a Lista de tipo contacto
	 * 
	 * @param nueva
	 *            lista a convertir
	 * @return el arreglo nueva convertido a lista
	 */

	public ListaEnlazada<Contacto> toLista(Contacto[] nueva) {

		ListaEnlazada<Contacto> nueva1 = new ListaEnlazada<>();

		for (int i = 0; i < nueva.length; i++) {
			nueva1.add(nueva[i]);
		}

		return nueva1;

	}

	/**
	 * Busqueda que dados los valores de dos o mas atributos encuentra los
	 * contactos que coinciden con al menos un atributo
	 * 
	 * @param contacto
	 *            brinda un contacto con los atributos dados
	 * @return una nueva lista con solo los contactos que coincidieron
	 */
	public ListaEnlazada<Contacto> busquedaO(Contacto contacto) {
		ListaEnlazada<Contacto> nuevaLista = new ListaEnlazada<>();
		for (Contacto contactoAgenda : agenda.getContactos()) {
			boolean agregar = false;
			String[] nombreNuevo=contacto.getNombre().split(" ");
			String[] nombre = contactoAgenda.getNombre().split(" ");
			if (nombre.length == 1) {
				if (contacto.getNombre().length() <= contactoAgenda.getNombre().length()) {
					if (contacto.getNombre().equalsIgnoreCase(contactoAgenda.getNombre())) {
						agregar=true;
					}
				}

			} else if(nombreNuevo.length==1){
				for (int i = 0; i < nombre.length; i++) {
					if (contacto.getNombre().length() <= nombre[i].length()) {
						if (contacto.getNombre().equalsIgnoreCase(nombre[i])) {
							agregar=true;
						}
					}
					
				}
			}else{
				if(nombre.length>=nombreNuevo.length){
					boolean agregar2=false;
				for (int i = 0; i < nombreNuevo.length; i++) {
					if(i<nombreNuevo.length){
					if (nombreNuevo[i].length() <= nombre[i].length()) {
						if (nombreNuevo[i].equalsIgnoreCase(nombre[i])) {
							agregar2=true;
						}else{
							agregar2=false;
						}
					}
					}
				}
				if(agregar2){
					agregar=true;
				}
				}
			}

			if (contacto.getCorreo().equalsIgnoreCase(contactoAgenda.getCorreo())
					|| contacto.getEdad() == contactoAgenda.getEdad()
					|| contacto.getNumeroTel() == contactoAgenda.getNumeroTel()
					|| contacto.getSex().equals(contactoAgenda.getSex())) {
				agregar = true;
			}
			if (agregar) {
				nuevaLista.add(contactoAgenda);
			}
		}

		return nuevaLista;
	}

	/**
	 * Busqueda que dados los valores de dos o mas atributos encuentra amigos
	 * que coinciden con todos los atributos dados
	 * 
	 * @param n
	 *            atributo a buscar en la lista
	 * @param lista
	 *            lista en la que se busca el atributo n
	 * @return lista enlazada con los contactos que coincidieron
	 */
	public ListaEnlazada<Contacto> busquedaY(String n, ListaEnlazada<Contacto> lista) {
		ListaEnlazada<Contacto> nuevaLista = new ListaEnlazada<>();
		if (!n.equalsIgnoreCase("")) {
			String[] nombreNuevo=n.split(" ");
			for (Contacto contactoAgenda : lista) {
				boolean agregar = false;
				String[] nombre = contactoAgenda.getNombre().split(" ");
				if (nombre.length == 1) {
					if (n.length() <= contactoAgenda.getNombre().length()) {
						if (n.equalsIgnoreCase(contactoAgenda.getNombre())) {
							agregar=true;
						}
					}

				} else if(nombreNuevo.length==1){
					for (int i = 0; i < nombre.length; i++) {
						if (n.length() <= nombre[i].length()) {
							if (n.equalsIgnoreCase(nombre[i])) {
								agregar=true;
							}
						}
						
					}
				}else{
					if(nombre.length>=nombreNuevo.length){
						boolean agregar2=false;
					for (int i = 0; i < nombreNuevo.length; i++) {
						if(i<nombreNuevo.length){
						if (nombreNuevo[i].length() <= nombre[i].length()) {
							if (nombreNuevo[i].equalsIgnoreCase(nombre[i].substring(0, nombreNuevo[i].length())) ) {
								agregar2=true;
							}else{
								agregar2=false;
							}
						}
						}
					}
					if(agregar2){
						agregar=true;
					}
					}
				}

				if (n.equalsIgnoreCase(contactoAgenda.getCorreo())) {
					agregar = true;
				}
				if (n.equalsIgnoreCase(contactoAgenda.getNumeroTel() + "")) {
					agregar = true;
				}
				if (n.equalsIgnoreCase(contactoAgenda.getEdad() + "")) {
					agregar = true;
				}
				if (n.equalsIgnoreCase(contactoAgenda.getSex())) {
					agregar = true;
				}
				if (agregar) {
					nuevaLista.add(contactoAgenda);
				}
			}
			return nuevaLista;
		}
		return lista;
	}

	/**
	 * Busca elementos de la lista de contactos que tenga un atributo en comun
	 * con el parametro n
	 * 
	 * @param n
	 *            cadena a buscar en la lista de contactos
	 * @param lista
	 *            lista en la que se buscaran los contactos que tienen un
	 *            parametro coinciden con n
	 * @return una lista enlazada con los contactos que tienen al menos un
	 *         atributo relacionado
	 */
	public ListaEnlazada<Contacto> buscarNormal(String n, ListaEnlazada<Contacto> lista) {
		ListaEnlazada<Contacto> listaNueva = new ListaEnlazada<>();
		String[] nombreNuevo=n.split(" ");
		for (Contacto elem : lista) {

			String[] nombre = elem.getNombre().split(" ");
			if (nombre.length == 1) {
				if (n.length() <= elem.getNombre().length()) {
					if (n.equalsIgnoreCase(elem.getNombre().substring(0, n.length())) && !(listaNueva.contains(elem))) {
						listaNueva.add(elem);
					}
				}

			} else if(nombreNuevo.length==1){
				for (int i = 0; i < nombre.length; i++) {
					if (n.length() <= nombre[i].length()) {
						if (n.equalsIgnoreCase(nombre[i].substring(0, n.length())) && !(listaNueva.contains(elem))) {
							listaNueva.add(elem);
						}
					}
					
				}
			}else{
				if(nombre.length>=nombreNuevo.length){
					boolean agregar=false;
				for (int i = 0; i < nombreNuevo.length; i++) {
					if(i<nombreNuevo.length){
					if (nombreNuevo[i].length() <= nombre[i].length()) {
						if (nombreNuevo[i].equalsIgnoreCase(nombre[i].substring(0, nombreNuevo[i].length())) && !(listaNueva.contains(elem))) {
							agregar=true;
						}else{
							agregar=false;
						}
					}
					}
				}
				if(agregar){
					listaNueva.add(elem);
				}
				}
			}

			if (n.length() <= elem.getCorreo().length()) {
				if (n.equalsIgnoreCase(elem.getCorreo().substring(0, n.length())) && !(listaNueva.contains(elem))) {
					listaNueva.add(elem);

				}
			}

			if (n.length() <= (elem.getNumeroTel() + "").length()) {
				if (n.equalsIgnoreCase((elem.getNumeroTel() + "").substring(0, n.length()))
						&& !(listaNueva.contains(elem))) {
					listaNueva.add(elem);

				}
			}

		}

		return listaNueva;
	}

	/**
	 * Busca en la lista actual contactos que coincidan con alguno de los dos
	 * parametros
	 * 
	 * @param textB
	 *            texto por delante del *
	 * @param textA
	 *            por detras del *
	 * @param lista
	 *            lista en la que se buscaran los contactos que coinciden
	 * @return la nueva lista con los contactos que cumplieron las condiciones.
	 */
	public ListaEnlazada<Contacto> busquedaSimilaridad(String textB, String textA, ListaEnlazada<Contacto> lista) {
		ListaEnlazada<Contacto> listaNueva = new ListaEnlazada<>();
		int caso = -1;

		if (textA.equals("") && !textB.equals("")) {
			caso = 0;
		}
		if (textB.equals("") && !textA.equals("")) {
			caso = 1;
		}
		if (!textB.equals("") && !textA.equals("")) {
			caso = 2;
		}
		switch (caso) {
		case 0:
			for (Contacto elem : lista) {
				if (textB.length() <= elem.getNombre().length()) {
					if (textB.equalsIgnoreCase(elem.getNombre().substring(0, textB.length()))
							&& !(listaNueva.contains(elem))) {
						listaNueva.add(elem);
					}
				}
				if (textB.length() <= elem.getCorreo().length()) {
					if (textB.equalsIgnoreCase(elem.getCorreo().substring(0, textB.length()))
							&& !(listaNueva.contains(elem))) {
						listaNueva.add(elem);
					}
				}
				if (textB.length() <= (elem.getNumeroTel() + "").length()) {
					if (textB.equalsIgnoreCase((elem.getNumeroTel() + "").substring(0, textB.length()))
							&& !(listaNueva.contains(elem))) {
						listaNueva.add(elem);
					}
				}
			}
			break;

		case 1:
			for (Contacto elem : lista) {
				if (textA.length() <= elem.getNombre().length()) {
					int size = elem.getNombre().length() - textA.length();
					if (textA.equalsIgnoreCase(elem.getNombre().substring(size)) && !(listaNueva.contains(elem))) {
						listaNueva.add(elem);
					}
				}
				if (textA.length() <= elem.getCorreo().length()) {
					int size = elem.getCorreo().length() - textA.length();
					if (textA.equalsIgnoreCase(elem.getCorreo().substring(size)) && !(listaNueva.contains(elem))) {
						listaNueva.add(elem);
					}
				}
				if (textA.length() <= (elem.getNumeroTel() + "").length()) {
					int size = (elem.getNumeroTel() + "").length() - textA.length();
					if (textA.equalsIgnoreCase((elem.getNumeroTel() + "").substring(size))
							&& !(listaNueva.contains(elem))) {
						listaNueva.add(elem);
					}
				}
			}
			break;

		case 2:
			for (Contacto elem : lista) {
				if (textB.length() <= elem.getNombre().length()) {
					if (textB.equalsIgnoreCase(elem.getNombre().substring(0, textB.length()))
							&& !(listaNueva.contains(elem))) {
						if (textA.length() <= elem.getNombre().length()) {
							int size = elem.getNombre().length() - textA.length();
							if (textA.equalsIgnoreCase(elem.getNombre().substring(size))
									&& !(listaNueva.contains(elem))) {
								listaNueva.add(elem);
							}
						}
					}
				}

				if (textB.length() <= elem.getCorreo().length()) {
					if (textB.equalsIgnoreCase(elem.getCorreo().substring(0, textB.length()))
							&& !(listaNueva.contains(elem))) {
						if (textA.length() <= elem.getCorreo().length()) {
							int size = elem.getCorreo().length() - textA.length();
							if (textA.equalsIgnoreCase(elem.getCorreo().substring(size))
									&& !(listaNueva.contains(elem))) {
								listaNueva.add(elem);
							}
						}
					}
				}
				if (textB.length() <= (elem.getNumeroTel() + "").length()) {
					if (textB.equalsIgnoreCase((elem.getNumeroTel() + "").substring(0, textB.length()))
							&& !(listaNueva.contains(elem))) {
						if (textA.length() <= (elem.getNumeroTel() + "").length()) {
							int size = (elem.getNumeroTel() + "").length() - textA.length();
							if (textA.equalsIgnoreCase((elem.getNumeroTel() + "").substring(size))
									&& !(listaNueva.contains(elem))) {
								listaNueva.add(elem);
							}
						}
					}
				}
			}
			break;
		}
		return listaNueva;
	}

	/**
	 * Busca en la lista actual contactos que coincidan con la fecha parametro
	 * 
	 * @param fecha fecha que se debe encontrar
	 * @param contactos
	 *            lista en la que se va a buscar los contactos
	 * @return lista con los contactos que coincidieron
	 */
	public ListaEnlazada<Contacto> buscarPorFecha(Fecha fecha, ListaEnlazada<Contacto> contactos) {
		ListaEnlazada<Contacto> nuevaLista = new ListaEnlazada<>();

		for (Contacto contacto : contactos) {

			if (contacto.getFecha().getAnio() == fecha.getAnio() && contacto.getFecha().getDia() == fecha.getDia()
					&& contacto.getFecha().getMes().equals(fecha.getMes())) {
				nuevaLista.add(contacto);
			}

		}
		return nuevaLista;
	}

	/**
	 * Realiza un promedio con los atributos enteros
	 * 
	 * @param lista
	 *            de la que se van a extraer los elementos
	 * @param n
	 *            entero que define el case
	 * @return variable Long con el resultado del promedio
	 */
	public long promedio(ListaEnlazada<Contacto> lista, int n) {
		long pro = 0;
		if (lista.size() == 0) {
			return 0;
		}
		for (Contacto elem : lista) {
			switch (n) {
			case -1:

				pro += elem.getNumeroTel();

				break;
			case 1:
				pro += elem.getEdad();
				break;
			}
		}

		return pro / lista.size();
	}

	/**
	 * Realiza un evaluacion para encontrar el maximo y minimo de un atributo
	 * 
	 * @param lista
	 *            a la que se le va a hacer la evaluacion
	 * @param n
	 *            entero que define el case para obtener el atributo
	 * @return arreglo con las posiciones del maximo y minimo en la lista
	 * @throws Exception
	 *             cuando el indice al que se quiere acceder con get es mayor o
	 *             menor que el tamaño de la lista
	 */
	public int[] maximoYminimo(ListaEnlazada<Contacto> lista, int n) throws Exception {
		int[] posi = new int[2];
		int max = -1;
		int min = -1;
		for (int i = 0; i < lista.size(); i++) {
			switch (n) {
			case 0:
				if (max == -1 && min == -1) {
					max = i;
					min = i;
				} else {
					if (lista.get(i).getNumeroTel() > lista.get(max).getNumeroTel())
						max = i;
					if (lista.get(i).getNumeroTel() < lista.get(min).getNumeroTel())
						min = i;
				}
				break;
			case 1:
				if (max == -1 && min == -1) {
					max = i;
					min = i;
				}
			case 2:
				if (max == -1 && min == -1) {
					max = i;
					min = i;
				} else {
					if (lista.get(i).getEdad() > lista.get(max).getEdad())
						max = i;
					if (lista.get(i).getEdad() < lista.get(min).getEdad())
						min = i;
				}
				break;
			case 3:
				if (max == -1 && min == -1) {
					max = i;
					min = i;
				} else {
					if (lista.get(i).getNombre().compareTo(lista.get(max).getNombre()) > 0) {
						max = i;
					}
					if (lista.get(i).getNombre().compareTo(lista.get(min).getNombre()) < 0) {
						min = i;
					}
				}

				break;

			case 4:
				if (max == -1 && min == -1) {
					max = i;
					min = i;
				} else {
					if (lista.get(i).getCorreo().compareTo(lista.get(max).getCorreo()) > 0) {
						max = i;
					}
					if (lista.get(i).getCorreo().compareTo(lista.get(min).getCorreo()) < 0) {
						min = i;
					}
				}
				break;
			}
		}
		posi[0] = min;
		posi[1] = max;
		return posi;

	}
	/**
	 * Determina si el correo y el telefono del contacto que se va a agregar no existe
	 * @param contacto contacto a evaluar
	 * @return true si existe el correo, de lo contrario false
	 */
	public boolean contieneEsteContacto(Contacto contacto) {

		for (Contacto contacto1 : agenda.getContactos()) {
			if ( contacto1.getCorreo().equalsIgnoreCase(contacto.getCorreo())
					|| contacto1.getNumeroTel() == contacto.getNumeroTel()) {

				return true;
				
			}

		}

		return false;
		
	}
}

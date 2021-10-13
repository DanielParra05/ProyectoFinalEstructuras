package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import logica.Agenda;
import logica.Contacto;
import logica.Logica;
import logica.Persistencia;

/**
 * Interfaz grafica de busqueda por similaridad
 * 
 * @author Daniel Parra - Andres Mosquera
 *
 */
public class WindowSimilaridad extends JFrame implements ActionListener, KeyListener {
	private JLabel txtBuscar;
	private JTextField cmpBuscar;
	private JButton btnBuscar, btnVolver;
	private JList<Contacto> listaContactos;
	private JScrollPane scroll;
	private Image icon;
	private Window venta;
	private Logica logica;
	private Agenda miAgenda;

	public WindowSimilaridad(Image icon, Window venta, Agenda agenda) {
		miAgenda = agenda;
		setLayout(null);
		this.icon = icon;
		this.venta = venta;
		logica = new Logica(miAgenda);

		/**
		 * Inicilalizacion y ubicacion del JList
		 */
		listaContactos = new JList<Contacto>();
		scroll = new JScrollPane(listaContactos);
		scroll.setBounds(20, 52, 330, 220);
		add(scroll);

		/*
		 * Inicializacion de componentes del Frame
		 */

		txtBuscar = new JLabel("Buscar:");
		txtBuscar.setBounds(20, 10, 120, 50);
		txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 20));
		add(txtBuscar);

		cmpBuscar = new JTextField("*");
		cmpBuscar.setBounds(90, 28, 120, 20);
		add(cmpBuscar);
		cmpBuscar.addKeyListener(this);

		btnVolver = new JButton("<==volver");
		btnVolver.setBounds(20, 300, 120, 20);
		add(btnVolver);
		btnVolver.addActionListener(this);


		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try {
					Persistencia.escribirObjeto(miAgenda);
				} catch (Exception e) {

				}
				System.exit(0);
			}

		});
		
		/**
		 * Caracteristicas del frame
		 */
		setSize(380, 380);
		setTitle("Busqueda similaridad");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(icon);
		actualizarJList();

	}

	/**
	 * Sincroniza el JList con la lista de contactos de la agenda
	 */
	public void actualizarJList() {
		DefaultListModel<Contacto> a = new DefaultListModel<>();
		for (int i = 0; i < miAgenda.getContactos().size(); i++) {
			try {
				a.addElement(miAgenda.getContactos().get(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		listaContactos.setModel(a);
	}

	/**
	 * Actualiza la lista de contactos en el JList, al momento de buscar
	 * 
	 * @param lista
	 *            nueva que se va a ubicar en el JList listaContactos
	 */
	public void actualizarJListBusqueda(listaEnlazada.ListaEnlazada<Contacto> lista) {
		DefaultListModel a = new DefaultListModel<>();
		for (Contacto elem : lista) {
			a.addElement(elem);
		}
		listaContactos.setModel(a);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnVolver) {
			this.setVisible(false);
			venta.setVisible(true);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (cmpBuscar.getText().isEmpty() || cmpBuscar.getText().equalsIgnoreCase("*")) {
			actualizarJList();
		} else {
			String textB = "";
			String textA = "";
			for (int i = 0; i < cmpBuscar.getText().length(); i++) {
				if (cmpBuscar.getText().charAt(i) == '*') {
					if (i == 0) {
						textA = cmpBuscar.getText().substring(i + 1);
					} else {
						textB = cmpBuscar.getText().substring(0, i);
						textA = cmpBuscar.getText().substring(i + 1);
					}

				}
			}
			System.out.println(textB);
			System.out.println(textA);
			if (textB == "" && textA == "") {
				System.out.println("no sirve");
			} else {
				listaEnlazada.ListaEnlazada<Contacto> aux = logica.busquedaSimilaridad(textB, textA,
						miAgenda.getContactos());
				actualizarJListBusqueda(aux);
			}

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}

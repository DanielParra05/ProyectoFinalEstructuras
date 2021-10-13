package gui;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import logica.Agenda;
import logica.Contacto;
import logica.ContactoCache;
import logica.Logica;
import logica.Persistencia;

/**
 * Ventana Principal, en la cual el usuario interactua con la aplicacion
 * 
 * @author Daniel Parra - Andres Mosquera
 * @version 1.0 Noviembre de 2016
 */
public class Window extends JFrame implements ActionListener, KeyListener, ChangeListener, MouseListener {

	private JButton btnAgregar, btnOrdenar, btnEliminar, btnDeshacer, btnRehacer, btnVer, btnIr;
	private ImageIcon rehacer, deshacer, agregar, eliminar;
	private JList<Contacto> listaContactos;
	private JTextField cmpBuscar;
	private boolean ctrl = false;
	private JScrollPane scroll;
	private JLabel txtBuscar, txtOrdenar;
	private JComboBox combo, combo1;
	private Image icon;
	private InterfazAgregar interfazAgregar;
	private InterfazPromedio interfazPromedio;
	private WindowO interfazO;
	private WindowY interfazY;
	private InterfazMaxMin maxMin;
	private WindowDate interfazVen;
	private InterfazVer interfazVer;
	private WindowSimilaridad interfazSimi;
	private Logica logica;
	private Agenda miAgenda;
	private JFileChooser fc;

	/**
	 * Contructor de la interfaz principal
	 * 
	 * @param title
	 *            define el titulo que tendra la ventana
	 */
	public Window(String title) {
		setLayout(null);

		txtOrdenar = new JLabel("Ordenar Por: ");
		txtOrdenar.setBounds(20, 20, 120, 50);
		add(txtOrdenar);

		btnOrdenar = new JButton("Ordenar");
		btnOrdenar.setBounds(220, 35, 100, 20);
		btnOrdenar.addActionListener(this);
		btnOrdenar.addKeyListener(this);
		add(btnOrdenar);

		combo1 = new JComboBox();
		combo1.setBounds(100, 35, 100, 20);
		combo1.addItem("Nombre");
		combo1.addItem("Edad");
		combo1.addItem("Fecha");
		combo1.addItem("Correo");
		combo1.addKeyListener(this);
		add(combo1);

		icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/agenda.png"));

		/**
		 * Inicilalizacion y ubicacion del JList
		 */
		listaContactos = new JList<Contacto>();
		listaContactos.addMouseListener(this);
		listaContactos.addKeyListener(this);
		scroll = new JScrollPane(listaContactos);
		scroll.setBounds(20, 92, 330, 220);
		scroll.addKeyListener(this);
		add(scroll);

		// Cargar la agenda desde un archivo
		cargar();

		logica = new Logica(miAgenda);
		interfazSimi = new WindowSimilaridad(icon, this, miAgenda);
		interfazAgregar = new InterfazAgregar(this, icon, miAgenda, logica);
		interfazY = new WindowY(this, icon, logica, miAgenda);
		interfazO = new WindowO(this, icon, logica);
		maxMin = new InterfazMaxMin(this, icon, miAgenda, logica);
		interfazPromedio = new InterfazPromedio(this, icon, miAgenda, logica);

		fc = new JFileChooser();
		// Indicamos que podemos seleccionar varios ficheros
		fc.setMultiSelectionEnabled(true);

		// Indicamos lo que podemos seleccionar
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		// Creamos el filtro
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt");

		// Le indicamos el filtro
		fc.setFileFilter(filtro);

		/*
		 * Inicializacion de componentes del Frame
		 */
		combo = new JComboBox();
		combo.setBounds(20, 10, 150, 20);
		combo.addItem("Promedio");
		combo.addItem("Max-Min");
		combo.addItem("Busqueda Y");
		combo.addItem("Busqueda O");
		combo.addItem("Busqueda por similaridad");
		combo.addItem("Busqueda por fecha");
		combo.addItem("Cargar archivo");
		combo.addKeyListener(this);
		add(combo);

		btnIr = new JButton("Ir");
		btnIr.setBounds(190, 10, 45, 20);
		btnIr.addActionListener(this);
		btnIr.addKeyListener(this);
		add(btnIr);

		btnAgregar = new JButton();
		agregar = new ImageIcon(getClass().getResource("/agregar.png"));
		btnAgregar.setIcon(agregar);
		btnAgregar.addActionListener(this);
		btnAgregar.setBounds(20, 332, 40, 40);
		btnAgregar.addKeyListener(this);
		add(btnAgregar);

		btnEliminar = new JButton();
		eliminar = new ImageIcon(getClass().getResource("/eliminar.png"));
		btnEliminar.setIcon(eliminar);
		btnEliminar.setBounds(115, 332, 40, 40);
		add(btnEliminar);
		btnEliminar.addKeyListener(this);
		btnEliminar.addActionListener(this);

		txtBuscar = new JLabel("Buscar:");
		txtBuscar.setBounds(20, 49, 120, 50);
		txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 20));
		add(txtBuscar);

		cmpBuscar = new JTextField();
		cmpBuscar.setBounds(90, 67, 120, 20);
		add(cmpBuscar);
		cmpBuscar.addKeyListener(this);

		btnRehacer = new JButton();
		rehacer = new ImageIcon(getClass().getResource("/rehacer.png"));
		btnRehacer.setBounds(310, 332, 40, 40);
		btnRehacer.setIcon(rehacer);
		add(btnRehacer);
		btnRehacer.addKeyListener(this);
		btnRehacer.addActionListener(this);

		btnVer = new JButton("Ver y editar");
		btnVer.setBounds(120, 382, 120, 30);
		add(btnVer);
		btnVer.addKeyListener(this);
		btnVer.addActionListener(this);

		btnDeshacer = new JButton();
		deshacer = new ImageIcon(getClass().getResource("/deshacer.png"));
		btnDeshacer.setBounds(210, 332, 40, 40);
		btnDeshacer.setIcon(deshacer);
		btnDeshacer.addActionListener(this);
		btnDeshacer.addKeyListener(this);
		add(btnDeshacer);

		/**
		 * Caracteristicas del frame
		 */
		setSize(380, 450);
		setTitle(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(icon);
		setVisible(true);
		addKeyListener(this);

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
	 *            la nueva que se va a ubicar en el JList listaContactos
	 */
	public void actualizarJListBusqueda(listaEnlazada.ListaEnlazada<Contacto> lista) {
		DefaultListModel a = new DefaultListModel<>();
		for (Contacto elem : lista) {
			a.addElement(elem);
		}
		listaContactos.setModel(a);
	}

	/**
	 * Escribe los cambios en el archivo persistente
	 */
	public void guardarCambios() {
		try {
			Persistencia.escribirObjeto(miAgenda);
		} catch (Exception e) {

		}
	}

	/**
	 * Cargar la persistencia al iniciar el programa
	 */
	private void cargar() {

		try {
			Object b = Persistencia.leerObjeto();
			if (b == null) {
				miAgenda = new Agenda();
			} else {
				miAgenda = (Agenda) b;
				miAgenda.getDesHacer().clear();
				miAgenda.getReHacer().clear();
				actualizarJList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnDeshacer) {
			if (miAgenda.getDesHacer().size() != 0) {
				logica.deshacer(miAgenda.getDesHacer().pop());
			} else {
				JOptionPane.showMessageDialog(null, "No hay mas acciones para deshacer", "",
						JOptionPane.WARNING_MESSAGE);
			}
			miAgenda.setContactos(logica.ordenarNombre());
			actualizarJList();
		}
		if (e.getSource() == btnRehacer) {
			if (miAgenda.getReHacer().size() != 0) {
				logica.reHacer(miAgenda.getReHacer().pop());

			} else {
				JOptionPane.showMessageDialog(null, "No hay mas acciones para rehacer", "",
						JOptionPane.WARNING_MESSAGE);
			}
			miAgenda.setContactos(logica.ordenarNombre());
			actualizarJList();
		}
		if (e.getSource() == btnOrdenar) {

			if (combo1.getSelectedItem() == "Edad") {
				miAgenda.setContactos(logica.ordenarEdad());

			}

			if (combo1.getSelectedItem() == "Nombre") {
				miAgenda.setContactos(logica.ordenarNombre());
			}

			if (combo1.getSelectedItem() == "Fecha") {
				miAgenda.setContactos(logica.ordenarFecha());
			}

			if (combo1.getSelectedItem() == "Correo") {
				miAgenda.setContactos(logica.ordenarCorreo());
			}

			actualizarJList();
		}

		if (combo.getSelectedItem() == "Cargar archivo" && e.getSource() == btnIr) {

			logica.cargarAgenda(fc, this);
			miAgenda.setContactos(logica.ordenarNombre());
			actualizarJList();

		}

		if (combo.getSelectedItem() == "Busqueda por fecha" && e.getSource() == btnIr) {
			interfazVen = new WindowDate(this, icon, miAgenda, logica);
			setVisible(false);
			interfazVen.setVisible(true);
		}
		if (combo.getSelectedItem() == "Busqueda O" && e.getSource() == btnIr) {
			setVisible(false);
			interfazO.setVisible(true);
		}
		if (combo.getSelectedItem() == "Busqueda Y" && e.getSource() == btnIr) {
			setVisible(false);
			interfazY.setVisible(true);
		}
		if (combo.getSelectedItem() == "Busqueda por similaridad" && e.getSource() == btnIr) {
			setVisible(false);
			interfazSimi.setVisible(true);
		}
		if (combo.getSelectedItem() == "Promedio" && e.getSource() == btnIr) {
			setVisible(false);
			interfazPromedio.setVisible(true);

		}
		if (combo.getSelectedItem() == "Max-Min" && e.getSource() == btnIr) {
			setVisible(false);
			maxMin.setVisible(true);

		}

		if (e.getSource() == btnEliminar) {
			if (listaContactos.getSelectedValue() != null) {

				int n = JOptionPane.showConfirmDialog(null,
						"¿Esta seguro que desea \n eliminar a "
								+ ((Contacto) listaContactos.getSelectedValue()).getNombre() + "?",
						"", JOptionPane.YES_NO_OPTION);
				if (n == 0) {
					miAgenda.getContactos().remove((Contacto) listaContactos.getSelectedValue());
					miAgenda.getDesHacer().push(new ContactoCache((Contacto) listaContactos.getSelectedValue(), false));
					actualizarJList();

				}

			} else {
				JOptionPane.showMessageDialog(null, "Seleccione un item para eliminar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		if (e.getSource() == btnVer) {
			if (listaContactos.getSelectedValue() != null) {
				interfazVer = new InterfazVer((Contacto) listaContactos.getSelectedValue(), this, icon, miAgenda);
				interfazVer.setVisible(true);
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione un contacto para ver y editar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		if (e.getSource() == btnAgregar) {
			setVisible(false);
			interfazAgregar.setVisible(true);
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

		if (arg0.getKeyCode() == arg0.VK_ESCAPE) {

			int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea cerrar la agenda?", "",
					JOptionPane.YES_NO_OPTION);

			if (respuesta == JOptionPane.YES_OPTION) {

				System.exit(0);

			}

		}

		if (arg0.getKeyCode() == arg0.VK_CONTROL) {
			ctrl = true;

		}

		if (arg0.getKeyCode() == arg0.VK_DELETE) {
			if (listaContactos.getSelectedValue() != null) {
				int n = JOptionPane.showConfirmDialog(null,
						"¿Esta seguro que desea \n eliminar a "
								+ ((Contacto) listaContactos.getSelectedValue()).getNombre() + "?",
						"", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					miAgenda.getContactos().remove((Contacto) listaContactos.getSelectedValue());
					miAgenda.getDesHacer().push(new ContactoCache((Contacto) listaContactos.getSelectedValue(), false));
					actualizarJList();

				}

			} else {
				JOptionPane.showMessageDialog(null, "Seleccione un item para eliminar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		if (arg0.getKeyCode() == arg0.VK_Z && ctrl) {
			if (miAgenda.getDesHacer().size() != 0) {
				logica.deshacer(miAgenda.getDesHacer().pop());
				miAgenda.setContactos(logica.ordenarNombre());
				actualizarJList();
			} else {
				JOptionPane.showMessageDialog(null, "No hay mas acciones para deshacer", "",
						JOptionPane.WARNING_MESSAGE);
			}
		}
		if (arg0.getKeyCode() == arg0.VK_Y && ctrl) {
			if (miAgenda.getReHacer().size() != 0) {
				logica.reHacer(miAgenda.getReHacer().pop());
				miAgenda.setContactos(logica.ordenarNombre());
				actualizarJList();
			} else {
				JOptionPane.showMessageDialog(null, "No hay mas acciones para rehacer", "",
						JOptionPane.WARNING_MESSAGE);
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == arg0.VK_CONTROL) {
			ctrl = false;

		}

		if (cmpBuscar.getText().isEmpty()) {
			actualizarJList();
		} else {
			listaEnlazada.ListaEnlazada<Contacto> aux = logica.buscarNormal(cmpBuscar.getText(),
					miAgenda.getContactos());
			actualizarJListBusqueda(aux);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2) {
			if (listaContactos.getSelectedValue() != null) {
				interfazVer = new InterfazVer((Contacto) listaContactos.getSelectedValue(), this, icon, miAgenda);
				interfazVer.setVisible(true);
				interfazVer.setFocusable(true);
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "Seleccione un contacto para ver y editar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public Agenda getMiAgenda() {
		return miAgenda;
	}

	public void setMiAgenda(Agenda miAgenda) {
		this.miAgenda = miAgenda;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}

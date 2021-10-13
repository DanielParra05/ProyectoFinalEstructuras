package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import listaEnlazada.ListaEnlazada;
import logica.Agenda;
import logica.Contacto;
import logica.Fecha;
import logica.Logica;
import logica.Persistencia;

/**
 * Interfaz de busqueda Y
 * 
 * @author Daniel Parra
 *
 */
public class WindowY extends JFrame implements ChangeListener, ActionListener {
	private JCheckBox check1Nombre, check2Telefono, check3Correo, check4Edad, check5Masculino, check6Femenino;
	private JTextField nombre, telefono, correo, edad;
	private JList listaContactos;
	private JScrollPane scroll;
	private Agenda agenda;
	private JButton btnVolver, btnBuscar;
	private Window ven;
	private Logica logica;

	public WindowY(Window v, Image icon, Logica logica, Agenda agenda) {
		this.ven = v;
		this.logica = logica;
		this.agenda = agenda;
		this.setLayout(null);
		check1Nombre = new JCheckBox("Nombre");
		check1Nombre.setBounds(10, 30, 150, 20);
		check1Nombre.addChangeListener(this);
		add(check1Nombre);

		nombre = new JTextField();
		nombre.setBounds(10, 60, 150, 20);
		nombre.setEnabled(false);
		add(nombre);

		check2Telefono = new JCheckBox("Telefono");
		check2Telefono.setBounds(10, 90, 150, 30);
		check2Telefono.addChangeListener(this);
		add(check2Telefono);

		telefono = new JTextField();
		telefono.setBounds(10, 120, 150, 20);
		telefono.setEnabled(false);
		add(telefono);

		check3Correo = new JCheckBox("Correo");
		check3Correo.setBounds(10, 150, 150, 20);
		check3Correo.addChangeListener(this);
		add(check3Correo);

		correo = new JTextField();
		correo.setBounds(10, 180, 150, 20);
		correo.setEnabled(false);
		add(correo);

		check4Edad = new JCheckBox("Edad");
		check4Edad.setBounds(10, 210, 150, 20);
		check4Edad.addChangeListener(this);
		add(check4Edad);

		edad = new JTextField();
		edad.setBounds(10, 240, 150, 20);
		edad.setEnabled(false);
		add(edad);

		check5Masculino = new JCheckBox("Masculino");
		check5Masculino.setBounds(10, 270, 150, 20);
		check5Masculino.addChangeListener(this);
		add(check5Masculino);

		check6Femenino = new JCheckBox("Femenino");
		check6Femenino.setBounds(10, 300, 150, 20);
		check6Femenino.addChangeListener(this);
		add(check6Femenino);

		btnVolver = new JButton("<== Volver");
		btnVolver.setBounds(10, 325, 150, 20);
		btnVolver.addActionListener((ActionListener) this);
		add(btnVolver);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(170, 10, 150, 20);
		btnBuscar.addActionListener((ActionListener) this);
		add(btnBuscar);

		// Inicializacion JList
		listaContactos = new JList<Object>();
		scroll = new JScrollPane(listaContactos);
		scroll.setBounds(170, 40, 300, 300);
		add(scroll);

		this.setSize(500, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(icon);
		this.setTitle("BusquedaY");
		this.setLocationRelativeTo(null);
		

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try {
					Persistencia.escribirObjeto(v.getMiAgenda());
				} catch (Exception e) {

				}
				System.exit(0);
			}

		});

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (check1Nombre.isSelected() == true) {
			nombre.setEnabled(true);
		}
		if (check1Nombre.isSelected() == false) {
			nombre.setText("");
			nombre.setEnabled(false);
		}
		if (check2Telefono.isSelected() == true) {
			telefono.setEnabled(true);
		}
		if (check2Telefono.isSelected() == false) {
			telefono.setText("");
			telefono.setEnabled(false);
		}
		if (check3Correo.isSelected() == true) {
			correo.setEnabled(true);
		}
		if (check3Correo.isSelected() == false) {
			correo.setText("");
			correo.setEnabled(false);
		}
		if (check4Edad.isSelected() == true) {
			edad.setEnabled(true);
		}
		if (check4Edad.isSelected() == false) {
			edad.setText("");
			edad.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ListaEnlazada<Contacto> nuevaLista = agenda.getContactos();

		if (e.getSource() == btnBuscar) {

			ListaEnlazada<JCheckBox> checksSeleccionados = checksSeleccionados();
			Contacto defalt;
			String nombreD = "";
			String correoD = "";
			String sexoD = "";
			String edadD = "";
			String numeroD = "";

			if (checksSeleccionados.contains(check5Masculino) && checksSeleccionados.contains(check6Femenino)) {
				JOptionPane.showMessageDialog(null, "No seleccione dos sexos.", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (check1Nombre.isSelected() && nombre.getText().trim().equals("")
					|| check2Telefono.isSelected() && telefono.getText().trim().equals("")
					|| check3Correo.isSelected() && correo.getText().trim().equals("")
					|| check4Edad.isSelected() && edad.getText().trim().equals("")) {

				error();
			} else {
				
				for (JCheckBox check : checksSeleccionados) {
					if (check.equals(check1Nombre)) {
						nombreD = nombre.getText();
						nuevaLista = logica.busquedaY(nombreD, agenda.getContactos());
					}

					if (check.equals(check2Telefono)) {
						numeroD = telefono.getText();
						nuevaLista = logica.busquedaY(numeroD, nuevaLista);
						
					}

					if (check.equals(check3Correo)) {
						correoD = correo.getText();
						nuevaLista = logica.busquedaY(correoD, nuevaLista);

					}

					if (check.equals(check4Edad)) {

						edadD = edad.getText();
						nuevaLista = logica.busquedaY(edadD, nuevaLista);
					}
				}

				if (check5Masculino.isSelected()) {

					sexoD = "Masculino";
					nuevaLista = logica.busquedaY(sexoD, nuevaLista);

				}

				if (check6Femenino.isSelected()) {

					sexoD = "Femenino";
					nuevaLista = logica.busquedaY(sexoD, nuevaLista);
				}

				actualizarJList(nuevaLista);
				if (nuevaLista.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Ningun contacto coincidio con su busqueda", "Informacion", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		}

		if (e.getSource() == btnVolver) {
			check1Nombre.setSelected(false);
			check2Telefono.setSelected(false);
			check3Correo.setSelected(false);
			check4Edad.setSelected(false);
			check5Masculino.setSelected(false);
			check6Femenino.setSelected(false);
			nombre.setText("");
			telefono.setText("");
			edad.setText("");
			correo.setText("");
			ven.setVisible(true);
			this.setVisible(false);
		}

	}

	/**
	 * Actualiza el JList de la ventana
	 * 
	 * @param nuevosContactos
	 *            nueva lista para crear la DefaultListModel
	 */
	public void actualizarJList(ListaEnlazada<Contacto> nuevosContactos) {
		DefaultListModel<Contacto> a = new DefaultListModel<>();

		for (Contacto contacto : nuevosContactos) {
			a.addElement(contacto);
		}

		listaContactos.setModel(a);
	}

	/**
	 * Ventana con un error especifico
	 */
	public void error() {
		JOptionPane.showMessageDialog(null, "No deje campos seleccionados en blanco.", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Agrega a una Lista todos los checks seleccionados del momento
	 * 
	 * @return lista con los checks seleccionados
	 */
	public ListaEnlazada<JCheckBox> checksSeleccionados() {

		ListaEnlazada<JCheckBox> checksSeleccionados = new ListaEnlazada<JCheckBox>();

		if (check1Nombre.isSelected()) {
			checksSeleccionados.add(check1Nombre);

		}

		if (check2Telefono.isSelected()) {

			checksSeleccionados.add(check2Telefono);
		}

		if (check3Correo.isSelected()) {
			checksSeleccionados.add(check3Correo);
		}

		if (check4Edad.isSelected()) {
			checksSeleccionados.add(check4Edad);

		}

		if (check5Masculino.isSelected()) {
			checksSeleccionados.add(check5Masculino);

		}

		if (check6Femenino.isSelected()) {
			checksSeleccionados.add(check6Femenino);
		}

		return checksSeleccionados;
	}

}

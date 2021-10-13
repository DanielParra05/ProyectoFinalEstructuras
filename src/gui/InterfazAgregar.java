package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import logica.Agenda;
import logica.Contacto;
import logica.ContactoCache;
import logica.Fecha;
import logica.Logica;
import logica.Persistencia;

/**
 * Interfaz Agregar, ventana en la que se piden los datos para agregar un
 * contacto
 * 
 * @author Daniel Parra - Andres Mosquera
 *
 */
public class InterfazAgregar extends JFrame implements ActionListener {

	private JLabel txtNombre, txtSexo, txtTelefono, txtFecha, txtCorreo, txtAnio, txtMes, txtDia;
	private JTextField cmpNombre, cmpTelefono, cmpCorreo;
	private JComboBox combo, year, month, day;
	private JButton btnAgregar, btnVolver;
	private Window window;
	private Logica logica;
	private Agenda miAgenda;
	private String[] a = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };

	public InterfazAgregar(Window window, Image icon, Agenda miAgenda, Logica logica) {
		setLayout(null);

		this.logica = logica;
		this.window = window;
		this.miAgenda = miAgenda;

		// Inicializacion de etiquetas

		txtNombre = new JLabel("Nombre: ");
		txtNombre.setBounds(30, 30, 120, 40);
		add(txtNombre);

		cmpNombre = new JTextField();
		cmpNombre.setBounds(90, 40, 100, 20);
		add(cmpNombre);

		txtSexo = new JLabel("Sexo: ");
		txtSexo.setBounds(30, 60, 120, 40);
		add(txtSexo);

		combo = new JComboBox();
		combo.setBounds(90, 70, 100, 20);
		combo.addItem("Masculino");
		combo.addItem("Femenino");
		add(combo);

		txtTelefono = new JLabel("Telefono:");
		txtTelefono.setBounds(30, 90, 120, 40);
		add(txtTelefono);

		cmpTelefono = new JTextField();
		cmpTelefono.setBounds(90, 100, 100, 20);
		add(cmpTelefono);

		txtCorreo = new JLabel("Correo:");
		txtCorreo.setBounds(30, 120, 120, 40);
		add(txtCorreo);

		cmpCorreo = new JTextField();
		cmpCorreo.setBounds(90, 130, 100, 20);
		add(cmpCorreo);

		txtFecha = new JLabel("Fecha de nacimiento:");
		txtFecha.setBounds(30, 150, 130, 40);
		add(txtFecha);

		txtAnio = new JLabel("Año:");
		txtAnio.setBounds(30, 180, 130, 40);
		add(txtAnio);

		year = new JComboBox<>();
		year.setBounds(60, 190, 55, 20);
		for (int i = 2015; i > 1909; i--) {
			year.addItem(i);
		}
		year.addActionListener(this);
		add(year);

		txtMes = new JLabel("Mes:");
		txtMes.setBounds(130, 180, 130, 40);
		add(txtMes);

		month = new JComboBox<>();
		month.setBounds(160, 190, 85, 20);
		for (int i = 0; i < a.length; i++) {
			month.addItem(a[i]);
		}
		month.addActionListener(this);
		add(month);

		txtDia = new JLabel("Dia:");
		txtDia.setBounds(259, 180, 130, 40);
		add(txtDia);

		day = new JComboBox<>();
		day.setBounds(280, 190, 40, 20);
		for (int i = 1; i < 32; i++) {
			day.addItem(i);
		}
		add(day);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(20, 310, 120, 30);
		add(btnAgregar);
		btnAgregar.addActionListener(this);

		

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(210, 310, 120, 30);
		add(btnVolver);
		btnVolver.addActionListener(this);

		// Caracteristicas ventana
		setSize(376, 400);
		setTitle("Agregar contacto");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(icon);
		

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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == year || e.getSource() == month) {
			if (year.getSelectedItem() != null && month.getSelectedItem() != null) {
				day.removeAllItems();
				Fecha f = new Fecha((int) year.getSelectedItem(), month.getSelectedItem() + "", 1);
				int i = f.mes((int) year.getSelectedItem(), month.getSelectedItem() + "");
				for (int j = 1; j <= i; j++) {
					day.addItem(j);
				}
			}

		}

		if (e.getSource() == btnAgregar) {

			if (!cmpNombre.getText().trim().equals("") && !cmpCorreo.getText().trim().equals("")
					&& !cmpTelefono.getText().trim().equals("")) {
				if (day.getSelectedItem() != null) {

					String nombre = cmpNombre.getText();
					String correo = cmpCorreo.getText();
					String sex = "";
					if (combo.getSelectedItem() == "Masculino") {
						sex = "Masculino";
					} else {

						sex = "Femenino";

					}
					Fecha fecha = new Fecha((Integer) year.getSelectedItem(), (String) month.getSelectedItem(),
							(Integer) day.getSelectedItem());
					try {
						long telefono = Long.parseLong(cmpTelefono.getText());
						Contacto contacto = new Contacto(nombre, correo, sex, 2016 - ((Integer) year.getSelectedItem()),
								telefono, fecha);
						if (!(miAgenda.contieneEsteContacto(contacto))) {
							if(!(logica.contieneEsteContacto(contacto))){
								miAgenda.getContactos().add(contacto);
								miAgenda.getDesHacer().push(new ContactoCache(contacto, true));
								cmpNombre.setText("");
								cmpCorreo.setText("");
								cmpTelefono.setText("");

								int n = JOptionPane.showConfirmDialog(null,
										"Contacto agregado exitosamente! \n ¿Desea agregar otro contacto?", "",
										JOptionPane.YES_NO_OPTION);

								if (n == 1) {
									setVisible(false);
									window.setVisible(true);
								}

								window.getMiAgenda().setContactos(logica.ordenarNombre());
								window.actualizarJList();
							}else{
								JOptionPane.showMessageDialog(null,
										"El correo o el numero de telefono estan repetidos", "Informacion",
										JOptionPane.WARNING_MESSAGE);
							}
							
						} else {
							JOptionPane.showMessageDialog(null,
									"No se cargaron algunos contactos que estaban repetidos", "Informacion",
									JOptionPane.WARNING_MESSAGE);
						}
						

					} catch (NumberFormatException a) {
						JOptionPane.showMessageDialog(null, "Verifique el campo telefono!", "Error",
								JOptionPane.ERROR_MESSAGE);
					}

				} else {

					JOptionPane.showMessageDialog(null, "Verifique el campo fecha!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "No deje campos en blanco!", "Error", JOptionPane.ERROR_MESSAGE);
			}

		}

		if (e.getSource() == btnVolver) {

			if (!cmpNombre.getText().trim().equals("") || !cmpCorreo.getText().trim().equals("")
					|| !cmpTelefono.getText().trim().equals("")) {
				int n = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea volver sin guardar los cambios?",
						"Confirmacion", JOptionPane.YES_NO_OPTION);
				if (n == 0) {

					this.setVisible(false);
					cmpCorreo.setText("");
					cmpTelefono.setText("");
					cmpNombre.setText("");
					window.setVisible(true);
				}

			} else {
				this.setVisible(false);
				cmpCorreo.setText("");
				cmpTelefono.setText("");
				cmpNombre.setText("");
				window.setVisible(true);

			}

		}

	}

}

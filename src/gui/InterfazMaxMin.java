package gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import logica.Agenda;
import logica.Logica;
import logica.Persistencia;

/**
 * JFrame de interfaz ara averiguar maximos y minimos
 * 
 * @author Daniel Parra
 *
 */
public class InterfazMaxMin extends JFrame implements ActionListener {

	private JComboBox maxMin;
	private JButton btnVolver, btnPromedio;
	private JLabel txtPromedio, letrero, letrero1;
	private Window window;
	private Logica logica;
	private Agenda miAgenda;
	private int contador;
	private Graphics g;

	public InterfazMaxMin(Window window, Image icon, Agenda age, Logica logica) {
		miAgenda = age;
		setLayout(null);
		this.logica = logica;
		this.window = window;
		contador = 0;

		btnPromedio = new JButton("MAX-MIN");
		btnPromedio.setBounds(200, 30, 100, 20);
		btnPromedio.addActionListener(this);
		add(btnPromedio);

		maxMin = new JComboBox();
		maxMin.setBounds(90, 30, 90, 20);
		maxMin.addItem("Telefono");
		maxMin.addItem("Fechas");
		maxMin.addItem("Edad");
		maxMin.addItem("Nombre");
		maxMin.addItem("Correo");
		add(maxMin);

		letrero = new JLabel("Maximo: ");
		letrero.setBounds(90, 80, 300, 20);
		letrero.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15));
		letrero.setForeground(java.awt.Color.decode("#000080"));
		add(letrero);

		letrero1 = new JLabel("Minimo: ");
		letrero1.setBounds(90, 115, 300, 20);
		letrero1.setForeground(java.awt.Color.decode("#000080"));
		letrero1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15));
		add(letrero1);

		btnVolver = new JButton("<= Volver");
		btnVolver.setBounds(30, 160, 90, 20);
		btnVolver.addActionListener(this);
		add(btnVolver);

		setIconImage(icon);
		setSize(400, 230);
		setTitle("MAX-MIN");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);


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

		if (e.getSource() == btnPromedio && maxMin.getSelectedItem() == "Telefono") {
			int[] n = null;
			try {
				n = logica.maximoYminimo(miAgenda.getContactos(), 0);
				letrero.setText("Maximo: " + miAgenda.getContactos().get(n[1]).getNumeroTel());
				letrero1.setText("Minimo: " + miAgenda.getContactos().get(n[0]).getNumeroTel());
			} catch (Exception e1) {

			}
		}
		if (e.getSource() == btnPromedio && maxMin.getSelectedItem() == "Edad") {
			int[] n = null;
			try {
				n = logica.maximoYminimo(miAgenda.getContactos(), 2);
				letrero.setText("Maximo: " + miAgenda.getContactos().get(n[1]).getEdad());
				letrero1.setText("Minimo: " + miAgenda.getContactos().get(n[0]).getEdad());
			} catch (Exception e1) {

			}
		}
		if (e.getSource() == btnPromedio && maxMin.getSelectedItem() == "Nombre") {
			int[] n = null;
			try {
				n = logica.maximoYminimo(miAgenda.getContactos(), 3);
				letrero.setText("Maximo: " + miAgenda.getContactos().get(n[1]).getNombre());
				letrero1.setText("Minimo: " + miAgenda.getContactos().get(n[0]).getNombre());
			} catch (Exception e1) {

			}
		}
		if (e.getSource() == btnPromedio && maxMin.getSelectedItem() == "Correo") {
			int[] n = null;
			try {
				n = logica.maximoYminimo(miAgenda.getContactos(), 4);
				letrero.setText("Maximo: " + miAgenda.getContactos().get(n[1]).getCorreo());
				letrero1.setText("Minimo: " + miAgenda.getContactos().get(n[0]).getCorreo());
			} catch (Exception e1) {

			}

		}
		if (e.getSource() == btnPromedio && maxMin.getSelectedItem() == "Fechas") {
			try {

				letrero1.setText("Minimo: " + logica.ordenarFecha().get(logica.ordenarFecha().size()-1).getFecha().toString());
				letrero.setText("Maximo: " + logica.ordenarFecha().get(0).getFecha().toString());

			} catch (Exception a) {

				a.printStackTrace();
				
			}

		}

		if (e.getSource() == btnVolver) {
			letrero.setText("Maximo: ");
			letrero1.setText("Minimo: ");
			setVisible(false);
			window.setVisible(true);
		}

	}
}
package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import logica.Agenda;
import logica.Logica;
import logica.Persistencia;

/**
 * Interfaz para averiguar promedios
 * 
 * @author Daniel Parra - Andres Mosquera
 *
 */
public class InterfazPromedio extends JFrame implements ActionListener {

	private JComboBox promedio;
	private JButton btnVolver, btnPromedio;
	private JLabel txtPromedio, letrero;
	private Window window;
	private Logica lo;
	private Agenda miAgenda;

	public InterfazPromedio(Window window, Image icon, Agenda agen, Logica logica) {
		miAgenda = agen;
		setLayout(null);
		this.lo = logica;
		this.window = window;

		btnPromedio = new JButton("Promedio");
		btnPromedio.setBounds(200, 30, 100, 20);
		btnPromedio.addActionListener(this);
		add(btnPromedio);

		promedio = new JComboBox();
		promedio.setBounds(90, 30, 90, 20);
		promedio.addItem("Telefono");
		promedio.addItem("Edad");
		add(promedio);

		letrero = new JLabel("Promedio: ");
		letrero.setBounds(90, 80, 180, 20);
		letrero.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 15));
		letrero.setForeground(java.awt.Color.decode("#000080"));
		add(letrero);

		btnVolver = new JButton("<= Volver");
		btnVolver.setBounds(30, 130, 90, 20);
		btnVolver.addActionListener(this);
		add(btnVolver);

		this.setIconImage(icon);
		setSize(400, 200);
		setTitle("Promedio");
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
		// TODO Auto-generated method stub

		if (e.getSource() == btnPromedio && promedio.getSelectedItem() == "Telefono"
				&& miAgenda.getContactos() != null) {
			long n = lo.promedio(miAgenda.getContactos(), -1);
			letrero.setText("Promedio: " + n);

		}
		if (e.getSource() == btnPromedio && promedio.getSelectedItem() == "Edad" && miAgenda.getContactos() != null) {
			long n = lo.promedio(miAgenda.getContactos(), 1);
			letrero.setText("Promedio: " + n);
		}

		if (e.getSource() == btnVolver) {
			letrero.setText("Promedio: ");
			setVisible(false);
			window.setVisible(true);
		}

	}

}
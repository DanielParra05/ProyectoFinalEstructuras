package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import listaEnlazada.ListaEnlazada;
import logica.Agenda;
import logica.Contacto;
import logica.Fecha;
import logica.Logica;
import logica.Persistencia;
/**
 * Ventana para realizar la busqueda por fecha
 * @author Daniel Parra - Andres Mosquera
 *
 */
public class WindowDate extends JFrame implements ActionListener {
	private JComboBox year, month, day;
	private JLabel txtYear, txtMonth, txtDay, txtDate;
	private JList lista;
	private JScrollPane scroll;
	private JButton btnVolver, btnBuscar;
	private Window ven;
	private Agenda miAgenda;
	private Logica logica;
	private ListaEnlazada<Contacto> listaActual;
	private String[] a = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };

	public WindowDate(Window v, Image icon, Agenda miAgenda, Logica logica) {

		setLayout(null);

		this.logica = logica;
		this.ven = v;
		this.miAgenda = miAgenda;
		this.listaActual = miAgenda.getContactos();

		lista = new JList<>();

		DefaultListModel b = new DefaultListModel<>();
		for (Contacto contacto : listaActual) {
			b.addElement(contacto);
		}
		lista.setModel(b);

		scroll = new JScrollPane(lista);
		scroll.setBounds(40, 120, 330, 350);
		add(scroll);

		txtDate = new JLabel("Fecha de nacimiento:");
		txtDate.setBounds(40, 20, 150, 30);
		add(txtDate);

		txtYear = new JLabel("Año:");
		txtYear.setBounds(50, 50, 40, 20);
		add(txtYear);

		year = new JComboBox<>();
		year.setBounds(50, 80, 55, 20);
		for (int j = 2016; j > 1909; j--) {
			year.addItem(j);
		}
		year.addActionListener(this);
		add(year);

		txtMonth = new JLabel("Mes:");
		txtMonth.setBounds(130, 50, 40, 20);
		add(txtMonth);

		month = new JComboBox<>();
		month.setBounds(130, 80, 85, 20);
		for (int j = 0; j < a.length; j++) {
			month.addItem(a[j]);
		}
		month.addActionListener(this);
		add(month);

		txtDay = new JLabel("Dia");
		txtDay.setBounds(240, 50, 40, 20);
		add(txtDay);

		day = new JComboBox<>();
		day.setBounds(240, 80, 40, 20);
		for (int i = 1; i < 32; i++) {
			day.addItem(i);
		}
		add(day);

		setSize(400, 600);
		setTitle("Busqueda por fecha");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage((Image) icon);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(200, 500, 120, 30);
		add(btnVolver);
		btnVolver.addActionListener(this);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(70, 500, 120, 30);
		add(btnBuscar);
		btnBuscar.addActionListener(this);
		

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

		if (e.getSource() == btnBuscar) {

			ListaEnlazada<Contacto> contactos = logica.buscarPorFecha(new Fecha((Integer) year.getSelectedItem(),
					(String) month.getSelectedItem(), (Integer) day.getSelectedItem()), listaActual);
				DefaultListModel b = new DefaultListModel<>();
				for (Contacto contacto : contactos) {
					b.addElement(contacto);
				}
				lista.setModel(b);

				if (contactos.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Su busqueda no ha retornado resultados!");
				} 
			
		}

		if (e.getSource() == btnVolver) {
			ven.setVisible(true);
			this.setVisible(false);
		}

	}

}

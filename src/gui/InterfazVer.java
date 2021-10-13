package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import logica.Agenda;
import logica.Contacto;
import logica.ContactoCache;
import logica.Fecha;
import logica.Persistencia;

/**
 * Interfaz para ver y editar los contacos
 * 
 * @author Daniel Parra - Andres Mosquera
 *
 */
public class InterfazVer extends JFrame implements ActionListener, ItemListener, KeyListener {

	private Window window;
	private Contacto contacto;
	private JLabel txtNombre, txtSexo, txtTelefono, txtFecha, txtEdad, txtCorreo, txtAnio, txtMes, txtDia;
	private JTextField cmpNombre, cmpTelefono, cmpCorreo;
	private JComboBox sex, year, month, day;
	private JButton btnGuardar, btnVolver;
	private JCheckBox check1;
	private Agenda miAgenda;
	private String[] a = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
			"Octubre", "Noviembre", "Diciembre" };

	public InterfazVer(Contacto contacto, Window window, Image icon, Agenda miAgenda) {

		setLayout(null);

		this.contacto = contacto;
		this.window = window;
		this.miAgenda = miAgenda;

		check1 = new JCheckBox("Habilitar edicion");
		check1.setBounds(30, 8, 120, 30);
		check1.addItemListener(this);
		add(check1);

		txtNombre = new JLabel("Nombre: ");
		txtNombre.setBounds(30, 30, 120, 40);
		add(txtNombre);

		cmpNombre = new JTextField(contacto.getNombre());
		cmpNombre.setBounds(90, 40, 160, 20);
		add(cmpNombre);

		txtSexo = new JLabel("Sexo: ");
		txtSexo.setBounds(30, 60, 120, 40);
		add(txtSexo);

		txtEdad = new JLabel("Edad: " + contacto.getEdad() + " años");
		txtEdad.setBounds(200, 60, 120, 40);
		add(txtEdad);

		sex = new JComboBox();
		sex.setBounds(90, 70, 100, 20);
		sex.addItem("Masculino");
		sex.addItem("Femenino");
		sex.setSelectedItem(contacto.getSex());
		add(sex);

		txtTelefono = new JLabel("Telefono:");
		txtTelefono.setBounds(30, 90, 120, 40);
		add(txtTelefono);

		cmpTelefono = new JTextField("" + contacto.getNumeroTel());
		cmpTelefono.setBounds(90, 100, 160, 20);
		add(cmpTelefono);

		txtCorreo = new JLabel("Correo:");
		txtCorreo.setBounds(30, 120, 120, 40);
		add(txtCorreo);

		cmpCorreo = new JTextField(contacto.getCorreo());
		cmpCorreo.setBounds(90, 130, 160, 20);
		add(cmpCorreo);

		txtFecha = new JLabel("Fecha de nacimiento:");
		txtFecha.setBounds(30, 150, 130, 40);
		add(txtFecha);

		txtAnio = new JLabel("Año:");
		txtAnio.setBounds(30, 180, 130, 40);
		add(txtAnio);

		txtMes = new JLabel("Mes:");
		txtMes.setBounds(130, 180, 130, 40);
		add(txtMes);

		txtDia = new JLabel("Dia:");
		txtDia.setBounds(259, 180, 130, 40);
		add(txtDia);

		year = new JComboBox<>();
		year.setBounds(60, 190, 55, 20);
		for (int i = 2016; i > 1909; i--) {
			year.addItem(i);
		}
		year.addActionListener(this);
		add(year);

		month = new JComboBox<>();
		month.setBounds(160, 190, 85, 20);
		for (int i = 0; i < a.length; i++) {
			month.addItem(a[i]);
		}
		month.addActionListener(this);
		add(month);

		day = new JComboBox<>();
		day.setBounds(280, 190, 40, 20);
		add(day);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(20, 310, 120, 30);
		add(btnGuardar);
		btnGuardar.addActionListener(this);

		

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(210, 310, 120, 30);
		add(btnVolver);
		btnVolver.addActionListener(this);

		month.setSelectedItem(contacto.getFecha().getMes());
		year.setSelectedItem(contacto.getFecha().getAnio());
		day.setSelectedItem(contacto.getFecha().getDia());
		day.addActionListener(this);

		// Caracteristicas ventana
		setSize(376, 400);
		setTitle("Ver y editar contacto");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setIconImage(icon);
		this.addKeyListener(this);
		bloquear();
		

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
		if (e.getSource() == btnVolver) {

			setVisible(false);
		    window.setVisible(true);

		}

		if (e.getSource() == btnGuardar) {

			if (!cmpNombre.getText().trim().equals("") && !cmpCorreo.getText().trim().equals("")
					&& !cmpTelefono.getText().trim().equals("")) {

				String nombre = cmpNombre.getText();
				String correo = cmpCorreo.getText();
				String sexo = sex.getSelectedItem().toString();
				Fecha fecha = new Fecha((Integer) year.getSelectedItem(), (String) month.getSelectedItem(),
						(Integer) day.getSelectedItem());
				try {
					long telefono = Long.parseLong(cmpTelefono.getText());
					int i = miAgenda.buscarPorId(contacto.getId());

					if (i != -1) {
						try {

							miAgenda.getContactos().get(i).setCorreo(correo);
							miAgenda.getContactos().get(i).setEdad((Integer) year.getSelectedItem() - 2016);
							miAgenda.getContactos().get(i).setFecha(fecha);
							miAgenda.getContactos().get(i).setNombre(nombre);
							miAgenda.getContactos().get(i).setNumeroTel(telefono);
							miAgenda.getContactos().get(i).setSex(sexo);
						} catch (Exception a) {

							a.printStackTrace();

						}
						check1.setSelected(false);
						window.actualizarJList();
					}

					JOptionPane.showMessageDialog(null, "Contacto editado exitosamente!", "",
							JOptionPane.WARNING_MESSAGE);

					setVisible(false);
					window.setVisible(true);

				} catch (NumberFormatException a) {

					JOptionPane.showMessageDialog(null, "Verifique el campo telefono!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "No deje campos en blanco", "Error", JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	@Override
	public void itemStateChanged(ItemEvent a) {
		// TODO Auto-generated method stub
		if (check1.isSelected()) {

			desbloquear();

		} else {

			bloquear();

		}
	}

	/**
	 * Bloquea todos los campos de texto seleccionados
	 */
	private void bloquear() {

		cmpNombre.setEnabled(false);
		cmpTelefono.setEnabled(false);
		cmpCorreo.setEnabled(false);
		year.setEnabled(false);
		month.setEnabled(false);
		btnGuardar.setEnabled(false);
		day.setEnabled(false);
		sex.setEnabled(false);

	}

	/**
	 * Desbloquea los campos de texto
	 */
	private void desbloquear() {

		btnGuardar.setEnabled(true);
		cmpNombre.setEnabled(true);
		cmpTelefono.setEnabled(true);
		cmpCorreo.setEnabled(true);
		year.setEnabled(true);
		month.setEnabled(true);
		day.setEnabled(true);
		sex.setEnabled(true);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub


		if (e.getKeyCode() == e.VK_ESCAPE) {

			window.setVisible(true);
			setVisible(false);
			
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}

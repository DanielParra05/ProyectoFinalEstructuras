package logica;

import java.io.Serializable;
/**
 * Fecha de nacimiento de los contactos
 * @author Daniel Parra - Andres Mosquera
 *
 */
public class Fecha implements Serializable {

	private String mes;
	private int dia, anio;

	public Fecha(int anio, String mes, int dia) {

		this.mes = mes;
		this.dia = dia;
		this.anio = anio;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	/**
	 * Evalua si una fecha es mayor que otra o igual
	 * 
	 * @param fecha,
	 *            fecha a comparar fecha2
	 * @return 0 si son iguales, 1 si fecha 1 es mayor que fecha 2, -1 si fecha
	 *         1 es meonr a fecha 2
	 */

	public int evaluaFecha(Fecha fecha) {
		if (fecha.getAnio() == anio && fecha.getDia() == dia && fecha.getMes().equalsIgnoreCase(mes)) {
			return 0;
		} else if (fecha.getAnio() == anio) {

			if (fecha.getMes().equalsIgnoreCase(mes)) {

				if (dia > fecha.getDia()) {
					return 1;
				} else {
					return -1;
				}

			} else {
				String[] a = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
						"Octubre", "Noviembre", "Diciembre" };

				int b = 0; // Mes 1
				int c = 0; // Mes 2

				for (int i = 0; i < a.length; i++) {
					if (mes.equalsIgnoreCase(a[i])) {
						b = i;
					}

					if (fecha.getMes().equalsIgnoreCase(a[i])) {
						c = i;
					}
				}

				if (b > c) {
					return 1;
				} else {
					return -1;
				}

			}

		} else {
			if (anio > fecha.getAnio()) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	/**
	 * Para saber si un anio es bisiesto
	 * @param n anio
	 * @return true si es bisiesto, de lo contrario false
	 */
	public boolean anioBisiesto(int n) {
		if (n % 400 == 0 || n % 4 == 0 && n % 100 != 0) {
			return true;
		}
		return false;
	}

	/**
	 * Asigna los dias correspondientes a un mes segun el año
	 * @param year año del mes
	 * @param month mes al que se le van a asignar dias segun el año
	 * @return dias del mes
	 */
	public int mes(int year, String month) {
		boolean fe = anioBisiesto(year);
		if (fe && month.equalsIgnoreCase("febrero")) {
			return 29;
		}
		if (!fe && month.equalsIgnoreCase("febrero")) {
			return 28;
		}
		if (month.equalsIgnoreCase("noviembre") || month.equalsIgnoreCase("abril") || month.equalsIgnoreCase("junio")
				|| month.equalsIgnoreCase("septiembre")) {
			return 30;
		} else {
			return 31;
		}

	}

	public int getDia() {
		return dia;
	}

	public void setDia(int dia) {
		this.dia = dia;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	@Override
	public String toString() {
		return dia + " de " + mes + " de " + anio;
	}

}

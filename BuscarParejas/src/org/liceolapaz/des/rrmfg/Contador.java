package org.liceolapaz.des.rrmfg;

import javax.swing.JTextField;

public class Contador extends Thread {

	private int segundos;
	private boolean parar;
	private JTextField textField;

	public Contador (JTextField textField) {
		this.textField = textField;
		parar = false;
		segundos = 0;
	}

	public void run() {
		while (!parar) {
				try {
					textField.setText(rellenarCeros(segundos, 3));
					sleep(1000);
					segundos++;		
				} catch(InterruptedException ie) {}
		}
	}

	public void parar() {
		parar = true;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
		textField.setText(rellenarCeros(segundos, 3));
	}

	public int getSegundos() {
		return segundos;
	}

	public static String rellenarCeros(int numero, int total) {
		String texto = new String();

		texto += numero;
		while (texto.length() < total) {
			texto = "0" + texto;
		}

		return texto;
	}

}

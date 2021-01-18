package com.liceolapaz.des.RRMFG;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Boton extends JButton {
	private static final ImageIcon ICONO_CIRCULO = new ImageIcon(Boton.class.getResource("/circulo.png"));
	private static final ImageIcon ICONO_CRUZ = new ImageIcon(Boton.class.getResource("/cruz.png"));

	public static final int VACIO = 0;
	public static final int CIRCULO = 1;
	public static final int CRUZ = 2;

	private Tablero tablero;
	private int fila;
	private int columna;
	private int estado;
	private boolean pulsado;

	public Boton(Tablero tablero, int fila, int columna) {
		super();
		this.tablero = tablero;
		this.fila = fila;
		this.columna = columna;
		this.estado = Boton.VACIO;
		this.pulsado = false;
		setBackground(Color.BLACK);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!Boton.this.pulsado) {
					pulsar();
				}
			}
		});
	}

	public int getColumna() {
		return this.columna;
	}

	public int getEstado() {
		return this.estado;
	}

	public int getFila() {
		return this.fila;
	}

	private void pulsar() {
		int turno = this.tablero.getTurno();
		if (turno == Tablero.CIRCULO) {
			setIcon(Boton.ICONO_CIRCULO);
			this.estado = CIRCULO;
		} else if (turno == Tablero.CRUZ) {
			setIcon(Boton.ICONO_CRUZ);
			this.estado = CRUZ;
		}
		setBackground(Color.WHITE);
		this.pulsado = true;
		this.tablero.pulsarBoton(this);
	}
}

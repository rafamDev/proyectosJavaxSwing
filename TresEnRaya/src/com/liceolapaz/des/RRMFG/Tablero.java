
package com.liceolapaz.des.RRMFG;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class Tablero extends JPanel {
	
	public static final int CIRCULO = 1;
	public static final int CRUZ = 2;
	public static final int EMPATE = 3;

	private static int generarNumeroAleatorio(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}
    //(Math.random() * 2) + 1; nextInt(2)+1;
	//(0,1) 1-0, +1 = (enre 0 y 1) + 0 desde 0 hasta 1
	//(2,5) 5-2 3+1 (entre 0 y 3) + 2 entre 2 y 5.
	
	private Ventana ventana;
	private int filas;
	private int columnas;
	private int turno;
	private int pulsados;
	private Boton[][] botones;

	public Tablero(Ventana ventana, int filas, int columnas) {
		super();
		this.ventana = ventana;
		this.filas = filas;
		this.columnas = columnas;
		this.pulsados = 0;
		setBorder(new LineBorder(Color.RED, 5));
		setLayout(new GridLayout(filas, columnas));
		crearBotones();
		generarTurno();
	}

	private void cambiarTurno() {
		if (this.turno == CIRCULO) {
			this.turno = CRUZ;
		} else if (this.turno == CRUZ) {
			this.turno = CIRCULO;
		}
		this.ventana.cambiarTurno(this.turno);
	}

	public boolean comprobar(int jugador) {
		if ((this.botones[0][0].getEstado() == jugador) && (this.botones[0][1].getEstado() == jugador)
				&& (this.botones[0][2].getEstado() == jugador)) {
			
			return true;
		
		} else if ((this.botones[1][0].getEstado() == jugador) && (this.botones[1][1].getEstado() == jugador)
				&& (this.botones[1][2].getEstado() == jugador)) {
			
			return true;
		
		
		} else if ((this.botones[2][0].getEstado() == jugador) && (this.botones[2][1].getEstado() == jugador)
				&& (this.botones[2][2].getEstado() == jugador)) {
			
			return true;
		
		} else if ((this.botones[0][0].getEstado() == jugador) && (this.botones[1][0].getEstado() == jugador)
				&& (this.botones[2][0].getEstado() == jugador)) {
			
			return true;
		
		} else if ((this.botones[0][1].getEstado() == jugador) && (this.botones[1][1].getEstado() == jugador)
				&& (this.botones[2][1].getEstado() == jugador)) {
			
			return true;
		
		} else if ((this.botones[0][2].getEstado() == jugador) && (this.botones[1][2].getEstado() == jugador)
				&& (this.botones[2][2].getEstado() == jugador)) {
			
			return true;
		
		} else if ((this.botones[0][0].getEstado() == jugador) && (this.botones[1][1].getEstado() == jugador)
				&& (this.botones[2][2].getEstado() == jugador)) {
			
			return true;
		
		} else if ((this.botones[0][2].getEstado() == jugador) && (this.botones[1][1].getEstado() == jugador)
				&& (this.botones[2][0].getEstado() == jugador)) {
			
			return true;
		}
		
		return false;
	}

	private void comprobarFin() {
		int resultado = 0;
		if (comprobar(Tablero.CIRCULO)) {
			resultado = Tablero.CIRCULO;
		} else if (comprobar(Tablero.CRUZ)) {
			resultado = Tablero.CRUZ;
		} else if (this.pulsados == 9) {
			resultado = Tablero.EMPATE;
		} else {
			return;
		}
		fin(resultado);
	}

	private void crearBotones() {
	  this.botones = new Boton[this.filas][this.columnas];
		for (int fila = 0; fila < this.filas; fila++) {
			for (int columna = 0; columna < this.columnas; columna++) {
				this.botones[fila][columna] = new Boton(this, fila, columna);
				add(this.botones[fila][columna]);
			}
		}
	}

	private void fin(int resultado) {
		for (int fila = 0; fila < this.filas; fila++) {
			for (int columna = 0; columna < this.columnas; columna++) {
				this.botones[fila][columna].setEnabled(false);
			}
		}
		this.ventana.fin(resultado);
	}

	private void generarTurno() {
		this.turno = generarNumeroAleatorio(1, 2);
		this.ventana.cambiarTurno(this.turno);
	}

	public int getTurno() {
		return this.turno;
	}

	public void pulsarBoton(Boton boton) {
		this.botones[boton.getFila()][boton.getColumna()] = boton;
		this.pulsados++;
		comprobarFin();
		cambiarTurno();
	}
}

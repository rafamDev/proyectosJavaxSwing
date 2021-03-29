package org.liceolapaz.des.rrmfg;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class Boton extends JButton {

	public static final int TAPADO = 0;
	public static final int DESTAPADO = 1;

	private Tablero tablero;
	private int estado;
	private int valor;
	private int fila;
    private int columna;
	
	public Boton(Tablero tablero, int fila, int columna) {
		super();
		this.tablero = tablero;
		this.estado = TAPADO;
		this.valor = 0;
		this.fila = fila;
		this.columna = columna;
		setBackground(Color.BLACK);
		setText("");
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {   
					pulsar();
				}
			}
		});
	}

	public void pulsar() {
		if (estado == TAPADO) {
			setBackground(Color.WHITE);
			setText("" + valor);
			estado = DESTAPADO;
			tablero.destapar(this);
		}
	}
	
	public void tapar() {
	    if (estado == DESTAPADO) {
            setBackground(Color.BLACK);
            setText("");
            estado = TAPADO;
        }
	}
	
	private void destapar() {
        setBackground(Color.WHITE);
        setText("" + valor);
        estado = DESTAPADO;        
    }
	
	public int getFila() {
        return fila;
    }
    
    public int getColumna() {
        return columna;
    }
	
	public int getEstado() {
		return estado;
	}
	
	public void setEstado(int estado) {
        this.estado = estado;
    }
	
	public void actualizarEstado(int estado) {
        if (estado == DESTAPADO) {
            destapar();
        }
    }

    public int getValor() {
        return valor;
    }
	
	public void setValor(int valor) {
        this.valor = valor;
    }

}

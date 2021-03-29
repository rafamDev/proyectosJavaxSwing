package org.liceolapaz.des.rrmfg;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Dialogo extends JDialog {
	
	private Ventana ventana;
	private int nivelDificultad;
	private JPanel panelArriba;
	private JPanel panelCentro;
	private JPanel panelAbajo;
	private JLabel lbFilas;
	private JTextField txtFilas;
	private JLabel lbColumnas;
	private JTextField txtColumnas;
	private JRadioButton rbFacil;
	private JRadioButton rbMedio;
	private JRadioButton rbDificil;
	private JRadioButton rbPersonalizado;
	private ButtonGroup grupo;
	private JButton bAceptar;
	private JButton bCancelar;
	private static final int WIDTH = 350;
	private static final int HEIGHT = 250;

	public Dialogo(Ventana ventana, int nivelDificultad) {
		super(ventana, "Nivel de dificultad", true);
		setLayout(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Point posicion = new Point();
		posicion.setLocation(ventana.getLocationOnScreen().getX()+200, ventana.getLocationOnScreen().getY()+100);
		setLocation(posicion);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		this.ventana = ventana;
		this.nivelDificultad = nivelDificultad;
		
		inicializarPanel();
	}
	
	private void inicializarPanel() {		
		panelArriba = new JPanel();
		panelArriba.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelArriba.setBounds(0, 0, WIDTH, 50);
		
		rbFacil = new JRadioButton("Fácil");
		rbFacil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				inicializarTextos(Ventana.FACIL);
			}
		});
		rbMedio = new JRadioButton("Medio");
		rbMedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				inicializarTextos(Ventana.MEDIO);
			}
		});
		rbDificil = new JRadioButton("Difícil");
		rbDificil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				inicializarTextos(Ventana.DIFICIL);
			}
		});
		rbPersonalizado = new JRadioButton("Personalizado");
		rbPersonalizado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				inicializarTextos(Ventana.PERSONALIZADO);
			}
		});
		
		grupo = new ButtonGroup();
		grupo.add(rbFacil);
		grupo.add(rbMedio);
		grupo.add(rbDificil);
		grupo.add(rbPersonalizado);
		switch (nivelDificultad) {
		case Ventana.FACIL:
			rbFacil.setSelected(true);
			break;
		case Ventana.MEDIO:
			rbMedio.setSelected(true);
			break;
		case Ventana.DIFICIL:
			rbDificil.setSelected(true);
			break;
		case Ventana.PERSONALIZADO:
			rbPersonalizado.setSelected(true);
			break;
		default:
			rbFacil.setSelected(true);
		}
		
		panelArriba.add(rbFacil);
		panelArriba.add(rbMedio);
		panelArriba.add(rbDificil);
		panelArriba.add(rbPersonalizado);
		
		panelCentro = new JPanel(new GridLayout(2, 2, 5, 5));
		panelCentro.setBounds(10, 60, WIDTH - 20, 50);
		
		lbFilas = new JLabel("Filas");
        txtFilas = new JTextField("", 3);
        txtFilas.setEditable(false);
        txtFilas.setText("" + ventana.getFilas());
        lbColumnas = new JLabel("Columnas");
        txtColumnas = new JTextField("", 3);
        txtColumnas.setEditable(false);
        txtColumnas.setText("" + ventana.getColumnas());
		
		inicializarTextos(nivelDificultad);
		
		panelCentro.add(lbFilas);
		panelCentro.add(txtFilas);
		panelCentro.add(lbColumnas);
		panelCentro.add(txtColumnas);
		
		panelAbajo = new JPanel();
		panelAbajo.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelAbajo.setBounds(0, 150, WIDTH, 50);
		
		bAceptar = new JButton("Aceptar");
		bAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int filas = new Integer(txtFilas.getText());
				int columnas = new Integer(txtColumnas.getText());
				      
			    
				if ((filas * columnas) % 2 == 0) {
					if (rbFacil.isSelected()) {
						nivelDificultad = Ventana.FACIL;
					} else if (rbMedio.isSelected()) {
						nivelDificultad = Ventana.MEDIO;
					} else if (rbDificil.isSelected()) {
						nivelDificultad = Ventana.DIFICIL;
					} else if (rbPersonalizado.isSelected()) {
						nivelDificultad = Ventana.PERSONALIZADO;
					}
					ventana.setNivelDificultad(nivelDificultad);
					ventana.nuevaPartida(filas, columnas);
					dispose();
				} else {
					JOptionPane.showMessageDialog(Dialogo.this, "El número de casillas debe ser par", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panelAbajo.add(bAceptar);
		
		bCancelar = new JButton("Cancelar");
		bCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		panelAbajo.add(bCancelar);
		
		add(panelArriba);
		add(panelCentro);
		add(panelAbajo);
	}

	private void inicializarTextos(int nivelDificultad) {
		if (nivelDificultad == Ventana.PERSONALIZADO) {
			txtFilas.setEditable(true);
			txtColumnas.setEditable(true);
		} else {
			txtFilas.setEditable(false);
			txtColumnas.setEditable(false);
			switch (nivelDificultad) {
			case Ventana.FACIL:
				txtFilas.setText("3");
				txtColumnas.setText("4");
				break;
				
			case Ventana.MEDIO:
				txtFilas.setText("4");
				txtColumnas.setText("5");
				break;
				
			case Ventana.DIFICIL:
				txtFilas.setText("6");
				txtColumnas.setText("6");
				break;
			}
		}
		
	}
}

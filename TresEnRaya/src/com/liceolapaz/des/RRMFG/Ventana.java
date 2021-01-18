package com.liceolapaz.des.RRMFG;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;



public class Ventana extends JFrame {
	private Tablero tablero;

	public Ventana() {
		super();
		setTitle("Tres En Raya");
		setSize(300, 300);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		URL url = getClass().getResource("/icono.png");
		setIconImage(new ImageIcon(url).getImage());
		crearMenu();
		crearTablero();
	}

	public void cambiarTurno(int turno) {
		if (turno == Tablero.CIRCULO) {
			setTitle("Jugador 1 (Círculo)");
		} else if (turno == Tablero.CRUZ) {
			setTitle("Jugador 2 (Cruz)");
		}
	}

	private void crearMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuPartida = new JMenu("Partida");
		menuPartida.setMnemonic(KeyEvent.VK_P);
		JMenuItem nuevaPartida = new JMenuItem("Nueva partida");
		nuevaPartida.setIcon(new ImageIcon(getClass().getResource("/icono.png")));
		nuevaPartida.setMnemonic(KeyEvent.VK_N);
		nuevaPartida.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		nuevaPartida.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nuevaPartida();
			}
		});
		menuPartida.add(nuevaPartida);
		JMenuItem salir = new JMenuItem("Salir");
		salir.setIcon(new ImageIcon(getClass().getResource("/salir.png")));
		salir.setMnemonic(KeyEvent.VK_S);
		salir.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		salir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuPartida.add(salir);
		menuBar.add(menuPartida);
		setJMenuBar(menuBar);
	}

	private void crearTablero() {
		if (this.tablero != null) {
			remove(this.tablero);
		}
		this.tablero = new Tablero(this, 3, 3);
		add(this.tablero);
		revalidate();
	}

	public void fin(int resultado) {
		String mensaje = "";
		switch (resultado) {
		case Tablero.CIRCULO:
			mensaje = "Ha ganado el jugador 1 (Círculo)";
			break;
		case Tablero.CRUZ:
			mensaje = "Ha ganado el jugador 2 (Cruz)";
			break;
		case Tablero.EMPATE:
			mensaje = "La partida ha terminado en empate";
			break;
		}
		JOptionPane.showMessageDialog(this, mensaje);
	}

	private void nuevaPartida() {
		crearTablero();
	}
}

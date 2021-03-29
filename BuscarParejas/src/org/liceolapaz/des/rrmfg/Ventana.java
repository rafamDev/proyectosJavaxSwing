package org.liceolapaz.des.rrmfg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Ventana extends JFrame {
    
    public static final int PERSONALIZADO = 0;
    public static final int FACIL = 1;
    public static final int MEDIO = 2;
    public static final int DIFICIL = 3;
    
    private static final String RUTA_RESULTADOS = "C:\\Users\\rafam\\Desktop\\resultados";
	
    private JPanel panel;
    private Tablero tablero;
    private JMenuBar menubar;
    private Dialogo dialogo;
    private JPanel abajo;
    private Contador contador;
    private boolean resultados;
    private int nivelDificultad;
	private JTextField txtIntentos;
	private JTextField txtParejas;
	private JTextField txtTiempo;
	
	public Ventana() {
	    super();
        setTitle("Buscar Parejas - Rafael Rodolfo Mayo Fernández-Getino");
        setIconImage(new ImageIcon(getClass().getResource("/logo.png")).getImage());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout(50, 50));

        resultados = false;
        nivelDificultad = FACIL;

        inicializarPanel();
        setVisible(true);
	}
	
	private void inicializarPanel() {
        panel = new JPanel();
        add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        panel.setBorder(new LineBorder(Color.BLACK, 5));

        JLabel titulo = new JLabel("Buscar Parejas");
        panel.add(titulo);
        titulo.setFont(new Font(Font.SERIF, Font.BOLD, 36));
        titulo.setBounds(300, 20, 500, 50);

        JButton imagen = new JButton(new ImageIcon(getClass().getResource("/imagen.png")));
        panel.add(imagen);
        imagen.setBounds(250, 100, 305, 305);
        imagen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setResizable(true);
                remove(panel);
                inicializarMenu();
                inicializarTablero(3, 4, -1, 0, null);
                inicializarPanelAbajo();
                inicializarContador();
            }
        });

        JLabel mensaje = new JLabel("Pulse en la imagen para empezar a jugar");
        panel.add(mensaje);
        mensaje.setFont(new Font(Font.SERIF, Font.PLAIN, 24));
        mensaje.setBounds(200, 400, 500, 50);

        JLabel nombre = new JLabel("Autor: Rafael Rodolfo Mayo fernandez-Getino");
        panel.add(nombre);
        nombre.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        nombre.setBounds(220, 480, 500, 30);        
    }

    private void inicializarMenu() {
        menubar = new JMenuBar();
        setJMenuBar(menubar);

        JMenu menuPartida = new JMenu("Partida");
        menubar.add(menuPartida);
        menuPartida.setMnemonic(KeyEvent.VK_P);

        JMenuItem nuevaPartida = new JMenuItem("Nueva partida");
        menuPartida.add(nuevaPartida);
        nuevaPartida.setIcon(new ImageIcon(getClass().getResource("/nueva.png")));
        nuevaPartida.setMnemonic(KeyEvent.VK_N);
        nuevaPartida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                ActionEvent.CTRL_MASK));
        nuevaPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                inicializarContador();
                inicializarTablero(tablero.getFilas(), tablero.getColumnas(), -1, 0, null);
                actualizarContadores();
            }
        });

        JMenuItem guardarPartida = new JMenuItem("Guardar partida");
        menuPartida.add(guardarPartida);
        guardarPartida.setIcon(new ImageIcon(getClass().getResource("/guardar.png")));
        guardarPartida.setMnemonic(KeyEvent.VK_G);
        guardarPartida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
                ActionEvent.CTRL_MASK));
        guardarPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                guardarPartida();
            }
        });

        JMenuItem cargarPartida = new JMenuItem("Cargar partida");
        menuPartida.add(cargarPartida);
        cargarPartida.setIcon(new ImageIcon(getClass().getResource("/cargar.png")));
        cargarPartida.setMnemonic(KeyEvent.VK_C);
        cargarPartida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                ActionEvent.CTRL_MASK));
        cargarPartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                cargarPartida();
            }
        });

        JMenuItem salir = new JMenuItem("Salir");
        menuPartida.add(salir);
        salir.setIcon(new ImageIcon(getClass().getResource("/salir.png")));
        salir.setMnemonic(KeyEvent.VK_S);
        salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.CTRL_MASK));
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        JMenu menuOpciones = new JMenu("Opciones");
        menubar.add(menuOpciones);
        menuOpciones.setMnemonic(KeyEvent.VK_O);

        JCheckBoxMenuItem almacenarResultados = new JCheckBoxMenuItem("Almacenar resultados");
        menuOpciones.add(almacenarResultados);
        almacenarResultados.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                resultados = !resultados;
            }
        });

        JMenuItem nivel = new JMenuItem("Nivel de dificultad");
        menuOpciones.add(nivel);
        nivel.setMnemonic(KeyEvent.VK_D);
        nivel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                ActionEvent.CTRL_MASK));
        nivel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                nivelDificultad();
            }
        });     
    }
    
    public void inicializarPanelAbajo() {
        abajo = new JPanel();
        abajo.setLayout(new GridLayout(1, 6, 10, 10));
        add(abajo, BorderLayout.SOUTH);

        JLabel lbIntentos = new JLabel("Intentos");
        lbIntentos.setBorder(new LineBorder(Color.BLACK, 5));
        lbIntentos.setHorizontalAlignment(JLabel.CENTER);
        abajo.add(lbIntentos);
        
        txtIntentos = new JTextField("0");
        abajo.add(txtIntentos);
        txtIntentos.setEditable(false);
        txtIntentos.setFont(new Font(Font.SERIF, Font.BOLD, 36));
        txtIntentos.setBorder(new LineBorder(Color.ORANGE, 5));
        txtIntentos.setHorizontalAlignment(JTextField.CENTER);
        
        JLabel lbParejas = new JLabel("Parejas");
        lbParejas.setBorder(new LineBorder(Color.BLACK, 5));
        lbParejas.setHorizontalAlignment(JLabel.CENTER);
        abajo.add(lbParejas);
        
        txtParejas = new JTextField("" + tablero.getParejasRestantes() , 3);
        abajo.add(txtParejas);
        txtParejas.setEditable(false);
        txtParejas.setFont(new Font(Font.SERIF, Font.BOLD, 36));
        txtParejas.setBorder(new LineBorder(Color.ORANGE, 5));
        txtParejas.setHorizontalAlignment(JTextField.CENTER);
        
        JLabel imagenTiempo = new JLabel(new ImageIcon(getClass().getResource("/reloj.png")));
        abajo.add(imagenTiempo);

        txtTiempo = new JTextField("000", 3);
        abajo.add(txtTiempo);
        txtTiempo.setEditable(false);
        txtTiempo.setFont(new Font(Font.SERIF, Font.BOLD, 36));
        txtTiempo.setBorder(new LineBorder(Color.ORANGE, 5));
        txtTiempo.setHorizontalAlignment(JTextField.CENTER);
    }

    public void inicializarTablero(int filas, int columnas, int parejasRestantes, int intentos, Boton[][] botones) {
        if (tablero != null) 
            remove(tablero);
        tablero = new Tablero(this, filas, columnas, parejasRestantes, intentos, botones);
        add(tablero, BorderLayout.CENTER);
        tablero.setBorder(new LineBorder(Color.RED, 5));
    }

    private void inicializarContador() {
        if (contador != null) {
            contador.parar();
        }
        contador = new Contador(txtTiempo);
        contador.start();
    }

    private void nivelDificultad() {
        dialogo = new Dialogo(this, nivelDificultad);
        dialogo.setVisible(true);
    }
    
    public void setNivelDificultad(int nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }
    
    public int getFilas() {
        if (tablero != null) {
            return tablero.getFilas();
        } else {
            return -1;
        }
    }

    public int getColumnas() {
        if (tablero != null) {
            return tablero.getColumnas();
        } else {
            return -1;
        }
    }
    
    public void actualizarContadores() {
        txtIntentos.setText("" + tablero.getIntentos());
        txtParejas.setText("" + tablero.getParejasRestantes());
    }
    
    public void fin(boolean ganar) {
        contador.parar();
        actualizarContadores();
        if (ganar) {
            if (resultados) {
                almacenarResultados();
            }
            JOptionPane.showMessageDialog(this, "¡¡¡Has ganado!!!");
        } else {
            JOptionPane.showMessageDialog(this, "Has perdido");
        }
    }

    private void almacenarResultados() {
        try {
            File fichero = new File(RUTA_RESULTADOS);
            FileWriter fw = new FileWriter(fichero, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            String usuario = JOptionPane.showInputDialog(this, "Introduzca su nombre: ");
            if (usuario != null) {
                String dificultad = "";
                switch (nivelDificultad) {
                case FACIL:
                    dificultad += "Fácil";
                    break;
                case MEDIO:
                    dificultad += "Medio";
                    break;
                case DIFICIL:
                    dificultad += "Difícil";
                    break;
                case PERSONALIZADO:
                    dificultad += "Personalizado";
                    break;
                }
                dificultad += "(Filas: " + tablero.getFilas() + ", Columnas: " + tablero.getColumnas() + ", Parejas: " + tablero.getParejas() + ")";
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String fecha = dateFormat.format(cal.getTime());
                String linea = "Usuario: " + usuario + " Intentos: " + tablero.getIntentos() + " Tiempo: " + contador.getSegundos() + " Nivel de dificultad: " + dificultad + " Fecha: " + fecha;
                pw.println(linea);
            }
            pw.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al almacenar resultados", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void guardarPartida() {
        File fichero;
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        jfc.setFileFilter(filter);
        if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            fichero = jfc.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(fichero);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                // Configuración
                String linea = "" + tablero.getFilas() + ";" + tablero.getColumnas() + ";" + tablero.getParejasRestantes() + ";" + tablero.getIntentos() + ";" + contador.getSegundos() + ";" + nivelDificultad;
                pw.println(linea);
                // Botones
                Boton[][] botones = tablero.getBotones();
                for (int i = 0; i < botones.length; i++) {
                    for (int j = 0; j < botones[i].length; j++) {
                        linea = "" + botones[i][j].getFila() + ";" + botones[i][j].getColumna() + ";" + botones[i][j].getEstado() + ";" + botones[i][j].getValor();
                        pw.println(linea);
                    }
                }
                pw.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error al guardar la partida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cargarPartida() {
        File fichero;
        JFileChooser jfc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        jfc.setFileFilter(filter);
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fichero = jfc.getSelectedFile();
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(fichero);
                br = new BufferedReader(fr);
                // Configuración
                String linea = br.readLine();
                String[] partes = linea.split(";");
                if (partes.length != 6) {
                    JOptionPane.showMessageDialog(this, "Formato de fichero incorrecto", "Error al cargar la partida", JOptionPane.ERROR_MESSAGE);
                    return;
                } 
                int filas = Integer.parseInt(partes[0]);
                int columnas = Integer.parseInt(partes[1]);
                int parejasRestantes = Integer.parseInt(partes[2]);
                int intentos = Integer.parseInt(partes[3]);
                int segundos = Integer.parseInt(partes[4]);
                int nivel = Integer.parseInt(partes[5]);
                // Botones
                Boton[][] botones = new Boton[filas][columnas];
                while((linea = br.readLine()) != null) {
                    partes = linea.split(";");
                    if (partes.length != 4) {
                        JOptionPane.showMessageDialog(this, "Formato de fichero incorrecto", "Error al cargar la partida", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int fila = Integer.parseInt(partes[0]);
                    int columna = Integer.parseInt(partes[1]);
                    int estado = Integer.parseInt(partes[2]);
                    int valor = Integer.parseInt(partes[3]);
                    Boton boton = new Boton(null, fila, columna);
                    boton.setEstado(estado);
                    boton.setValor(valor);
                    botones[fila][columna] = boton;
                }
                inicializarTablero(filas, columnas, parejasRestantes, intentos, botones);
                nivelDificultad = nivel;
                actualizarContadores();
                inicializarContador();
                contador.setSegundos(segundos);
                tablero.ganar();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error al cargar la partida", JOptionPane.ERROR_MESSAGE);
            } finally {
                try{
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, e.getMessage(), "Error al cargar la partida", JOptionPane.ERROR_MESSAGE);
                } 
            }
        }
    }

    public void nuevaPartida(int filas, int columnas) {
        inicializarContador();
        inicializarTablero(filas, columnas, -1, 0, null);
        actualizarContadores();
    }

}

package org.liceolapaz.des.rrmfg;

import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tablero extends JPanel {

    private Ventana ventana;
    private int filas;
    private int columnas;
    private int parejas;
    private int parejasRestantes;
    private int intentos;
    private Boton[][] botones;
    private Random rand;
    private Boton ficha1;
    private Boton ficha2;

    public Tablero(Ventana ventana, int filas, int columnas,int parejasRestantes, int intentos, Boton[][] botones) {
        super();
        this.ventana = ventana;
        this.filas = filas;
        this.columnas = columnas;
        comprobarNumeroCasillas();
        this.parejas = (filas * columnas) / 2;
        if ((parejasRestantes >= 0) && (parejasRestantes <= this.parejas)) {
            this.parejasRestantes = parejasRestantes;
        } else {
            this.parejasRestantes = this.parejas;
        }
        this.intentos = intentos;
        this.ficha1 = null;
        this.ficha2 = null;
        crearBotones(botones);
    }

    private void comprobarNumeroCasillas() {
        while ((filas * columnas) % 2 != 0) {
            JOptionPane.showMessageDialog(this,
                    "El número de casillas debe ser par", "Error",
                    JOptionPane.ERROR_MESSAGE);
            filas = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Introduzca el número de filas"));
            columnas = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Introduzca el número de columnas"));
        }
    }

    private void crearBotones(Boton[][] botones) {
        setLayout(new GridLayout(filas, columnas));
        if (botones == null) {
            this.botones = new Boton[filas][columnas];
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    this.botones[i][j] = new Boton(this, i, j);
                    add(this.botones[i][j]);
                }
            }

            for (int i = 0; i < 2; i++) {
                int contador = 0;
                rand = new Random(System.currentTimeMillis());
                while (contador < parejas) {
                    int fila = rand.nextInt(filas);
                    int columna = rand.nextInt(columnas);
                    if (this.botones[fila][columna].getValor() == 0) {
                        this.botones[fila][columna].setValor(++contador);
                    }
                }
            }
        } else {
            this.botones = new Boton[botones.length][botones[0].length];
            for (int i = 0; i < botones.length; i++) {
                for (int j = 0; j < botones[i].length; j++) {
                    this.botones[i][j] = new Boton(this,
                            botones[i][j].getFila(),
                            botones[i][j].getColumna());
                    add(this.botones[i][j]);
                }
            }
            for (int i = 0; i < botones.length; i++) {
                for (int j = 0; j < botones[i].length; j++) {
                    this.botones[i][j].setEstado(botones[i][j].getEstado());
                    this.botones[i][j].setValor(botones[i][j].getValor());
                    this.botones[i][j]
                            .actualizarEstado(botones[i][j].getEstado());
                }
            }
        }
    }

    public Boton[][] getBotones() {
        return botones;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void destapar(Boton boton) {
        if (ficha1 == null) {
            ficha1 = boton;
        } else if (ficha2 == null) {
            ficha2 = boton;
            comprobarPareja();
        }
    }

    private void comprobarPareja() {
        if (ficha1.getValor() == ficha2.getValor()) {
            JOptionPane.showMessageDialog(this,
                    "Los números destapados son iguales", "Bien hecho",
                    JOptionPane.INFORMATION_MESSAGE);
            ficha1.setEnabled(false);
            ficha1 = null;
            ficha2.setEnabled(false);
            ficha2 = null;
            parejasRestantes--;
        } else {
            JOptionPane.showMessageDialog(this,
                    "Los números destapados no son iguales", "Sigue buscando",
                    JOptionPane.INFORMATION_MESSAGE);
            ficha1.tapar();
            ficha1 = null;
            ficha2.tapar();
            ficha2 = null;
        }
        intentos++;
        ventana.actualizarContadores();
        comprobarFin();
    }

    public void comprobarFin() {
        if (parejasRestantes == 0) {
            ventana.fin(true);
            JOptionPane.showMessageDialog(this,
                    "¡¡¡Has ganado en " + intentos + " intento(s)!!!",
                    "Enhorabuena", JOptionPane.INFORMATION_MESSAGE);
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Quieres jugar otra partida?", "Fin de partida",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                ventana.nuevaPartida(filas, columnas);
            } else {
                System.exit(0);
            }
        }
    }

    public int getParejas() {
        return parejas;
    }

    public int getParejasRestantes() {
        return parejasRestantes;
    }

    public int getIntentos() {
        return intentos;
    }

    public void ganar() {
        if (this.parejasRestantes == 0) {
            ventana.fin(true);
        }
    }

}

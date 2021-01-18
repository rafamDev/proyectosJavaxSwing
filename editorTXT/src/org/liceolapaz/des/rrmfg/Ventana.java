package org.liceolapaz.des.rrmfg;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class Ventana extends JFrame {

    private JTextArea txt;
	private JMenu archivo;
	private JMenuBar menub;
	private JMenuItem menuItem;
	private JScrollPane barrita;
	private String tituloVentana;
    private String ruta;
    private int opcion;

    public Ventana() {
	   super();
		setSize(1024,768);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Documento nuevo");
		 this.txt = new JTextArea();
	     this.archivo = new JMenu("Archivo");
	     this.menub = new JMenuBar();
	     this.menuItem = new JMenuItem();
	     this.barrita = new JScrollPane();
	     this.txt.setBackground(Color.BLACK);
		 this.txt.setForeground(Color.GREEN);
		 this.txt.setFont(new Font("Comics Sans", 3, 30));
	     this.ruta = null;
	     this.barrita.setViewportView(this.txt);
	     add(this.barrita,BorderLayout.CENTER);
	     this.barrita.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	     this.barrita.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	     this.menub.add(this.archivo);
	     this.txt.setCaretColor(Color.GREEN);
		 setJMenuBar(this.menub);
	     //Esto con ImageIcon al exportar el proyecto no lo harian las imagenes.
	    URL url = getClass().getResource("/imagen.jpg");
	    setIconImage(new ImageIcon(url).getImage());
	   nuevo();
	   abrir();
	   guardar();
	   guardarComo();
	   salir();
   }

    private void nuevo() {
     //Opcion NUEVO
     this.menuItem = new JMenuItem("Nuevo");
     this.tituloVentana = "NUEVO";
     this.archivo.add(this.menuItem);
     this.menuItem.addActionListener(new ActionListener() {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		opcion = JOptionPane.showConfirmDialog(menuItem, "¿El texto que no se haya guardado se perderá, desea abrir un archivo nuevo?", tituloVentana,JOptionPane.YES_NO_OPTION);
    			if(opcion == 0){
    				setTitle("NUEVO");
    				 ruta = null;
    	    		 txt.setText(null);
    			}     
    	  }  
    	});
    	this.menuItem.setMnemonic(KeyEvent.VK_A);
    	 this.menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
    	    this.menuItem.setIcon(new ImageIcon(getClass().getResource("/nuevo.png")));
     }
     private void abrir() {
	    //Opcion ABRIR
		this.menuItem = new JMenuItem("Abrir");
		this.tituloVentana = "ABRIR";
		this.archivo.add(this.menuItem);
		this.menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			 opcion = JOptionPane.showConfirmDialog(menuItem, "¿El texto que no se haya guardado se perderá, desea abrir un archivo nuevo?", tituloVentana,JOptionPane.YES_NO_OPTION);
 				if(opcion == 0){
 					setTitle("ABRIR");
 					abrirFichero();
 				}
			 }
		   });
		 this.menuItem.setMnemonic(KeyEvent.VK_Z);
           this.menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		      this.menuItem.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
	}
    private void guardar() {
 	   //Opcion GUARDAR
    	this.menuItem = new JMenuItem("Guardar");
		this.tituloVentana = "GUARDAR";
    	this.archivo.add(this.menuItem);
		this.menuItem.addActionListener(new ActionListener() {
		  @Override
		  public void actionPerformed(ActionEvent e) {
			opcion = JOptionPane.showConfirmDialog(menuItem, "¿Desea guardar un archivo ?", tituloVentana,JOptionPane.YES_NO_OPTION);
			  if(opcion == 0){
				 if(ruta == null){
				     setTitle("GUARDAR COMO");
					 guardarComoFichero();
			     }else{
					 setTitle("GUARDAR");	
					 guardarFichero();
			  }
		    }
		  }	 
	     });
       this.menuItem.setMnemonic(KeyEvent.VK_K);
          this.menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl K"));
	        this.menuItem.setIcon(new ImageIcon(getClass().getResource("/guardarComoo.png")));
	}
    private void guardarComo() {
       //Opcion GUARDAR COMO
	   this.menuItem = new JMenuItem("Guardar como");
	   this.tituloVentana = "GUARDAR COMO";
       this.archivo.add(this.menuItem);
	   this.menuItem.addActionListener(new ActionListener() {
		 @Override
		 public void actionPerformed(ActionEvent e) {
            opcion = JOptionPane.showConfirmDialog(menuItem, "¿Desea guardar un archivo ?", tituloVentana,JOptionPane.YES_NO_OPTION);
				 if(opcion == 0){
					 setTitle("GUARDAR COMO");
					 guardarComoFichero();
				 }
			}});
	   this.menuItem.setMnemonic(KeyEvent.VK_S);
         this.menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
	        this.menuItem.setIcon(new ImageIcon(getClass().getResource("/guardar.png")));
    }
    private void salir() {
      //Opcion SALIR 
	  this.menuItem = new JMenuItem("Salir");
	  this.tituloVentana = "SALIR";
	  this.archivo.add(this.menuItem);
	  this.menuItem.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e){
		    opcion = JOptionPane.showConfirmDialog(menuItem, "¿El texto que no se haya guardado se perderá, desea salir?", tituloVentana,JOptionPane.YES_NO_OPTION);
   				if(opcion == 0){
				    setTitle("SALIR");
					 System.exit(0);
				 }
			}
	    });
	  this.menuItem.setMnemonic(KeyEvent.VK_C);
        this.menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
	      this.menuItem.setIcon(new ImageIcon(getClass().getResource("/salir.png")));
	 }
     private void abrirFichero() {
       this.ruta = JOptionPane.showInputDialog(null,"Introduce ruta: ");
       try {
       File archivoSeleccionado = new File(ruta);
       FileReader fr = new FileReader(archivoSeleccionado);
       BufferedReader br = new BufferedReader(fr);
    	//El metodo readLine() no lee la primera linea.
    	String linea;
        String texto = "";
    	if(this.ruta.endsWith(".txt")){
        	 while((linea = br.readLine()) != null){
    		//El br no para hasta que no de tecta linea en blanco o salto de linea.
    	          texto = texto + linea + "\n";
                  this.txt.setText(texto);
    		 }
    	 br.close();
    	}else{
    		JOptionPane.showMessageDialog(null, "El archivo no es un .txt");
    	}
       }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "El archivo no es una ruta");
       }
   }  	
   private void guardarComoFichero() {
	JFileChooser filechooser = new JFileChooser();
	filechooser.setDialogTitle("Guardar");
	int seleccion = filechooser.showSaveDialog(null);
	  if (seleccion == JFileChooser.APPROVE_OPTION) {
		try {
			this.ruta = filechooser.getSelectedFile().getAbsolutePath();
			FileWriter fw = new FileWriter(this.ruta);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.println(this.txt.getText());
			pw.close();
		}
		catch(IOException ex) { 
	       JOptionPane.showMessageDialog(this.txt, "No has escogido un archivo para guardar","Error", 0);
        }
	
     }		
   }
   private void guardarFichero() {
	  try{
	    FileWriter fw = new FileWriter(this.ruta);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		pw.println(this.txt.getText());
		pw.close();
	  }catch(IOException ex){ 
	    JOptionPane.showMessageDialog(this.txt, "No has escogido un archivo para guardar","Error", 0);
      }
  } 

	

 }
   




	
	
	


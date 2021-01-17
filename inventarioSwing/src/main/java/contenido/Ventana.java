package contenido;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;


public class Ventana extends JFrame {
	private Dialogo dialogo;
	private String usuario;
        private String password;
	private JPanel panelCampos;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JComboBox<String>comboxTipo;
	private JTextField txtMarca;
	private JTextField txtModelo;
	private JTextField txtNumSerie;
	private JTextField txtResponsable;
	private JTextField txtLocal_Aula;
	private JTextField txtFechaDeAlta;
	private JTextField txtFechaDeModificacion;
	private JTextField txtFechaDeBaja;
	private JTextField txtMotivoDeBaja;
	private JTextArea areaObservaciones;
	private String instruccionSQL;
        private ResultSet rst;
        private Statement st;
	private PreparedStatement ps;
	
	private static final String URL_BASE_DATOS = "jdbc:mysql://localhost:3306/liceo?useSSL=false&serverTimezone=Europe/Madrid";
	
	public Ventana(Dialogo dialogo,String usuario,String password){
	  super();
	  this.dialogo = dialogo;
	  this.usuario = usuario;
	  this.password = password;
          setSize(1100, 650);
          setLayout(new GridLayout(2,1));
	  setLocationRelativeTo(null);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  setTitle("Inventario");
	  setLayout(new GridLayout(1,2,10,10));
          componentes();
	  try{
	    Class.forName("com.mysql.cj.jdbc.Driver");
		}catch (ClassNotFoundException e){
		  JOptionPane.showMessageDialog(this, "Error al registrar el driver", "Error", JOptionPane.ERROR_MESSAGE);
		  System.exit(0);
		} 
	  }
	
	public Connection crearConexion(String url) throws SQLException{
		return DriverManager.getConnection(url, this.usuario, this.password);
	}
	
	private void menu(){
	    JMenuBar menuBar = new JMenuBar();
	    JMenu menuOpciones = new JMenu("Opciones");
	    menuOpciones.setMnemonic(KeyEvent.VK_O);
	    JMenuItem cambiarUsuario = new JMenuItem("Cambiar usuario");
	    cambiarUsuario.setMnemonic(KeyEvent.VK_U);
	    cambiarUsuario.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
	    cambiarUsuario.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e) {
		    cambiarUsuario();
		  }
             });
	   menuOpciones.add(cambiarUsuario);
           JMenuItem cargarDatos = new JMenuItem("Cargar datos");
           cargarDatos.setMnemonic(KeyEvent.VK_D);
	   cargarDatos.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
	   cargarDatos.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
	           cargarDatos();
		 }
            });
           menuOpciones.add(cargarDatos);
	   menuBar.add(menuOpciones);
	   JMenuItem limpiarDatos = new JMenuItem("Limpiar datos");
	   limpiarDatos.setMnemonic(KeyEvent.VK_L);
	   limpiarDatos.setAccelerator(KeyStroke.getKeyStroke("ctrl L"));
	   limpiarDatos.addActionListener(new ActionListener() {
	       public void actionPerformed(ActionEvent e){
		  limpiarDatos();
	       }
	   });
	  menuOpciones.add(limpiarDatos);
	  menuBar.add(menuOpciones);
	  JMenu menuInformes = new JMenu("Informes");
	  menuInformes.setMnemonic(KeyEvent.VK_A);
	  JMenuItem Actuales = new JMenuItem("Actuales");
	  Actuales.setMnemonic(KeyEvent.VK_B);
	  Actuales.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
          Actuales.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e){
	         actuales();
              }
          });
	  menuInformes.add(Actuales);
	  JMenuItem actualesMasBajas = new JMenuItem("Actuales + Bajas");
	  actualesMasBajas.setMnemonic(KeyEvent.VK_C);
	  actualesMasBajas.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
	  actualesMasBajas.addActionListener(new ActionListener(){
	       public void actionPerformed(ActionEvent e){
	          actualesMasBajas();
               }
           });
	   menuInformes.add(actualesMasBajas);
	   JMenuItem historico = new JMenuItem("Historico");
	   historico.setMnemonic(KeyEvent.VK_H);
	   historico.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
	   historico.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
	           historico();
	        }
            });
	    menuInformes.add(historico);
	    JMenuItem responsable_altas = new JMenuItem("Responsable/Altas");
	    responsable_altas.setMnemonic(KeyEvent.VK_R);
	    responsable_altas.setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
	    responsable_altas.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
	           responsable_aula();
                }
             });
	menuInformes.add(responsable_altas);
	menuBar.add(menuInformes);
        setJMenuBar(menuBar);
      }

	private void componentes(){
	      menu();
		this.panelCampos = new JPanel();
		this.panelCampos.setLayout(new GridLayout(14,2,10,10));
		this.panelCampos.setBorder(new EmptyBorder(20,10,10,10));
		JLabel lbCodigo = new JLabel("Codigo");
		this.panelCampos.add(lbCodigo);
		this.txtCodigo = new JTextField();
		this.txtCodigo.setEditable(false);
		this.panelCampos.add(this.txtCodigo);
		JLabel lbDescripcion = new JLabel("Descripcion");
		this.panelCampos.add(lbDescripcion);
		this.txtDescripcion = new JTextField();
		this.panelCampos.add(this.txtDescripcion);
		JLabel lb1Tipo = new JLabel("Tipo (Elija una opción)");
		this.panelCampos.add(lb1Tipo);
		this.comboxTipo = new JComboBox<String>();
		this.comboxTipo.setEditable(false);
		this.comboxTipo.addItem("");
		this.comboxTipo.addItem("PC");
		this.comboxTipo.addItem("Proyector");
		this.comboxTipo.addItem("Pantalla");
		this.comboxTipo.addItem("Pantalla interactiva");
		this.comboxTipo.addItem("Tablet");
		this.comboxTipo.addItem("HIFI");
		this.comboxTipo.addItem("TV");
		this.comboxTipo.addItem("DVD");
		this.comboxTipo.addItem("Combo");
		this.panelCampos.add(this.comboxTipo);
		JLabel lbMarca = new JLabel("Marca");
		this.panelCampos.add(lbMarca);
		this.txtMarca = new JTextField();
		this.panelCampos.add(this.txtMarca);
		JLabel lbModelo = new JLabel("Modelo");
		this.panelCampos.add(lbModelo);
		this.txtModelo = new JTextField();
		this.panelCampos.add(this.txtModelo);
		JLabel lbNumSerie = new JLabel("Numero de serie");
		this.panelCampos.add(lbNumSerie);
		this.txtNumSerie = new JTextField();
		this.panelCampos.add(this.txtNumSerie);
		JLabel lbResponsable = new JLabel("Responsable");
		this.panelCampos.add(lbResponsable);
		this.txtResponsable = new JTextField();
		this.panelCampos.add(this.txtResponsable);
		JLabel lbLocal_Aula = new JLabel("Local/Aula");
		this.panelCampos.add(lbLocal_Aula);
		this.txtLocal_Aula = new JTextField();
		this.panelCampos.add(this.txtLocal_Aula);
		JLabel lbFechaDeAlta = new JLabel("Fecha de Alta");
		this.panelCampos.add(lbFechaDeAlta);
		this.txtFechaDeAlta = new JTextField();
		this.txtFechaDeAlta.setEditable(false);
		this.panelCampos.add(this.txtFechaDeAlta);
	        JLabel lbFechaDeModificacion = new JLabel("Fecha de Modificacion");
		this.panelCampos.add(lbFechaDeModificacion);
		this.txtFechaDeModificacion= new JTextField();
		this.txtFechaDeModificacion.setEditable(false);
		this.panelCampos.add(this.txtFechaDeModificacion);
		JLabel lbFechaDeBaja = new JLabel("Fecha de baja");
		this.panelCampos.add(lbFechaDeBaja);
		this.txtFechaDeBaja = new JTextField();
		this.txtFechaDeBaja.setEditable(false);
		this.panelCampos.add(this.txtFechaDeBaja);
		JLabel lbMotivoDeBaja = new JLabel("Motivo de baja");
		this.panelCampos.add(lbMotivoDeBaja);
		this.txtMotivoDeBaja = new JTextField();
		this.txtMotivoDeBaja.setEditable(false);
		this.panelCampos.add(this.txtMotivoDeBaja);
		JLabel lbObservaciones = new JLabel("Observaciones");
		this.panelCampos.add(lbObservaciones);
		this.areaObservaciones = new JTextArea();
		JScrollPane barrita = new JScrollPane(this.areaObservaciones);
		barrita.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		barrita.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                this.panelCampos.add(barrita);
	      botones();
            add(this.panelCampos);
	}

	private void botones(){
	  JButton bActualizar = new JButton("Grabar datos");
	  bActualizar.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent e){
	      grabarDatos();
	   }
          });
	  this.panelCampos.add(bActualizar);
          JButton bMostrar = new JButton("Dar de baja");
	  bMostrar.addActionListener(new ActionListener(){ 
	   public void actionPerformed(ActionEvent e){
	     darDeBaja();
	    }
	  });
	 this.panelCampos.add(bMostrar);
       }
	
       private void cambiarUsuario() {
	  this.dialogo.setVisible(true);
          this.setVisible(false);
	}

      private void limpiarDatos(){
    	this.txtCodigo.setText("");
    	this.txtDescripcion.setText("");
    	this.comboxTipo.setSelectedIndex(0);
    	this.txtMarca.setText("");
    	this.txtModelo.setText("");
    	this.txtNumSerie.setText("");
    	this.txtResponsable.setText("");
    	this.txtLocal_Aula.setText("");
    	this.txtFechaDeAlta.setText("");
    	this.txtFechaDeModificacion.setText("");
    	this.txtFechaDeBaja.setText("");
    	this.txtMotivoDeBaja.setText("");
    	this.areaObservaciones.setText("");
     }

   private void cargarDatos() {
        Connection conexion = null;
  	 this.instruccionSQL = "SELECT * FROM actual;";
  	 int codigo = Integer.parseInt(JOptionPane.showInputDialog("Introduzca Codigo: "));
  	try{
      	 conexion = crearConexion(URL_BASE_DATOS);
      	 this.st = conexion.createStatement();
  	     this.rst = this.st.executeQuery(this.instruccionSQL);
  	       while(this.rst.next()){
  	             if(codigo == this.rst.getInt(1)){
  			this.txtCodigo.setText("" + this.rst.getInt("Cod")); 
  			this.txtDescripcion.setText(this.rst.getString("Des"));    
  			this.comboxTipo.setSelectedItem(this.rst.getString("Tipo"));
			this.txtMarca.setText(this.rst.getString("Marca"));     
  			this.txtModelo.setText(this.rst.getString("Modelo"));
  			this.txtNumSerie.setText(this.rst.getString("NumSerie"));
  			this.txtResponsable.setText(this.rst.getString("Resp"));
    	                this.txtLocal_Aula.setText(this.rst.getString("Local"));
    	                if(this.rst.getTimestamp("FecAlta") != null){
	     	           this.txtFechaDeAlta.setText("" + this.rst.getTimestamp("FecAlta"));
	     	        }else{
	     	          this.txtFechaDeAlta.setText("");
	     	        }
	     	        if(this.rst.getTimestamp("FecMod") != null){
	       	          this.txtFechaDeModificacion.setText("" + this.rst.getTimestamp("FecMod"));
	                }else{
	       	          this.txtFechaDeModificacion.setText("");
	       	        }
	     	        if(this.rst.getTimestamp("FecBaja") != null){
	       	          this.txtFechaDeBaja.setText("" + this.rst.getTimestamp("FecBaja"));
	       	        }else{
	       	          this.txtFechaDeBaja.setText("");
	       	        }
    	                this.txtMotivoDeBaja.setText(this.rst.getString("Motivo"));
    	                this.areaObservaciones.setText(this.rst.getString("Obs"));  
    	        conexion.close();
    	        return;
  	        } 
  	       }			 
  	      JOptionPane.showMessageDialog(this, "No exite objeto con Codigo: " + codigo, "Error", JOptionPane.ERROR_MESSAGE); 
  	      conexion.close();
  	  }catch(SQLException e){
  	      JOptionPane.showMessageDialog(this, e.getMessage(), "Error al mostrar los datos", JOptionPane.ERROR_MESSAGE);
  	      try{
  		if(conexion != null){
  	            conexion.close();
  		  }
  	      }catch(SQLException e1){}
  	  }
    }
 
    private void actuales(){
       Connection conexion = null;
	 this.instruccionSQL = "SELECT * FROM actual WHERE FecBaja IS NULL";
	   try{
	      conexion = crearConexion(URL_BASE_DATOS);
	      this.st = conexion.createStatement();
              this.rst = this.st.executeQuery(this.instruccionSQL);
	      Informe informe = new Informe(this.rst);
	      informe.setVisible(true);
	      conexion.close();
	      return;
          }catch(SQLException e){
	   JOptionPane.showMessageDialog(this, e.getMessage(), "Error al mostrar los datos", JOptionPane.ERROR_MESSAGE);
	  try{
	    if(conexion != null){
		conexion.close();
	       }
          }catch(SQLException e1){}
        }
   }

   private void actualesMasBajas(){
     Connection conexion = null;
     this.instruccionSQL = "SELECT * FROM actual";
      try{
	conexion = crearConexion(URL_BASE_DATOS);
	this.st = conexion.createStatement();
	this.rst = this.st.executeQuery(this.instruccionSQL);
	Informe informe = new Informe(this.rst);
        informe.setVisible(true);
	conexion.close();
        return;
      }catch(SQLException e){
         JOptionPane.showMessageDialog(this, e.getMessage(), "Error al mostrar los datos", JOptionPane.ERROR_MESSAGE);
	 try{
	  if(conexion != null){
	      conexion.close();
	    } 
	 }catch(SQLException e1){}
      }
   }

    private void historico(){
       Connection conexion = null;
       this.instruccionSQL = "SELECT * FROM historico WHERE Cod IN (SELECT MAX(Cod) FROM historico GROUP BY fecBaja) ORDER BY Cod DESC";
	try{
          conexion = crearConexion(URL_BASE_DATOS);
	  this.st = conexion.createStatement();
	  this.rst = this.st.executeQuery(this.instruccionSQL);
	  Informe informe = new Informe(this.rst);
	  informe.setVisible(true);
	  conexion.close();
	  return;
	}catch(SQLException e){
          JOptionPane.showMessageDialog(this, e.getMessage(), "Error al mostrar los datos", JOptionPane.ERROR_MESSAGE);
	   try{
	     if(conexion != null){
	        conexion.close();
	     }
	   }catch(SQLException e1){}
	}
   }   
   
   private void responsable_aula(){
	Connection conexion = null;
	   this.instruccionSQL = "SELECT * FROM actual WHERE Resp=? && Local=?";
	   String responsable = JOptionPane.showInputDialog("Introduzca Responsable: ");
	   String local_aula = JOptionPane.showInputDialog("Introduzca Local/Aula: ");
	 try{
    	   conexion = crearConexion(URL_BASE_DATOS);
    	   this.ps = conexion.prepareStatement(this.instruccionSQL);
    	   this.ps.setString(1, responsable);
    	   this.ps.setString(2, local_aula);
    	   this.rst = this.ps.executeQuery();
    	   Informe informe = new Informe(this.rst);
    	   informe.setVisible(true);
    	   conexion.close();
    	   return;
        }catch(SQLException e){
         JOptionPane.showMessageDialog(this, e.getMessage(), "Error al mostrar los datos", JOptionPane.ERROR_MESSAGE);
	 try{
	   if(conexion != null){
		conexion.close();
	    }
	 }catch(SQLException e1){}
        }  
   }

   private void grabarDatos(){
        Connection conexion = null;
	try{
	 if(verificarDatos() == true && verificacionCodigo() == false){
	     this.instruccionSQL ="INSERT INTO actual"
	 	+ "(Des,Tipo,Marca,Modelo,NumSerie,Resp,Local,FecAlta,Obs)"
	 	+ "VALUES (?,?,?,?,?,?,?,?,?);";
	 	conexion = crearConexion(URL_BASE_DATOS);
	 	     this.ps = conexion.prepareStatement(this.instruccionSQL); 
	             this.ps.setString(1,this.txtDescripcion.getText());
	             this.ps.setString(2,this.comboxTipo.getSelectedItem().toString());
	             this.ps.setString(3,this.txtMarca.getText());
	             this.ps.setString(4,this.txtModelo.getText());
	             this.ps.setString(5,this.txtNumSerie.getText());
	             this.ps.setString(6,this.txtResponsable.getText());
	             this.ps.setString(7,this.txtLocal_Aula.getText());
	             this.ps.setTimestamp(8, fecha_actual());
	             this.ps.setString(9,this.areaObservaciones.getText());
	          this.ps.executeUpdate();
	          JOptionPane.showMessageDialog(this,"Se han creado los datos");
		 limpiarDatos();
	         conexion.close();
	 	
	 }else if(verificarDatos() == true && verificacionCodigo() == true){
	         this.instruccionSQL ="UPDATE actual SET Des=?,Tipo=?,"
	         + "Marca=?,Modelo=?,NumSerie=?,Resp=?,Local=?,"
		 + "FecAlta=?,FecMod=?,Obs=? WHERE Cod=?;";
	 	 conexion = crearConexion(URL_BASE_DATOS);
		 this.ps = conexion.prepareStatement(this.instruccionSQL); 
			 this.ps.setString(1,this.txtDescripcion.getText());
		 	 this.ps.setString(2,this.comboxTipo.getSelectedItem().toString());
		 	 this.ps.setString(3,this.txtMarca.getText());
		 	 this.ps.setString(4,this.txtModelo.getText());
		 	 this.ps.setString(5,this.txtNumSerie.getText());
		 	 this.ps.setString(6,this.txtResponsable.getText());
		 	 this.ps.setString(7,this.txtLocal_Aula.getText());
		 	 this.ps.setString(8,this.txtFechaDeAlta.getText());
		 	 this.ps.setTimestamp(9,fecha_actual());
		         this.ps.setString(10,this.areaObservaciones.getText());
		 	 this.ps.setString(11,this.txtCodigo.getText());
		      this.ps.executeUpdate();
		  JOptionPane.showMessageDialog(this,"Se han modificado los datos con codigo: " + this.txtCodigo.getText());
		  limpiarDatos();
		  conexion.close();
		  return;
        }     
       }catch(SQLException e){
	    JOptionPane.showMessageDialog(this, e.getMessage(), "Error al mostrar los datos", JOptionPane.ERROR_MESSAGE);
	    e.printStackTrace();
		   try{
		      if(conexion != null){
		  	  conexion.close();
		      }
		  }catch(SQLException e1){}
		}
   }
   
   private void darDeBaja(){
	 Connection conexion = null;
	  String motivo = JOptionPane.showInputDialog("Introduzca Motivo de baja: ");
	  this.instruccionSQL ="UPDATE actual SET Des=?,Tipo=?,"
	  + "Marca=?,Modelo=?,NumSerie=?,Resp=?,Local=?,FecAlta=?,FecBaja=?,"
	  + "Motivo=?,Obs=? WHERE Cod=?;";
	    try{
		 conexion = crearConexion(URL_BASE_DATOS);
		   this.ps = conexion.prepareStatement(this.instruccionSQL);
		      this.ps.setString(1,this.txtDescripcion.getText());
		 	  this.ps.setString(2,this.comboxTipo.getSelectedItem().toString());
		 	  this.ps.setString(3,this.txtMarca.getText());
		 	  this.ps.setString(4,this.txtModelo.getText());
		 	  this.ps.setString(5,this.txtNumSerie.getText());
		 	  this.ps.setString(6,this.txtResponsable.getText());
		 	  this.ps.setString(7,this.txtLocal_Aula.getText());
		 	  this.ps.setString(8,this.txtFechaDeAlta.getText());
		 	  this.ps.setTimestamp(9,fecha_actual());
		 	  this.ps.setString(10,motivo);
		 	  this.ps.setString(11,this.areaObservaciones.getText());
		 	  this.ps.setString(12,this.txtCodigo.getText());
		       this.ps.executeUpdate();
		  conexion.close();
		JOptionPane.showMessageDialog(this,"Se han dado de baja los datos con codigo: " + this.txtCodigo.getText());
		JOptionPane.showMessageDialog(this,"Se han insertado en historico, los datos con codigo: " + this.txtCodigo.getText());    
		limpiarDatos(); 
	        return;
	}catch(SQLException e){
	      JOptionPane.showMessageDialog(this, e.getMessage(), "Error al mostrar los datos", JOptionPane.ERROR_MESSAGE);
	       try{
		 if(conexion != null){
		    conexion.close();
		 }
	     }catch(SQLException e1){}
	}		
   }	

   private Timestamp fecha_actual(){
      return new Timestamp(System.currentTimeMillis());
   }

   private boolean verificacionCodigo(){
	 if(this.txtCodigo.getText().equals("")) {
		return false;
	 } 
	 return true;
   }
   
   private boolean verificarDatos(){
	  if(this.txtDescripcion.getText().equals("")){
		  JOptionPane.showMessageDialog(this, "Inserte Descripcion", "Error", JOptionPane.ERROR_MESSAGE);  
		     return false;
	  }
	  if(this.txtMarca.getText().equals("")){
		 JOptionPane.showMessageDialog(this, "Inserte Marca", "Error", JOptionPane.ERROR_MESSAGE); 
		    return false;
	  }
	  if(this.txtModelo.getText().equals("")){
	     JOptionPane.showMessageDialog(this, "Inserte Modelo", "Error", JOptionPane.ERROR_MESSAGE); 
		   return false;
	  }
	  if(this.comboxTipo.getSelectedItem().equals("")){
		 JOptionPane.showMessageDialog(this, "Inserte Tipo", "Error", JOptionPane.ERROR_MESSAGE); 
		   return false;
	  }
	  return true;
   }

}

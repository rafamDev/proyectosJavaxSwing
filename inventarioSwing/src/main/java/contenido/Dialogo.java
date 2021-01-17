package contenido;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Dialogo extends JDialog {
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private Ventana ventana;
	
	public Dialogo() {
		super();
		setTitle("Introduzca el usuario");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2, 10, 10));
		JLabel lbUsuario = new JLabel("Usuario");
		panel.add(lbUsuario);
		this.txtUsuario = new JTextField("root");
		this.txtUsuario.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				super.keyReleased(e);
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					aceptar();
				}
			}
		});
		panel.add(txtUsuario);
		JLabel lbPassword = new JLabel("Contraseña");
		panel.add(lbPassword);
		this.txtPassword = new JPasswordField();
		this.txtPassword.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					aceptar();
				}
			}
		});
		panel.add(txtPassword);
		JButton bAceptar = new JButton("Aceptar");
		bAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  aceptar();				
			}
		});
		panel.add(bAceptar);
		JButton bCancelar = new JButton("Cancelar");
		bCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  cancelar();
			}
		});
		panel.add(bCancelar);
		panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		add(panel, BorderLayout.CENTER);
	}
	
	private void cancelar(){
		if(this.ventana == null){
			System.exit(0);
		}
		this.ventana.setVisible(true);
		this.setVisible(false);
	}
	
	private void aceptar() {
		String usuario;
		String password;
		usuario = this.txtUsuario.getText();
		if (!usuario.equalsIgnoreCase("root") && !usuario.equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(this, "El usuario de este servidor debe ser <root>", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		password = new String(this.txtPassword.getPassword());
		if (!password.equalsIgnoreCase("abc123") && !usuario.equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(this, "Password incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		
		}
		this.ventana = new Ventana(this, usuario, password);
	        this.ventana.setVisible(true);
		this.setVisible(false);
	}
}

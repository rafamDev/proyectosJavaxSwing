package flowLayout;

import javax.swing.JFrame;

public class Frame extends JFrame {

	public Frame(){
	 super();
	  this.setTitle("Frame2");	
	  this.setBounds(600, 350, 600, 300);
	  add(new Panel());
	}
	
}

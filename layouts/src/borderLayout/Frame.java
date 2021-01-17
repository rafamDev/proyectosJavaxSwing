package borderLayout;

import javax.swing.JFrame;

public class Frame extends JFrame {

	public Frame(){
     super();		
	  this.setTitle("Frame");	
	  this.setBounds(600, 350, 600, 300);
	  add(new Panel());
	}
	
}

package borderLayout;

import java.awt.Color;

import javax.swing.JButton;

public class Button extends JButton {

	public Button(String title,Color color) {
	 super();	
		this.setText(title);
	    this.setForeground(Color.GRAY);
		this.setBackground(color);
	}
}

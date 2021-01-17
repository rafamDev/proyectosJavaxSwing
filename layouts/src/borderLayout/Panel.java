package borderLayout;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class Panel extends JPanel {
//Cada Panel solo puede tener un unico tipo de layout.
	public Panel() {
      super();
		
     //BorderLayout bLayout = new BorderLayout();
      
        //Hgap , Vgap
        BorderLayout bLayout = new BorderLayout(10,10);	
        this.setLayout(bLayout);
		
		Button bYellow = new Button("Yellow",Color.YELLOW);
		this.add(bYellow,BorderLayout.NORTH);
		
		Button bBlue = new Button("Blue",Color.BLUE);
		this.add(bBlue,BorderLayout.SOUTH);
		
		Button bBlack = new Button("Black",Color.BLACK);
		this.add(bBlack,BorderLayout.CENTER);
		
		Button bRed = new Button("Red",Color.RED);
		this.add(bRed,BorderLayout.WEST);
		
		Button bPurple = new Button("Pink",Color.PINK);
		this.add(bPurple,BorderLayout.EAST);
	}
}

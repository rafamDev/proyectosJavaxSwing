package flowLayout;

import java.awt.FlowLayout;
import javax.swing.JPanel;

public class Panel extends JPanel {

	public Panel() {
		Button bYellow = new Button("Yellow");
		this.add(bYellow);
		Button bBlue = new Button("Blue");
		this.add(bBlue);
		Button bRed = new Button("Red");
		this.add(bRed);
		
		//CENTER (By deafult)
		//Separacion (hGap px) , (vGap px)
		FlowLayout fLayout = new FlowLayout(FlowLayout.CENTER,75,100);
		this.setLayout(fLayout);
		
		/*
		//LEFT
		FlowLayout fLayout = new FlowLayout(FlowLayout.LEFT);
		this.setLayout(fLayout);
		
		//RIGHT
		FlowLayout fLayout = new FlowLayout(FlowLayout.RIGHT);
		this.setLayout(fLayout);
		*/
	}
}

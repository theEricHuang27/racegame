import java.applet.Applet;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class GraphicsMain extends Applet{

	public static void main(String[] args) {
		Canvas window = new Canvas();
	    JPanel p = new JPanel();
	    p.add(new GraphicsPanel());  //  add a class that extends JPanel
	    window.setTitle("Race!");
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    window.setContentPane(p);
	    window.pack();
	    window.setLocationRelativeTo(null);
	    window.setVisible(true);

	}

}

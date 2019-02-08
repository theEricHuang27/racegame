
// Class: GraphicsPanel
// Written by: Mr. Swope
// Date: 10/28/15
// Description: This class is the main class for this project.  It extends the Jpanel class and will be drawn on
// 				on the JPanel in the GraphicsMain class.  Your project should have at least one character that moves
//				with the arrow keys and one character that moves with the clock.  Finally, you should detect if the
//				two items intersect and have something happen if they do intersect.
//
// Since you will modify this class you should add comments that describe when and how you modified the class.  
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphicsPanel extends JPanel implements KeyListener {

	int background_x;
	private int count;
	private int count1;
	private int increase; // for rocks
	private Timer t; // The timer is used to move objects at a consistent time interval.
	private Character jeep; // A Lambo
	private ArrayList<Character> bullets;
	private ArrayList<Character> rocks;
	private ArrayList<Character> cops;
	private int score;
	private boolean win;
	private double numbullet;
	private boolean start;
	private boolean realStart;
	private int copColor;
	private int speed;
	private int level; 
	private boolean realRealStart;

	public GraphicsPanel() {

		// Instantiates jeep character
		jeep = new Character(0, 800, 120);

		setPreferredSize(new Dimension(950, 343)); // Set these dimensions to the width
													// of your background picture.

		t = new Timer(5, new ClockListener(this)); // t is a timer. This object will call the ClockListener's
													// action performed method every 5 milliseconds once the
													// timer is started. You can change how frequently this
													// method is called by changing the first parameter.

		this.setFocusable(true); // for keylistener
		this.addKeyListener(this);

		background_x = 0; // Instantiation of all variables
		count = 0;
		score = 0;
		win = true;
		increase = 0;
		bullets = new ArrayList<>();
		rocks = new ArrayList<>();
		cops = new ArrayList<>();
		numbullet = 1;
		count1 = 0;
		start = false;
		realStart = false;
		copColor = 5;
		speed = 1000;
		level = 1;
		realRealStart = false;

	}

	// method: paintComponent
	// description: This method will paint the items onto the graphics panel. This
	// method is called when the panel is
	// first rendered. It can also be called by this.repaint()
	// parameters: Graphics g - This object is used to draw your images onto the
	// graphics panel.
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		ClassLoader cldr = this.getClass().getClassLoader(); // These five lines of code load the background picture.
		String imagePath = "images/background.png"; // Change this line if you want to use a different
		URL imageURL = cldr.getResource(imagePath); // background image. The image should be saved in the
		ImageIcon image = new ImageIcon(imageURL); // images directory.
		image.paintIcon(this, g2, 0, 0);

		ImageIcon background1Image = new ImageIcon(imageURL); // Two ImageIcon's are used to scroll the background.
		background1Image.paintIcon(this, g2, background_x, 0);
		ImageIcon background2Image = new ImageIcon(imageURL);
		background2Image.paintIcon(this, g2, background_x - 1365, 0);

		//updates graphics for jeep
		jeep.draw(g2, this);
//		g2.drawRect(jeep.getBounds().x, jeep.getBounds().y, (int)jeep.getBounds().getWidth(), (int)jeep.getBounds().getHeight());
		//sets font size, type, and displays score and # bullets
		g2.setFont(new Font("Verdana", 0, 30));
		g2.drawString("Score: " + score, 50, 50);
		g2.drawString("Level: "+ level, 400, 50);
		g2.setFont(new Font("Verdana", 0, 20));
		//g2.drawString("#Bullets: "+(int)numbullet, 400, 45);
		
		
		if(realRealStart == false) {
			g2.setColor(Color.BLUE);
			g2.drawRect(0, 0, 1000, 1000);
			g2.fillRect(0, 0, 1000, 1000);
			g2.setFont(new Font("Verdana", Font.BOLD, 25));
			g2.setColor(Color.RED);
			g2.drawString("Welcome to TSA. Your goal is to make it to Nationals", 50, 50);
			g2.drawString("But there is a catch... ", 50, 100);
			g2.drawString("Other teams are trying to stop you by crashing into your bus", 50, 150);
			g2.drawString("You must succeed!", 50, 200);
			g2.drawString("(C) to continue", 400, 300);
		}
		
		
		if(start == false && realRealStart == true) {
			g2.setColor(Color.BLACK);
			g2.drawRect(0, 0, 1000, 1000);
			g2.fillRect(0, 0, 1000, 1000);
			g2.setPaint(Color.CYAN);
			g2.setFont(new Font("Verdana", 0, 50));
			g2.drawString("CHOOSE YOUR BUS", 210, 50);
			new Character(4, 75, 100).draw(g2, this);
			new Character(5, 375, 100).draw(g2, this);
			new Character(6, 675, 100).draw(g2, this);
			g2.setPaint(Color.YELLOW);
			g2.drawString("    #1            #2             #3", 75, 250);

		}
		
		if (realStart == false && start == true) {
			g2.setColor(Color.BLACK);
			g2.drawRect(0, 0, 1000, 1000);
			g2.fillRect(0, 0, 1000, 1000);
			g2.setPaint(Color.CYAN);
			g2.setFont(new Font("Verdana", 0, 50));
			g2.drawString("CHOOSE YOUR LEVEL", 200, 50);
			g2.setPaint(Color.YELLOW);
			g2.drawString("EASY          MEDIUM          HARD", 50, 150);
			g2.drawString(" (E)               (M)                (H)", 50, 200);
			g2.setFont(new Font("Verdana", 0, 25));
			g2.drawString("(Keypad to move)", 350, 300);


		}
		
		
		//for loop that loops through array list of road signs
		for (Character r : rocks) {			
			
			r.draw(g2, this); //updates road sign graphics
//			g2.drawRect(r.getBounds().x, r.getBounds().y, (int)r.getBounds().getWidth(), (int)r.getBounds().getHeight());
			for (Character b : bullets) { // for loop that loops through array list of bullets
				b.draw(g2, this); //updates bullets graphics
			}
			for(Character c: cops) {
//				g2.drawRect(c.getBounds().x, c.getBounds().y, (int)c.getBounds().getWidth(), (int)c.getBounds().getHeight());
				c.draw(g2, this);//updates cops graphics
			if (jeep.getBounds().intersects(r.getBounds()) || jeep.getBounds().intersects(c.getBounds())) // This checks if the rock and jeep intersect																						// intersected
				win = false; //win is set to false which is referenced later
			}
		}
		//checks for intersection of road signs and bullets and then removes the Characters that intersected by index 
		if (rocks.size()>1 ) { 
			for (int y = rocks.size() - 1; y >= 0; y--) {
				for (int b = bullets.size() - 1; b >= 0; b--) {
					if (bullets.get(b).getBounds().intersects(rocks.get(y).getBounds())) {
						bullets.remove(b);
						rocks.remove(y);
						break;
					
				}
			}
		}
		
	
		
		
		
		//Game Over code that checks if win is false. Timer is stopped and a Game Over screen is displayed
		if (win == false) {
			g2.setPaint(Color.BLACK);
			g2.setFont(new Font("Verdana", 0, 100));
			g2.drawString("GAME OVER", 170, 200);
			g2.setPaint(new Color(255, 0, 0, 127));
			g2.fillRect(0, 0, 1000, 500);
			t.stop();
		}
		
	

		//Stop Code that checks if the timer is stopped and the game isn't lost yet. Then displays pause screen
		if (t.isRunning() != true && win == true) {
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Verdana", 0, 100));
			g2.drawString("PAUSE", 300, 200);
			g2.setPaint(new Color(255, 0, 0, 127));
			g2.fillRect(0, 0, 1000, 500);
		}}

	}

	// method:clock
	// description: This method is called by the clocklistener every 5 milliseconds.
	// You should update the coordinates
	// of one of your characters in this method so that it moves as time changes.
	// After you update the
	// coordinates you should repaint the panel.
	public void clock() {

		count++; // adds one every time the clock is looped through, used to measure time
		count1++;
		if (background_x < 1365)
			background_x++;
		else
			background_x = 0;

		// updates the jeep position
		jeep.keyPressedMove();

		// moves the array list of rocks with the background
		for (Character r : rocks)
			r.timerMove(1);

		// Constant time interval between rock increase. Resets count every 3s
		if (count == 300 - increase) {
			rocks.add(new Character(-1, -100, (int) (Math.random() * 150 + 80)));
			if (increase != 150)
				increase++;
			count = 0;

		}

		if (score == 5000) {
			speed -= 100 ;
			level = 2;
		}
		else if(score == 10000){
			speed -= 100;
			level = 3;
		}
		
		if (count1 == speed) { // every 10s

			if ((int) (Math.random() * 2 + 1) == 1)
				cops.add(new Character(copColor, 1000, 100)); // instantiates cops
			else
				cops.add(new Character(copColor, 1000, 180));
			count1 = 0;
		}
		
		
		

		for (Character c : cops) // moves cops
			c.timerMove(-1);

		// Movement update for all bullets
//		for (Character b : bullets) {
//			b.timerMove(2);
//		}

		// @ constant speed, a portion of a bullet is added to make a full bullet
		if (count % 300 == 0)
			numbullet += .23;

		score++; // Variable that updates with time (ms) is the "score"

		// references graphics method and refreshes every 5ms
		this.repaint();
	}

	// method: keyPressed()
	// description: This method is called when a key is pressed. You can determine
	// which key is pressed using the
	// KeyEvent object and . For example if(e.getKeyCode() == KeyEvent.VK_LEFT)
	// would test to see if
	// the left key was pressed.
	// parameters: KeyEvent e
	@Override
	public void keyPressed(KeyEvent e) {
		// switch that checks for keys pressed and performs actions accordingly
		// Movement
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			jeep.setDirection(-2);
			break;
		case KeyEvent.VK_RIGHT:
			jeep.setDirection(2);
			break;
		case KeyEvent.VK_1:
			if(start == false && realRealStart == true){
				jeep.setImage(1);
				copColor = 5;
				start = true;
			}
			break;
		case KeyEvent.VK_2:
			if(start == false && realRealStart == true) {
				jeep.setImage(2);
				copColor = 6;
				start = true;
			}
			break;
		case KeyEvent.VK_3:
			if(start == false && realRealStart == true) {
				jeep.setImage(3);
				copColor = 4;
				start = true;
			}
			break;
		case KeyEvent.VK_E:
			if (start == true) {
				realStart = true;
				t.start();
				speed = 1000;
			}
			break;
		case KeyEvent.VK_M:
			if (start == true) {
				realStart = true;
				t.start();
				speed = 750;
			}
			break;
		case KeyEvent.VK_H:
			if (start == true) {
				realStart = true;
				t.start();
				speed = 500;
			}
			break;
		case KeyEvent.VK_UP:
			jeep.setVertical(-1);
			break;
		case KeyEvent.VK_DOWN:
			jeep.setVertical(1);
			break;
		case KeyEvent.VK_C:
			realRealStart = true;
			break;
		case KeyEvent.VK_R:
			t.restart();
			t.stop();
			jeep.setX(800);
			jeep.setY(120);
			count = 0;
			score = 0;
			win = true;
			increase = 0;
			bullets = new ArrayList<>();
			rocks = new ArrayList<>();
			cops = new ArrayList<>();
			numbullet = 1;
			count1 = 0;
			start = false;
			realStart = false;
			copColor = 5;
			speed = 1000;
			level = 1;
			break;
		// Pause
		case KeyEvent.VK_ESCAPE:
			if (start == true && realStart == true) {
				if (t.isRunning())
					t.stop();
				else
					t.start();
			}
			break;
		case KeyEvent.VK_SPACE:
			// if((int)numbullet>0) {
			// bullets.add(new Character(6, (int) jeep.getBounds().getX(), (int)
			// jeep.getBounds().getY() - 10 ));
			// numbullet--;
			// }
			break;
		}
		this.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			jeep.setDirection(0);
			break;
		case KeyEvent.VK_RIGHT:
			jeep.setDirection(0);
			break;
		case KeyEvent.VK_UP:
			jeep.setVertical(0);
			break;
		case KeyEvent.VK_DOWN:
			jeep.setVertical(0);
			break;

		}
	}

}

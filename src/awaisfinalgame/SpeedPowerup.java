package awaisfinalgame;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpeedPowerup {
	

	// x and y positions
	static double x = 100;
	static double y = 100;

	// monkey speed
	int speed = 3;

	 // moving position
	static double vy = 0.2;
	static double ay = 0.12;
	
	static int score = 0;

	// assigning variables to keyboard values
	String up = "SPACE";

	static String playerDead = "images/playerDead.png";
	
	static String stillPlayerSpeed = "images/playerStillSpeed.png";
	static String movingPlayerSpeed = "images/playerMovingSpeed1.png";
	static String movingPlayerSpeed2 = "images/playerMovingSpeed2.png";

	
	static String curImageName = stillPlayerSpeed;
	static Image image = new Image(curImageName, 60, 50, false, false);
	

	long iterationsSincePlayerChange = 0;

	// set canvas and graphics context
	GraphicsContext gc;
	@FXML
	Canvas gameCanvas;

	ArrayList<String> input;

	public SpeedPowerup(GraphicsContext gc, Canvas gameCanvas, ArrayList<String> input) {
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		this.input = input;
		setPosition();
	}

	// Monkey constructors
	public SpeedPowerup(String imageName, GraphicsContext gc, Canvas gameCanvas, ArrayList<String> input) {
		this.curImageName = imageName;
		this.gc = gc;
		this.gameCanvas = gameCanvas;
		this.input = input;
	}

	public void setPosition() {
		this.x = 40;
		this.y = 340;
	}

	// getters and setters for monkey
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}


	public void move() {
	
		settingGravity();
		
		animatingPlayer();
		        
		this.gc.drawImage(this.image, this.x, this.y);	
	}
	
	public void settingGravity() {
		
		if (curImageName != playerDead) {
		if (this.input.contains(this.up) || this.input.contains("PRIMARY")) {
			this.vy = -4.0 ;
		}  
		}
		
		double y = this.y;
		this.y += this.vy;
		
        this.setY(this.getY() + vy);
          
        vy += ay;
        
        if (this.y > 340) {
			this.vy=0;
		}
        if (this.y < 0) {
			this.y = y ;
		}
	}
	
	private void animatingPlayer() {	
		if (curImageName != playerDead) {
			if (iterationsSincePlayerChange > 10) {
			   if (curImageName == movingPlayerSpeed2) {
			    curImageName = movingPlayerSpeed;
			    
	    		}
	    		else {
	    		    curImageName = movingPlayerSpeed2;
	    		}
			   iterationsSincePlayerChange = 0;
				score++;
			}
			
			iterationsSincePlayerChange++;
	       
			if (this.y < 340) {
				curImageName = stillPlayerSpeed;
			}
		}
		
		if (curImageName == playerDead) {
			this.image = new Image(curImageName, 50, 35, false, false);
		}
		else if (curImageName == movingPlayerSpeed || curImageName == movingPlayerSpeed2) {
			this.image = new Image(curImageName, 45, 55, false, false);
		}	
		else {
			this.image = new Image(curImageName, 55, 55, false, false);
		}
	}
	
	public  Rectangle2D getBoundary() {
		return new Rectangle2D(this.x, this.y, this.image.getWidth(), this.image.getHeight());
	}
	
	public boolean collisionLaser(Laser laser) {
		int pixelsIn = 20;
		int pixelsOut = 30;
		int pixelsOver = 10;
		int pixelsUnder = 10;
				
		boolean collide = this.x + image.getWidth() > laser.x + pixelsIn && this.x + image.getWidth() < laser.x + laser.image.getWidth() + pixelsOut 
				&& this.y + image.getHeight() > laser.y + pixelsOver && this.y < laser.y + laser.image.getHeight() - pixelsUnder;
		
		return collide;
	}
	
	public boolean collisionMissile(Missile missile) {
		boolean collide = this.getBoundary().intersects(missile.getBoundary());
		return collide;
	}
	
	public boolean collisionPowerup(Powerup powerup) {
		boolean collide = this.getBoundary().intersects(powerup.getBoundary());
		return collide;
	}
	
	public boolean collisionCoin(Coin coin) {
		boolean collide = this.getBoundary().intersects(coin.getBoundary());
		return collide;
	}
	
	
	
}



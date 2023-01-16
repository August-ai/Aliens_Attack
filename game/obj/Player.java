package game.obj;

import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;

import game.component.PanelGame;
import game.component.Model;


import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import game.obj.EnemyHelper;
public class Player {
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.image = new ImageIcon(getClass().getResource("/game/image/planeR.png")).getImage();
		this.image_speed = new ImageIcon(getClass().getResource("/game/image/planeS.png")).getImage();
	}
	
	public static final double PLAYER_SIZE = 64;
	private double x;
	private double y;
	private final float MAX_SPEED = 0.1F;
	private float speed = 0f;
	private float angle = 0f;
	private final Image image;
	private final Image image_speed;
	public int health = 3;
	public boolean over = false;
	private boolean speedUp;
	private EnemyHelper enemyHelper;
	private Model model = new Model();
	
	public void update() {
		x += Math.cos(Math.toRadians(angle))*speed;
		y += Math.sin(Math.toRadians(angle))*speed;
		checkCollisions();

	}
	public void changePosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void changeAngle(float angle) {
		if(angle < 0) {
			angle = 359;
		}
		else if(angle > 359) {
			angle = 0;
		}
		this.angle = angle;
	}
	
	
	public void draw(Graphics2D g2) {
		AffineTransform oldTransform = g2.getTransform();
		
		g2.translate(x, y);
		
		AffineTransform tran = new AffineTransform();
		tran.rotate(Math.toRadians(angle), PLAYER_SIZE / 2, PLAYER_SIZE / 2);
		
		g2.drawImage(speedUp ? image_speed : image, tran, null);
		
		g2.setTransform(oldTransform);

		//g2.draw(getBounds());
	}
	
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void speedUp() {
		speedUp = true;
		
		if(speed > MAX_SPEED) {
			speed = MAX_SPEED;
		}else {
			speed += 0.00f;
		}
	}
	
	public void speedDown() {
		speedUp = false;
		
		if(speed <= 0) {
			speed = 0;
		}else {
			speed -= 0.000f;
		}
	}

	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,image.getWidth(null), image.getHeight(null));
	}

	public void checkCollisions(){
		ArrayList<Enemy> enemies = Model.getEnemyList();

		for(int i = 0; i < enemies.size(); i++){
			Enemy tempEnemy = enemies.get(i);
			if(getBounds().intersects(enemies.get(i).getBounds())){
				Model.removeEnemy(tempEnemy);
				health -= 1;
				if(health == 0){
					over = true;
				}
			}
		}
	}
}
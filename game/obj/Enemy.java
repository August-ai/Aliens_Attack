package game.obj;

import java.awt.Rectangle;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;;

public class Enemy {
	public Enemy(int x, int y) {
        this.x = x;
		this.y = y;
        this.enemy = new ImageIcon(getClass().getResource("/game/image/ship.png")).getImage();
	}
	
	public static final double ENEMY_SIZE = 64;
	private double x;
	private double y;
	private float angle = 0f;
	private final Image enemy;
	private boolean speedUp;
	
	public void draw(Graphics2D g2) {
		AffineTransform oldTransform = g2.getTransform();
		
		g2.translate(x, y);
		
		AffineTransform tran = new AffineTransform();
		tran.rotate(Math.toRadians(angle + 90), ENEMY_SIZE / 2, ENEMY_SIZE / 2);
		
		g2.drawImage(speedUp ? enemy : enemy, tran, null);
		
		g2.setTransform(oldTransform);

		g2.draw(getBounds());
	}
	
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,enemy.getWidth(null), enemy.getHeight(null));
	}

	public void moveEnemy(double playerX, double playerY){
		double diffX = playerX - x;
		double diffY = playerY - y;

		float angleToFollow = (float)Math.toDegrees(Math.atan2(diffY, diffX));
		angle = angleToFollow;
		x += 0.15 * Math.cos(angle);
		y += 0.15 * Math.sin(angle);
		System.out.println(angle);

	}
}

package game.component;

import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.PointerInfo;
import java.awt.MouseInfo;
import java.lang.Thread;
import java.lang.Math;

import javax.swing.JPanel;

import java.awt.event.*;

import game.component.PanelGame;
import game.obj.Bullet;
import game.obj.Enemy;
import game.obj.EnemyHelper;
import game.obj.Player;
import game.component.Extras;

public class Model extends JPanel{
    //add thread to render graphics
	private Thread thread;

	private boolean start = true;
    //declare key object
	private Key key;

    static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	Random rand = new Random();
    // declare Extras
	private Extras extras;
	//declare player object
	private Player player;
	private Enemy enemy1;
	private EnemyHelper enemyHelper;
    public PanelGame panel = new PanelGame();
    
	private boolean over = false;
	private int health;
    int width;
    int height;
    private final int FPS = 60;
	private final int TARGET_TIME = 1000000000 / FPS;
	//define list for bullets
	private List <Bullet> bullets;
	int enemyCount = 5;

	public Model(){
		// super();
		this.panel = new PanelGame();
		// this.game.setGUI(this);
		// this.GUI.update();
  	}
	public PanelGame getGUI(){
		return panel;
	}

    public void start() {
		// width = getWidth();
		// height = getHeight();
        width = 1200;
		height = 600;

		//initialize thread object with runnable
		thread = new Thread(new Runnable() {
			//run method
			public void run() {
				while(start && !player.over) {
					long startTime = System.nanoTime(); //gets the current time in nano seconds

					//subtracts the start time from current time to determine the delay when rendering
					long time = System.nanoTime() - startTime;

					//if time is smaller than TARGET_TIME then delay the loop
					if(time < TARGET_TIME) {
						//determine how many times to delay the loop
						long sleep = (TARGET_TIME - time) / 1000000; //converts nanoseconds to milliseconds
						sleep(sleep); 
					}
					panel.updateView();
					// health = player.health;
					// if(health == 0){
					// 	gameOver = true;
					// }
				}

			}
		});

		// for(int i = 0; i < enemyCount; i++ ){
		// 	addEnemy(new Enemy(rand.nextInt(1000), rand.nextInt(600)));
		// }
		enemyHelper.addEnemies(enemyCount);
		//add initialized methods 
        initObjectGame();
		initKeyboard();
		initBullets();


		//start thread
		thread.start();
	}
	
	private void initObjectGame() {
		player = new Player(150, 150);
		health = player.health;
	}
	
	private void initKeyboard() {
		key = new Key();
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_A) {
					key.setKey_left(true);
				}else if(e.getKeyCode() == KeyEvent.VK_D) {
					key.setKey_right(true);
				}else if(e.getKeyCode() == KeyEvent.VK_W) {
					key.setKey_up(true);
				}else if(e.getKeyCode() == KeyEvent.VK_S) {
					key.setKey_down(true);
				}else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					key.setKey_space(true);
				}else if(e.getKeyCode() == KeyEvent.VK_J) {
					key.setKey_j(true);
				}else if(e.getKeyCode() == KeyEvent.VK_K) {
					key.setKey_k(true);
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_A) {
					key.setKey_left(false);
				}else if(e.getKeyCode() == KeyEvent.VK_D) {
					key.setKey_right(false);
				}else if(e.getKeyCode() == KeyEvent.VK_W) {
					key.setKey_up(false);
				}else if(e.getKeyCode() == KeyEvent.VK_S) {
					key.setKey_down(false);
				}else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					key.setKey_space(false);
				}else if(e.getKeyCode() == KeyEvent.VK_J) {
					key.setKey_j(false);
				}else if(e.getKeyCode() == KeyEvent.VK_K) {
					key.setKey_k(false);
				}
			}
		});
		addMouseListener(new MouseListener() {
			
			public void mousePressed(MouseEvent me) { }
	        public void mouseReleased(MouseEvent me) {
				// key.setLeft_click(false);
			}
    	    public void mouseEntered(MouseEvent me) { }
        	public void mouseExited(MouseEvent me) { }
			@Override
			public void mouseClicked(MouseEvent me) { 
			  if(me.getButton() == MouseEvent.BUTTON1) {
				key.setLeft_click(true);
				System.out.println("Clicked");
			  }
			//   if(me.getButton() == MouseEvent.BUTTON3) {
			// 	label.setText("Right Click!");
			//   }
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				// float s = 0.5f;
				while(start) {

					
					double[] position = new double[]{player.getX(), player.getY()};
					if(enemies.size() > 0){
						enemies.get(0).moveEnemy(position[0], position[1]);
					}
					
					// To face the mouse pointer
					float angle = (float) player.getAngle();
					double[] r = new double[]{0, 0};
					PointerInfo point = MouseInfo.getPointerInfo();
					double xMouse = point.getLocation().x;
					double yMouse = point.getLocation().y;
					// x
					double a = xMouse - position[0] - 232;
					double b = yMouse - position[1] - 87;
		
					r[0] = Math.abs(a);
					// y
					r[1] = Math.abs(b);
		
					if(a > 0 && b < 0){
						angle = (float) (Math.toDegrees(Math.atan(r[0]/r[1])));	
						// System.out.printf("Q1 should be an angle of %f%n", angle);
					}else if(a > 0 && b > 0){
						angle = (float) (90 + Math.toDegrees(Math.atan(r[1]/r[0])));
						// System.out.printf("Q2 should be an angle of %f%n", angle);
					}else if(a < 0 && b > 0){
						angle = (float) (180 + Math.toDegrees(Math.atan(r[0]/r[1])));	
						// System.out.printf("Q3 should be an angle of %f%n", angle);
		
					}else if(a < 0 && b < 0){
						angle = (float) (270 + Math.toDegrees(Math.atan(r[1]/r[0])));	
						// System.out.printf("Q4 should be an angle of %f%n", angle);
						
					}
					// System.out.printf("Mouse x: %f Mouse y: %f -------- x: %f y: %f%n", xMouse, yMouse, position[0], position[1]);

					if(key.isKey_left()) {
						position[0] -= 0.2;
					}if(key.isKey_right()) {
						position[0] += 0.2;
					}
					if(key.isKey_up()) {
						position[1] -= 0.2;
					}if(key.isKey_down()) {
						position[1] += 0.2;
					}
										
					player.update();
					player.changeAngle(angle);
					player.changePosition(position[0], position[1]);

					if(key.isLeft_click() || key.isKey_k()) {
						float bulletAngle = angle - 90;
						if(bulletAngle < 0){
							bulletAngle = 360 - angle;
						}
                        if(key.isLeft_click()) {
                            bullets.add(0, new Bullet(position[0], position[1], (float) angle - 90, 5, 3f));
                            System.out.printf("x:%f y:%f", bullets.get(0).getX(), bullets.get(0).getY());
                            System.out.printf("Player x:%f Player y:%f%n", position[0], position[1]);

                            key.setLeft_click(false);

                        }
                    }
                    // }else {
                    // 	bullets.add(0, new Bullet(player.getX(), player.getY(), (float) angle - 90, 20, 3f));
                    // }
					// shotTime++;
					// if(shotTime == 0) {
					// 	shotTime = 0;
					// }
					// else {
					// 	shotTime = 0;
					// }
					

					// if(key.isKey_space()) {
					// 	player.speedUp();
					// }else {
					// 	player.speedDown();
					// }
					// sleep((long)3.5);
				}
			}
		}).start();
	}
	
	private void initBullets() {
		bullets = new ArrayList<>();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(start) {
					for(int i = 0; i < bullets.size(); i++) {
						Bullet bullet = bullets.get(i);
						if(bullet != null) {
							bullet.update();
							
							bullet.checkCollisions();
							// extras.scoreUpdate(touch);
							if(!bullet.check(width, height)) {
								bullets.remove(bullet);
							}
						}else {
							
							bullets.remove(bullet);
						}
					}
					sleep(1);
				}
			}
		}).start();
	}
	

	/**use a sleep method to delay the loop for display
	 * long has the same function as int
	 * can hold a lower negative value and higher positive value
	 **/
	public void sleep(long speed) {
		//use try catch
		try {
			Thread.sleep(speed);
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
	public void addEnemy(Enemy e){
		enemies.add(e);
	}
	public static ArrayList<Enemy> getEnemyList(){
		return enemies;
	}
	public static void removeEnemy(Enemy e){
        enemies.remove(e);
    }
    
}

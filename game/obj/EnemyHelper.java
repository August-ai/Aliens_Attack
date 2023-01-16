package game.obj;
import java.util.ArrayList;
import java.util.Random;

import game.obj.Bullet;
import game.obj.Enemy;
import game.obj.Player;
import game.component.Extras;

public class EnemyHelper {
    
    static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private int enemyCount = 5;
    
    Random rand = new Random();


    public void addEnemies(int enemyCount){
        for(int i = 0; i < enemyCount; i++ ){
			addEnemy(new Enemy(rand.nextInt(1000), rand.nextInt(600)));
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
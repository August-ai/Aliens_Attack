package game.main;
import java.awt.PointerInfo;
import java.awt.MouseInfo;
import java.lang.Thread;
import java.lang.Math;

public class maintest { 
    public static void main(String[] args) throws InterruptedException{
        int[] pos = new int[]{1000, 500};
        double angle = 0f;
        double[] r = new double[]{0, 0};
        while(true){
            PointerInfo point = MouseInfo.getPointerInfo();
            int x = point.getLocation().x;
            int y = point.getLocation().y;
            // x

            int a = x - pos[0];
            int b = y - pos[1];

            r[0] = Math.abs(a);
            // y
            r[1] = Math.abs(b);

            if(a > 0 && b < 0){
                angle = (float) (Math.toDegrees(Math.atan(r[0]/r[1])));	
                System.out.printf("Q1 %d %d should be an angle of %f%n", x, y, angle);
            }else if(a > 0 && b > 0){
                angle = (float) (90 + Math.toDegrees(Math.atan(r[1]/r[0])));
                System.out.printf("Q2 %d %d should be an angle of %f%n", x, y, angle);
            }else if(a < 0 && b > 0){
                angle = (float) (180 + Math.toDegrees(Math.atan(r[0]/r[1])));	
                System.out.printf("Q3 %d %d should be an angle of %f%n", x, y, angle);

            }else if(a < 0 && b < 0){
                angle = (float) (270 + Math.toDegrees(Math.atan(r[1]/r[0])));	
                System.out.printf("Q4 %d %d should be an angle of %f%n", x, y, angle);
                
            }
            
            Thread.sleep(250);
        }

    }
}
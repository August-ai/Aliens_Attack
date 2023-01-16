package game.component;

public class Extras {
    private int score = 0;
    public Extras(){
        this.score = score;
    }
    public void scoreUpdate(boolean kill){
        if(kill){
            this.score += 1;
        }
    }
}

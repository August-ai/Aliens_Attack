package game.main;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import game.component.PanelGame;
// import game.component.Model;


public class Main extends JFrame{
    
  public Main() {
    initialize();
  }
  
  public void initialize() {
    PanelGame panel = new PanelGame();
    // Model model = new Model();

    setTitle("Space Game");
    setSize(1200, 600);
    setLocationRelativeTo(null);
    setResizable(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    setLayout(new BorderLayout());
    // PanelGame panelGame = new PanelGame();
    
    add(panel);
    

    //adds the specified window listener to receive window events from this window
    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e){
        panel.start();
      }
    });
  }
  
  
  public static void main(String[] args) {
    Main main = new Main();
    main.setVisible(true);
  }
}
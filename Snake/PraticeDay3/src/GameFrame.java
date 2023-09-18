import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame (){
        this.add(new GamePanel());
        this.setTitle("My Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // takes our JFrame and fits around our components
        this.setVisible(true);
        this.setLocationRelativeTo(null); // centers to middle of screen
    }
}

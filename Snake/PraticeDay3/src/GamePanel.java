import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int screenWidth = 600;    // screen parameters
    static final int screenHeight = 600;
    static final int unitSize = 25; // objects
    static final int gameUnits = (screenWidth*screenHeight)/unitSize;
    static final int DELAY = 75;       // reaction delay
    final int[] x = new int[gameUnits]; // coordinates
    final int[] y = new int[gameUnits];
    int bodyParts = 6;
    int applesScored;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel (){
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        draw(gr);
    }

    public void draw(Graphics g){
        if (running) {
            // drawing lines on gamePanel
//            for (int i = 0; i < screenHeight / gameUnits; i++) {
//                g.drawLine(i * unitSize, 0, i * unitSize, screenHeight); // vertical lines
//                g.drawLine(0, i * unitSize, screenWidth, i * unitSize); // horizontal lines
//            }
            //
            g.setColor(Color.red);  // apple
            g.fillOval(appleX, appleY, unitSize, unitSize);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {    // head of snake
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                } else {    // body
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD,45));

            FontMetrics metrics = g.getFontMetrics(g.getFont());
            g.drawString("Score: "+applesScored, (screenWidth-metrics.stringWidth("Score: "+applesScored))/2,g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }

    public void newApple(){
        appleX = random.nextInt((int) screenWidth/unitSize)*unitSize;   // setting apple x/y
        appleY = random.nextInt((int) screenWidth/unitSize)*unitSize;
    }

    public void move(){
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];  // moving coordinates by 1 unitSize
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0] -unitSize;
                break;
            case 'D':
                y[0] = y[0] +unitSize;
                break;
            case 'L':
                x[0] = x[0] -unitSize;
                break;
            case 'R':
                x[0] = x[0] +unitSize;
                break;
        }
    }

    public void checkApple(){
        if ((x[0]== appleX)&& (y[0]==appleY)){ // head of snake touches apple
            bodyParts++;
            applesScored++;
            newApple();
        }
    }

    public void collisionCheck(){
        // head collision with body of snake
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])){
                running = false;

            }
        }
        // head collides with left border
        if (x[0]<0){
            running =false;
        }
        // head collides with right border
        if (x[0]>screenWidth){
            running = false;
        }
        // head collides with top border
        if (y[0]<0){
            running = false;
        }
        // head collides with bottom border
        if (y[0]>screenHeight){
            running = false;
        }
        if (!running){
            timer.stop();
        }
    }

    public void gameOver(Graphics gr){
        // gave over text
        gr.setColor(Color.red);
        gr.setFont(new Font("Ink Free", Font.BOLD,60));

        FontMetrics metrics = gr.getFontMetrics(gr.getFont());
        gr.drawString("Game Over", (screenWidth-metrics.stringWidth("Game over"))/2,screenHeight/2);
        // game over score
        gr.setColor(Color.red);
        gr.setFont(new Font("Ink Free", Font.BOLD,45));

        FontMetrics metrics2 = gr.getFontMetrics(gr.getFont());
        gr.drawString("Score: "+applesScored, (screenWidth-metrics2.stringWidth("Score: "+applesScored))/2,gr.getFont().getSize());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            collisionCheck();
        }
        repaint();
    }

    // inner class
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent event){
            switch (event.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}

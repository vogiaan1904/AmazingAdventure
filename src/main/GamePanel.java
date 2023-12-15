package main;
import entity.Player;
import main.object.SuperObject;
import tiles.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.io.IOException;
import java.security.Key;

public class GamePanel extends JPanel implements Runnable{
    //SCREEN setting
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize*scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize*maxScreenCol;
    public final int screenHeight = tileSize*maxScreenRow;
    //WORLD setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth= maxWorldCol*tileSize;
    public final int worldHeight= maxWorldRow*tileSize;
    //FPS
    int FPS = 60;
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public UI ui = new UI(this);

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;
    public Player player = new Player(this,keyH);
    public SuperObject[] obj = new SuperObject[10];
    //this doesn't mean having only 10 objs, but can displaying 10 objs
    // at the same time
    public int gameState;
    public final int playState = 1; //telling the program what
    //kind of state we are in.
    //For example: Enter => swing the sword, but in the menu screen, Enter key works as a confirmation key
    public final int pauseState = 2;

    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //improve game's rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame(){//we want to call set obj be4 the game start
        aSetter.setObject();
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        gameState = playState;
    }
    @Override
    public void run(){
        double drawInterval = (double) 1000000000 /FPS;  // 0.01666 seconds = frame
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/drawInterval;
            lastTime = currentTime;
            if(delta>=1){
                update();
                repaint();
                delta--;
            }
        }
    }
    public void update(){//update position of the player
        if(gameState == playState){
            player.update();
        }
        if(gameState == pauseState){

        }
    }
    public void paintComponent(Graphics g){
        long drawStart= 0;
        long drawEnd;
        if(keyH.checkDrawTime){
            drawStart = System.nanoTime();
        }
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        //tile
        tileM.draw(g2);
        //object
        for(int i =0;i<obj.length;i++){
             if(obj[i] != null){
                obj[i].draw(g2,this);
            }
        }
        //player
        player.draw(g2);
        //UI
        ui.draw(g2);
        if(keyH.checkDrawTime){
            drawEnd = System.nanoTime();
            g2.setColor(Color.white);
            long timePass = drawEnd-drawStart;
            g2.drawString("Draw time: " + timePass,10,400);
            System.out.println("Draw time: " + timePass);
        }
        g2.dispose();
    }
}

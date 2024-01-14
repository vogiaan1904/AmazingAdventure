package main;
import ai.PathFinder;
import entity.Entity;
import entity.Player;
import entity.Projectile;
import tile_interactive.InteractiveTile;
import tiles.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{
    //SCREEN setting
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize*scale; // 48pixel
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
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public UI ui = new UI(this);

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    //Entity and Object
    public Player player = new Player(this,keyH);
    public Entity[] obj = new Entity[10];
    public Entity[] npc =new Entity[10];
    public Entity[] monster =new Entity[10];
    //this doesn't mean having only 10 objs, but can displaying 10 objs
    // at the same time
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public InteractiveTile[] iTile = new InteractiveTile[50];
    public ArrayList<Entity> entityList = new ArrayList<>();


    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1; //telling the program what
    //kind of state we are in.
    //For example: Enter => swing the sword, but in the menu screen, Enter key works as a confirmation key
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

    public final int loseState = 5;
    public final int winState = 6;
    public PathFinder pFinder = new PathFinder(this);

    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //improve game's rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame(){//we want to call set obj be4 the game start
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        gameState = titleState;
    }
    public void resetGame(){//we want to call set obj be4 the game start
        player.resetGame();
        entityList.clear();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();

        gameState = playState;
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        gameState = titleState;
    }
    public void endGameThread(){
        gameThread = null;
    }
    @Override
    public void run(){
        double drawInterval = (double) 1000000000/FPS;  // 0.01666 seconds = frame
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
    public void update(){
        if(player.life == 0 ){
            gameState = loseState;
        }

        //update position of the player
        if(gameState == playState){
            //Player
            player.update();
            //NPC
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
            for(int i=0;i<monster.length;i++){
                if(monster[i] != null){
                    if(monster[i].alive && !monster[i].dying){
                        monster[i].update();
                    }
                    if(!monster[i].alive){
                        monster[i] = null;
                    }
                }
            }
            for(int i=0;i<projectileList.size();i++){
                if(projectileList.get(i) != null){
                    if(projectileList.get(i).alive){
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive){
                        projectileList.remove(i);
                    }
                }
            }
            for (InteractiveTile interactiveTile : iTile) {
                if (interactiveTile != null) {
                    interactiveTile.update();
                }
            }

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

        //title screen
        if(gameState == titleState){
            ui.draw(g2);
        }
        else {
            //Tile
            tileM.draw(g2);

            //Interactive Tile
            for (InteractiveTile interactiveTile : iTile) {
                if (interactiveTile != null) {
                    interactiveTile.draw(g2);
                }
            }
            //Add entities to the list
            entityList.add(player);
            for (Entity entity1 : npc) {
                if (entity1 != null) {
                    entityList.add(entity1);
                }
            }
            for (Entity element : obj) {
                if (element != null) {
                    entityList.add(element);
                }
            }
            for (Entity item : monster) {
                if (item != null) {
                    entityList.add(item);
                }
            }
            for (Entity value : projectileList) {
                if (value != null) {
                    entityList.add(value);
                }
            }

            entityList.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            //Draw entities
            for (Entity entity : entityList) {
                entity.draw(g2);
            }
            //Empty entities list
            entityList.clear();
            //UI
            ui.draw(g2);
        }

        //Debug
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

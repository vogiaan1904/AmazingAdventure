package main;
import entity.Entity;
import entity.Player;
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
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public UI ui = new UI(this);

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    //Entity and Object
    public Player player = new Player(this,keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[]  =new Entity[10];
    public Entity monster[]  =new Entity[10];
    //this doesn't mean having only 10 objs, but can displaying 10 objs
    // at the same time
    ArrayList<Entity> entityList = new ArrayList<>();


    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1; //telling the program what
    //kind of state we are in.
    //For example: Enter => swing the sword, but in the menu screen, Enter key works as a confirmation key
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

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
        gameState = titleState;
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        gameState = titleState;
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
            //Player
            player.update();
            //NPC
            for(int i=0;i<npc.length;i++){
                if(npc[i] != null){
                    npc[i].update();
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

        //title screen
        if(gameState == titleState){
            ui.draw(g2);
        }
        else {
            tileM.draw(g2);
            //Add entities to the list
            entityList.add(player);
            for(int i=0;i<npc.length;i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }
            for(int i=0;i<obj.length;i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }
            for(int i=0;i<monster.length;i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //Draw entities
            for(int i=0;i<entityList.size();i++){
                entityList.get(i).draw(g2);
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

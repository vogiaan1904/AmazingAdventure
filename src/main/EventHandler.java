package main;
import com.sun.source.tree.BreakTree;
import java.awt.*;
public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col <gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23; // in the middle of the tile
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
    }
    public void checkEvent(){

        //check if the Player character is more 1 tile away the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance  = Math.max(xDistance,yDistance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
            resetEventDone();
        }
        if(canTouchEvent){
            // MID - UP 2 SIDES
            for(int j =22; j<=24; j+=2){
                String direct = "left";
                if(j==24){
                    direct = "right";
                }
                for(int i=12; i<=18; i++){
                    if(hit(j,i,direct)){
                        //event happens
                        damagePit(j,i,gp.playState);
                    }
                }
            }
            //MID - BOT 2 SIDES
            for(int j =22; j<=24; j+=2){
                String direct = "left";
                if(j==24){
                    direct = "right";
                }
                for(int i=23; i<=34; i++){
                    if(hit(j,i,direct)){
                        //event happens
                        damagePit(j,i,gp.playState);
                    }
                }
            }

            //HEALING POOL
            for(int i = 21; i<=25; i++){
                if(hit(i,12,"any")){
                    healingPool(i,13,gp.playState);
                }
            }
            //WON STAR
            if(hit(11,19,"any")){
                winGame(11,19, gp.playState);
            }

        }
    }
    public boolean hit(int col, int row, String reqDirection){
        boolean hit = false;
        //update the position of player's rectangle
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")){
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }
    public void damagePit(int col, int row, int gameState){
        gp.gameState = gameState;
        if(gp.player.life>=2){
            gp.player.life -= 1;
        }
        eventRect[col][row].eventDone = true;
    }
    public void winGame(int col, int row, int gameState){
        if(gameState!= gp.winState){
            gp.gameState = gp.winState;
        }
    }
    public void healingPool(int col, int row,int gameState){
            gp.gameState = gameState;
            if(gp.player.life>=1 && gp.player.life <6){
                gp.player.life =6 ;
                gp.ui.currentDialogue="You drink the holy water \n and get full health";
            }else gp.player.life = 6;

        eventRect[col][row].eventDone = true;
        canTouchEvent = false;

    }

    private void resetEventDone() {
        for (int col = 0; col < gp.maxWorldCol; col++) {
            for (int row = 0; row < gp.maxWorldRow; row++) {
                eventRect[col][row].eventDone = false;
            }
        }
    }
}


package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Monster_Slime extends Entity {
    GamePanel gp;

    public Monster_Slime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        onPath = false;
        type = 2;
        attack = 2;
        name = "Slime";
        speed = 2;
        maxLife = 15;
        life = maxLife;
        solidArea = new Rectangle();
        solidArea.x = 3;
        solidArea.y = 10;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 42;
        solidArea.height = 30;
        getImage();
    }

    public void setAction(){
        if(onPath){
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol, goalRow);
        }else{
            actionLockCounter++;
            if(actionLockCounter == 60){ // lock for 60 frames / 1s
                //simplest AI
                Random random = new Random();
                int i = random.nextInt(100)+1;
                if(i<=25){
                    direction = "up";
                }
                if(i>25 && i<=50){
                    direction = "down";
                }
                if(i>50 && i<=75){
                    direction = "left";
                }
                if(i>75){
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }
    public void getImage(){
        down1 = setup("/greenslime_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/greenslime_down_2",gp.tileSize,gp.tileSize);
    }
}

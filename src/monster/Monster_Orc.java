package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class Monster_Orc extends Entity {
    GamePanel gp;
    public Monster_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;
        onPath = false;
        type = 2;
        attack = 2;
        name = "Orc";
        speed = 3;
        maxLife = 15;
        life = maxLife;
        solidArea = new Rectangle();
        solidArea.x = 14;
        solidArea.y = 28;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 20;
        solidArea.height = 20;
        attackArea.width=36;
        attackArea.height=36;
        getImage();
    }


    public void getImage(){
        up1 = setup("/monster/orc_attack_up_1",gp.tileSize,gp.tileSize*2);
        up2 = setup("/monster/orc_attack_up_2",gp.tileSize,gp.tileSize*2);
        down1 = setup("/monster/orc_attack_down_1",gp.tileSize,gp.tileSize*2);
        down2 = setup("/monster/orc_attack_down_2",gp.tileSize,gp.tileSize*2);
        left1 = setup("/monster/orc_attack_left_1",gp.tileSize*2,gp.tileSize);
        left2 = setup("/monster/orc_attack_left_2",gp.tileSize*2,gp.tileSize);
        right1 = setup("/monster/orc_attack_right_1",gp.tileSize*2,gp.tileSize);
        right2= setup("/monster/orc_attack_right_2",gp.tileSize*2,gp.tileSize);
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
    public void damageReaction(){
        actionLockCounter=0;
        direction = gp.player.direction;
    }
}

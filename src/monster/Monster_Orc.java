package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Monster_Orc extends Entity {
    GamePanel gp;
    public Monster_Orc(GamePanel gp) {
        super(gp);
        this.gp = gp;
        onPath = false;
        type = 2;
        attack = 1;
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

        attackArea.width=34;
        attackArea.height=34;
        attackArea.x = 7;
        attackArea.y = 7;
        getImage();
        getAttackImage();
    }
    public void getImage(){
        up1 = setup("/monster/orc_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/monster/orc_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/monster/orc_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/monster/orc_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/monster/orc_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/monster/orc_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/monster/orc_right_1",gp.tileSize,gp.tileSize);
        right2= setup("/monster/orc_right_2",gp.tileSize,gp.tileSize);
    }

    public void getAttackImage(){
        attackUp1 = setup("/monster/orc_attack_up_1",gp.tileSize,gp.tileSize*2);
        attackUp2 = setup("/monster/orc_attack_up_2",gp.tileSize,gp.tileSize*2);
        attackDown1 = setup("/monster/orc_attack_down_1",gp.tileSize,gp.tileSize*2);
        attackDown2 = setup("/monster/orc_attack_down_2",gp.tileSize,gp.tileSize*2);
        attackLeft1 = setup("/monster/orc_attack_left_1",gp.tileSize*2,gp.tileSize);
        attackLeft2 = setup("/monster/orc_attack_left_2",gp.tileSize*2,gp.tileSize);
        attackRight1 = setup("/monster/orc_attack_right_1",gp.tileSize*2,gp.tileSize);
        attackRight2 = setup("/monster/orc_attack_right_2",gp.tileSize*2,gp.tileSize);
    }

    public void setAction(){
        if(onPath){
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol, goalRow);

        } else {
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
        if(!attacking){
            checkAttackOrNot(30, gp.tileSize*4, gp.tileSize);
        }
    }
    public void damageReaction(){
        actionLockCounter=0;
        direction = gp.player.direction;
    }

}

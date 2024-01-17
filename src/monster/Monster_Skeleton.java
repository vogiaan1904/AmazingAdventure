package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Monster_Skeleton extends Entity {
    GamePanel gp;
    public Monster_Skeleton(GamePanel gp) {
        super(gp);
        this.gp = gp;
        onPath = false;
        type = 2;
        attack = 2;
        name = "Skeleton";
        defaultspeed = 2;
        speed = defaultspeed;
        maxLife = 50;
        life = maxLife;
        solidArea = new Rectangle();
        solidArea.x = 28;
        solidArea.y = 52;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 40;
        solidArea.height = 44;

        attackArea.width=48;
        attackArea.height=48;
        getImage();
        getAttackImage();
    }
    public void getImage(){
        up1 = setup("/monster/skeletonlord_phase2_up_1",gp.tileSize*2,gp.tileSize*2);
        up2 = setup("/monster/skeletonlord_phase2_up_2",gp.tileSize*2,gp.tileSize*2);
        down1 = setup("/monster/skeletonlord_phase2_down_1",gp.tileSize*2,gp.tileSize*2);
        down2 = setup("/monster/skeletonlord_phase2_down_2",gp.tileSize*2,gp.tileSize*2);
        left1 = setup("/monster/skeletonlord_phase2_left_1",gp.tileSize*2,gp.tileSize*2);
        left2 = setup("/monster/skeletonlord_phase2_left_2",gp.tileSize*2,gp.tileSize*2);
        right1 = setup("/monster/skeletonlord_phase2_right_1",gp.tileSize*2,gp.tileSize*2);
        right2= setup("/monster/skeletonlord_phase2_right_2",gp.tileSize*2,gp.tileSize*2);
    }

    public void getAttackImage(){
        attackUp1 = setup("/monster/skeletonlord_phase2_attack_up_1",gp.tileSize*2,gp.tileSize*4);
        attackUp2 = setup("/monster/skeletonlord_phase2_attack_up_2",gp.tileSize*2,gp.tileSize*4);
        attackDown1 = setup("/monster/skeletonlord_phase2_attack_down_1",gp.tileSize*2,gp.tileSize*4);
        attackDown2 = setup("/monster/skeletonlord_phase2_attack_down_2",gp.tileSize*2,gp.tileSize*4);
        attackLeft1 = setup("/monster/skeletonlord_phase2_attack_left_1",gp.tileSize*4,gp.tileSize*2);
        attackLeft2 = setup("/monster/skeletonlord_phase2_attack_left_2",gp.tileSize*4,gp.tileSize*2);
        attackRight1 = setup("/monster/skeletonlord_phase2_attack_right_1",gp.tileSize*4,gp.tileSize*2);
        attackRight2 = setup("/monster/skeletonlord_phase2_attack_right_2",gp.tileSize*4,gp.tileSize*2);
    }

    public void setAction(){

        if(onPath){
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath(goalCol, goalRow);
        }
        else {
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

        //check if it attacks
        if(!attacking){
            checkAttackOrNot(30, gp.tileSize*6, gp.tileSize*3);
        }
    }
    public void damageReaction(){
        actionLockCounter=0;
        direction = gp.player.direction;
    }
}

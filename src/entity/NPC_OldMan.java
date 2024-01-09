package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class NPC_OldMan extends Entity{
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction ="down";
        speed =1;
        getImage();
        setDialogue();
    }
    public void setDialogue(){
        dialogues[0] = "Hi, adventurer";
        dialogues[1] = "I'm the landlord!! \n begging me for mercy, bastard";
        dialogues[2] = "Hey son!!";
        dialogues[3] = "I'm Jeda";
    }
    public void speak(){
        super.speak();
        onPath = true;
    }

    public void getImage(){
        up1 = setup("/npc/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/npc/oldman_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/npc/oldman_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/npc/oldman_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/npc/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/npc/oldman_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/npc/oldman_right_1",gp.tileSize,gp.tileSize);
        right2= setup("/npc/oldman_right_2",gp.tileSize,gp.tileSize);
    }

    public void setAction(){
        if(onPath){
            int goalCol = 11;
            int goalRow = 9;
            searchPath(goalCol, goalRow);
        }else {
            actionLockCounter++;
            if(actionLockCounter == 120){ // lock for 120 frames / 2s
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
}

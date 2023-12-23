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
        dialogues[1] = "I'm your father";
        dialogues[2] = "Hey son!!";
        dialogues[3] = "I'm Jeda";
    }
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0 ;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

    }

    public void getImage(){
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2= setup("/npc/oldman_right_2");
    }

    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == 120){ // lock for 120 frames / 2s
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
            //simplest AI ever :)))
            actionLockCounter = 0;
        }
    }

}

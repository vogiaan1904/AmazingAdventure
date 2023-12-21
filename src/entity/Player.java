package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    KeyHandler keyH;
    public final int screenX; // the position of the player ON THE SCREEN
    public final int screenY;
    public Player(GamePanel qp, KeyHandler keyH){
        super(qp);
        this.keyH = keyH;
        screenX = qp.screenWidth/2 - (qp.tileSize/2);// to make sure that the player is always at the center(- qp.tileSize/2)
        screenY = qp.screenHeight/2 - (qp.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 14;
        solidArea.y = 28;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 20;
        solidArea.height = 20;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){
        worldX= gp.tileSize*23; // the position of the player ON THE MAP
        worldY= gp.tileSize*23;
        speed=8;
        direction = "down";

        //Player Status
        maxLife = 6; // 2 lives = 1 heart
        life = maxLife;
    }
    public void getPlayerImage(){
        up1 = setup("/player/boy_up_1-1");
        up2 = setup("/player/boy_up_2-1");
        down1 = setup("/player/boy_down_1-1");
        down2 = setup("/player/boy_down_2-1");
        left1 = setup("/player/boy_left_1-1");
        left2 = setup("/player/boy_left_2-1");
        right1 = setup("/player/boy_right_1-1");
        right2= setup("/player/boy_right_2-1");
    }
    public void update(){
        if(keyH.downPressed || keyH.upPressed || keyH.leftPressed || keyH.rightPressed){
            if(keyH.upPressed){
                direction = "up";
            }else if(keyH.downPressed){
                direction = "down";
            }else if(keyH.leftPressed){
                direction = "left";
            } else if(keyH.rightPressed) {
                direction = "right";
            }

            //check tile Collision
            collisionON = false;
            gp.cChecker.checkTile(this);
            // if collision is false => player can move

            //check object Collision
            int objectIndex = gp.cChecker.checkObject(this, true);
            pickupObject(objectIndex);

            //check Npc collision
            int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
            interactNPC(npcIndex);

            //check event
            gp.eHandler.checkEvent();
            gp.keyH.enterPressed = false;
            if(!collisionON){
                switch (direction){
                    case "down": worldY+=speed;break;
                    case "up": worldY-= speed;break;
                    case "left": worldX-=speed;break;
                    case "right": worldX+=speed;break;
                }
            }
            spriteCounter++;
            if(spriteCounter>10){ //make the animation (each 10 frames)
                if(spriteNum==1){
                    spriteNum = 2;
                }else if(spriteNum ==2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void pickupObject(int i){
        if(i!=999){
        }
    }
    public void interactNPC(int i){
        if(i!=999){
            System.out.println("Watch out, mtfk!");
        }
    }
    public void draw(Graphics2D g2){ // draw the movement of player
        BufferedImage image = null;
        switch (direction){
            case "up" :
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down" :
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left" :
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right" :
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image,screenX,screenY,null);//draw the player
    }
}

package entity;

import main.GamePanel;
import main.KeyHandler;
import main.object.Object_Axe;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Objects;

public class Player extends Entity{
    KeyHandler keyH;
    public final int screenX; // the position of the player ON THE SCREEN
    public final int screenY;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int inventorySize = 20;
    public Entity currentWeapon;
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
        attackArea.width=36;
        attackArea.height=36;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }
    public void setDefaultValues(){
        worldX= gp.tileSize*23; // the position of the player ON THE MAP
        worldY= gp.tileSize*23;
        speed=5;
        direction = "down";

        //Player Status
        maxLife = 6; // 2 lives = 1 heart
        life = maxLife;
        currentWeapon = new Object_Axe(gp);
    }
    public void setItems(){
        inventory.add(currentWeapon);
    }
    public void getPlayerAttackImage(){
        attackUp1 = setup("/player/att_up_1",gp.tileSize, gp.tileSize*2);
        attackUp2 = setup("/player/att_up_2",gp.tileSize, gp.tileSize*2);
        attackDown1 = setup("/player/att_down_1",gp.tileSize, gp.tileSize*2);
        attackDown2 = setup("/player/att_down_2",gp.tileSize, gp.tileSize*2);
        attackLeft1 = setup("/player/att_left_1",gp.tileSize*2,gp.tileSize);
        attackLeft2 = setup("/player/att_left_2",gp.tileSize*2,gp.tileSize);
        attackRight1 = setup("/player/att_right_1",gp.tileSize*2,gp.tileSize);
        attackRight2 = setup("/player/att_right_2",gp.tileSize*2,gp.tileSize);
    }
    public void getPlayerImage(){
        up1 = setup("/player/boy_up_1-1",gp.tileSize,gp.tileSize);
        up2 = setup("/player/boy_up_2-1",gp.tileSize,gp.tileSize);
        down1 = setup("/player/boy_down_1-1",gp.tileSize,gp.tileSize);
        down2 = setup("/player/boy_down_2-1",gp.tileSize,gp.tileSize);
        left1 = setup("/player/boy_left_1-1",gp.tileSize,gp.tileSize);
        left2 = setup("/player/boy_left_2-1",gp.tileSize,gp.tileSize);
        right1 = setup("/player/boy_right_1-1",gp.tileSize,gp.tileSize);
        right2= setup("/player/boy_right_2-1",gp.tileSize,gp.tileSize);
    }
    public void update(){
        if(attacking){
            attacking();
        }
        else if (keyH.downPressed || keyH.upPressed
                || keyH.leftPressed || keyH.rightPressed ||
                keyH.enterPressed){
            if(keyH.upPressed){
                direction = "up";
            }else if(keyH.downPressed){
                direction = "down";
            }else if(keyH.leftPressed){
                direction = "left";
            } else if(keyH.rightPressed) {
                direction = "right";
            }

            int entityRow = (int)this.worldY/gp.tileSize;
            int entityCol = (int)this.worldX/gp.tileSize;

            System.out.println("Player Position Col:"+ entityCol + " Row: "+entityRow);

            //check tile Collision
            collisionON = false;
            gp.cChecker.checkTile(this);
            // if collision is false => player can move

            //check object Collision
            int objectIndex = gp.cChecker.checkObject(this, true);
            pickupObject(objectIndex);

            //check NPC collision
            int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
            interactNPC(npcIndex);
            //check Monster collision
            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            contactMonster(monsterIndex);
            //check event
            gp.eHandler.checkEvent();
            // if collision is false, player can move
            if(!collisionON && !keyH.enterPressed){
                switch (direction){
                    case "down": worldY+=speed;break;
                    case "up": worldY-= speed;break;
                    case "left": worldX-=speed;break;
                    case "right": worldX+=speed;break;
                }

            }

            gp.keyH.enterPressed = false;

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
        else {
            standCounter++;
            if(standCounter == 20){
                spriteNum=1;
                standCounter=0;
            }
        }
        //this needs to be outside of key if statement
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter>60){
                invincible = false;
                invincibleCounter=0;
            }
        }
    }
    public void attacking(){
        spriteCounter++;
        if(spriteCounter<=5){
            spriteNum = 1;
        }
        if(spriteCounter>5 && spriteCounter <=25){
            spriteNum = 2;
            //save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //adjust player's worldX, worldY for the solidArea
            switch(direction){
                case "up":worldY-=attackArea.height;break;
                case "down":worldY+=attackArea.height;break;
                case "left":worldX-=attackArea.width;break;
                case "right":worldX+=attackArea.width;break;
            }

            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //check monster collision with the updated worldX, worldY
            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            damageMonster(monsterIndex);
            //after checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter>25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickupObject(int i){
        if(i!=999){
        }
    }
    public void interactNPC(int i){
        if(gp.keyH.enterPressed){
            if(i!=999){ // check if it's an NPC
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].speak();
            }else{ // it's not an NPC => a monster
                    attacking = true;
            }
            gp.keyH.enterPressed = false;
        }
    }

    public void contactMonster(int i){//used for player
        if(i!=999){
            if(!invincible){
                life-=1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i){//used for monsters
        if(i!= 999){
            if(!gp.monster[i].invincible){
                gp.monster[i].life -=1;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                if(gp.monster[i].life<=0){
                    gp.monster[i].dying = true;
                }
            }
        }
    }
    public void draw(Graphics2D g2){ // draw the movement of player
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction){
            case "up" :
                if(!attacking) {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                if(attacking){
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) image = attackUp1;
                    if (spriteNum == 2) image = attackUp2;
                }
                break;
            case "down" :
                if(!attacking){
                    if(spriteNum == 1)image = down1;
                    if(spriteNum == 2)image = down2;
                }
                if(attacking){
                    if(spriteNum == 1)image = attackDown1;
                    if(spriteNum == 2)image = attackDown2;
                }

                break;
            case "left" :
                if(!attacking){
                    if(spriteNum == 1)image = left1;
                    if(spriteNum == 2)image = left2;
                }
                if(attacking){
                    tempScreenX = screenX - gp.tileSize;
                    if(spriteNum == 1)image = attackLeft1;
                    if(spriteNum == 2)image = attackLeft2;
                }
                break;
            case "right" :
                if(!attacking){
                    if(spriteNum == 1)image = right1;
                    if(spriteNum == 2)image = right2;
                }
                if(attacking){
                    if(spriteNum == 1)image = attackRight1;
                    if(spriteNum == 2)image = attackRight2;
                }
                break;
        }
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
        }
        g2.drawImage(image,tempScreenX,tempScreenY,null);//draw the player
        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
    }
}

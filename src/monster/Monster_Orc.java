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

        }
        else if(attackingPlayer){
                damagePlayer();
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
    }
    public void damageReaction(){
        actionLockCounter=0;
        direction = gp.player.direction;
    }
    public void damagePlayer(){
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

            //after checking collision, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if(spriteCounter>25){
            spriteNum = 1;
            spriteCounter = 0;
            attackingPlayer = false;
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX; //worldX and worldY are the updating position
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        if(worldX + gp.tileSize > gp.player.worldX-gp.player.screenX &&
                worldX - gp.tileSize< gp.player.worldX+gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY-gp.player.screenY &&
                worldY - gp.tileSize< gp.player.worldY+gp.player.screenY){
            switch (direction){
                case "up" :
                    if(!attackingPlayer) {
                        if (spriteNum == 1) image = up1;
                        if (spriteNum == 2) image = up2;
                    }
                    if(attackingPlayer){
                        tempScreenY = screenY - gp.tileSize;
                        if (spriteNum == 1) image = attackUp1;
                        if (spriteNum == 2) image = attackUp2;
                    }
                    break;
                case "down" :
                    if(!attackingPlayer){
                        if(spriteNum == 1)image = down1;
                        if(spriteNum == 2)image = down2;
                    }
                    if(attackingPlayer){
                        if(spriteNum == 1)image = attackDown1;
                        if(spriteNum == 2)image = attackDown2;
                    }

                    break;
                case "left" :
                    if(!attackingPlayer){
                        if(spriteNum == 1)image = left1;
                         if(spriteNum == 2)image = left2;
                    }
                    if(attackingPlayer){
                        tempScreenX = screenX - gp.tileSize;
                        if(spriteNum == 1)image = attackLeft1;
                        if(spriteNum == 2)image = attackLeft2;
                    }
                    break;
                case "right" :
                    if(!attackingPlayer){
                        if(spriteNum == 1)image = right1;
                        if(spriteNum == 2)image = right2;
                    }
                    if(attackingPlayer){
                        if(spriteNum == 1)image = attackRight1;
                        if(spriteNum == 2)image = attackRight2;
                    }
                    break;
            }
            //Monster healBar
            if(type==2 && hpBarOn){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-2,screenY-14,gp.tileSize+4, 14);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX,screenY-12,(int)hpBarValue,10);

                hpBarCounter++;

                if(hpBarCounter>600){
                    hpBarCounter=0;
                    hpBarOn= false;
                }
            }
            if(invincible){
                hpBarOn = true;
                hpBarCounter=0;
                changeAlpha(g2,0.4f);
            }
            if(dying){
                dyingAnimation(g2);
            }
            g2.drawImage(image, tempScreenX, tempScreenY,null);
            changeAlpha(g2,1f);
        }
    }
}

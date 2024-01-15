package monster;

import entity.Entity;
import main.GamePanel;
import main.object.Object_Rock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Monster_Slime extends Entity {
    GamePanel gp;

    public Monster_Slime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        onPath = false;
        type = 2;
        attack = 1;
        name = "Slime";
        speed = 2;
        maxLife = 10;
        life = maxLife;
        solidArea = new Rectangle();
        solidArea.x = 3;
        solidArea.y = 10;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 42;
        solidArea.height = 30;
        getImage();
        projectile = new Object_Rock(gp,this);
    }

    public void setAction(){
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
        Random random = new Random();
        int i = random.nextInt(100)+1;
        if(i > 99 && !projectile.alive && shotAvailablCounter == 30){
            System.out.println("shot!");
            projectile.set(worldX,worldY,direction,true);
            gp.projectileList.add(projectile);
            shotAvailablCounter = 0;
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX; //worldX and worldY are the updating position
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + gp.tileSize > gp.player.worldX-gp.player.screenX &&
                worldX - gp.tileSize< gp.player.worldX+gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY-gp.player.screenY &&
                worldY - gp.tileSize< gp.player.worldY+gp.player.screenY){

            if(spriteNum == 1){
                image = down1;
            }
            if(spriteNum == 2){
                image = down2;
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
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            changeAlpha(g2,1f);
        }
    }
    public void getImage(){
        down1 = setup("/monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/monster/greenslime_down_2",gp.tileSize,gp.tileSize);
    }
}

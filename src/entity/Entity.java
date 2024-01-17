package entity;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Entity {
    GamePanel gp;
    public  int worldX,worldY;
    public  int speed;
    public BufferedImage up1,up2,down1,down2,right1,right2,left1,left2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction = "down";
    public int spriteCounter=0;
    public int standCounter=0;
    public int spriteNum =1;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionON = false;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean attackingPlayer = false;
    public int invincibleCounter = 0;
    public int maxLife;
    public int life;
    public int mana;
    public int maxMana;
    public int manaRecoverCounter = 0;
    public int attack;
    public int defaultspeed;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean isResting = false;
    public int restingCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;
    public Projectile projectile;
    public int shotAvailablCounter = 0;
    public int knockBackCounter = 0;
    public int useCost;
    public int numKey = 0;
    public int numFinalKey = 0;
    public String dialogues[] = new String[20];
    public int dialogueIndex = 0;
    public int invincibleTime = 60;
    public BufferedImage image,image2,image3;
    public String name;
    public boolean collision = false;
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int type_player = 1;
    public final int type_npc = 2;
    public final int type_monster = 3;
    public final int type_axe = 4;
    public final int type_consumable = 6;
    public final int type_fireBall = 7;
    public boolean onPath = false;
    public boolean loseKey = false;
    public boolean knockBack = false;
    public Entity attacker;
    public String knockBackDirection;
    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool utilityTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath+".png")));
            image = utilityTool.scaledImage(image,width,height);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;
        gp.pFinder.setNodes(startCol,startRow,goalCol,goalRow);
        if(gp.pFinder.search()){
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            } else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            } else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                if (enLeftX>nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if (enTopY > nextY && enLeftX > nextX) {
                 direction = "up";
                 checkCollistion();
                 if(collisionON) {
                     direction = "left";
                 }
            } else if (enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollistion();
                if(collisionON){
                    direction = "right";
                }
            } else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollistion();
                if(collisionON){
                    direction = "left";
                }
            } else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollistion();
                if(collisionON){
                    direction = "right";
                }
            }
        }
    }
    public void setAction(){
    }
    public void damageReaction(){}
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0 ;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        switch (gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    };

    public void checkCollistion(){
        collisionON = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        boolean contactPlayer = gp.cChecker.checkPlayer(this, false);
        if(contactPlayer && type == type_monster){
            damagePlayer(attack);
        }
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);

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


            if(this.type == 2){
                if(gp.cChecker.checkPlayer(this, false)){
                    damagePlayer(attack);
                }
            }else{
                //check monster collision with the updated worldX, worldY
                int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
                gp.player.damageMonster(monsterIndex,this,gp.player.axeDamage);

                int iTileIndex = gp.cChecker.checkEntity(this,gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                int projectileIndex = gp.cChecker.checkEntity(this,gp.projectile);
                damageProjectile(projectileIndex);
            }


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
    public void damageProjectile(int i){
        if(i!=999){
            Entity projectile = gp.projectile[i];
            projectile.alive = false;
            generateParticle(projectile,projectile);
        }

    }
    public Color getParticleColor(){
        Color color = null;
        return color;
    }
    public int getParticleSize(){
        int size = 0; // 6 pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed = 0;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 0;
        return maxLife;
    }
    public void generateParticle(Entity generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();
        Particle p1 = new Particle(gp,generator,color,size,speed,maxLife,-2,-1);
        Particle p2 = new Particle(gp,generator,color,size,speed,maxLife,2,-1);
        Particle p3 = new Particle(gp,generator,color,size,speed,maxLife,-2,1);
        Particle p4 = new Particle(gp,generator,color,size,speed,maxLife,2,1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }
    public void damagePlayer(int attack){
        if(!gp.player.invincible){
            gp.player.life -= attack;
            gp.player.invincible = true;
        }
    }
    public void knockBack(Entity target, Entity attacker){
        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += 5;
        target.knockBack = true;
    }
    public void update(){
        if(knockBack){
            checkCollistion();
            if(collisionON){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultspeed;
            }else if(!collisionON){
                switch (knockBackDirection){
                    case "down": worldY+=speed;break;
                    case "up": worldY-= speed;break;
                    case "left": worldX-=speed;break;
                    case "right": worldX+=speed;break;
                }
            }
            knockBackCounter++;
            if(knockBackCounter==10){
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultspeed;
            }
        }
        else if(attacking){
            attacking();
        }else {
            setAction();
            checkCollistion();
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
            if(invincible){
                invincibleCounter++;
                if(invincibleCounter>invincibleTime){
                    invincible = false;
                    invincibleCounter=0;
                }
            }
            if(isResting){
                restingCounter++;
                if(restingCounter>20){
                    isResting = false;
                    restingCounter = 0;
                }
            }
        }
    }
    public void checkAttackOrNot(int rate, int straight, int horizontal){
        boolean targetInRange = false;
        int xDistance = Math.abs(gp.player.worldX - this.worldX);
        int yDistance = Math.abs(gp.player.worldY - this.worldY);
        switch (direction){
            case "up":
                if(gp.player.worldY< worldY && yDistance < straight && xDistance < horizontal){
                    targetInRange = true;
                }break;
            case  "down":
                if(gp.player.worldY > worldY && yDistance < straight  && xDistance < horizontal){
                    targetInRange = true;
                }break;
            case  "left":
                if(gp.player.worldX < worldX && yDistance < straight  && xDistance < horizontal){
                    targetInRange = true;
                }break;
            case  "right":
                if(gp.player.worldX > worldX && yDistance < straight  && xDistance < horizontal){
                    targetInRange = true;
                }break;
        }
        if(targetInRange){
            int i = new Random().nextInt(rate);
            if(i==0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
            }
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
            int tempScreenX = screenX;
            int tempScreenY = screenY;
            switch (direction){
                case "up" :
                    if(!attacking) {
                        if (spriteNum == 1) image = up1;
                        if (spriteNum == 2) image = up2;
                    }
                    if(attacking){
                        tempScreenY = screenY - up1.getHeight();
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
                        tempScreenX = screenX - left1.getWidth();
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
            g2.drawImage(image, tempScreenX, tempScreenY, null);
            changeAlpha(g2,1f);
        }
    }
    public void dyingAnimation(Graphics2D g2){
        dyingCounter++;
        int i = 5;
        if(dyingCounter<=i){changeAlpha(g2,0f);}
        if(dyingCounter > i && dyingCounter <= i*2){changeAlpha(g2,1f);}
        if(dyingCounter > i*2 && dyingCounter <= i*3){changeAlpha(g2,0f);}
        if(dyingCounter > i*3 && dyingCounter <= i*4){changeAlpha(g2,1f);}
        if(dyingCounter > i*4 && dyingCounter <= i*5){changeAlpha(g2,0f);}
        if(dyingCounter > i*5 && dyingCounter <= i*6){changeAlpha(g2,1f);}
        if(dyingCounter > i*6 && dyingCounter <= i*7){changeAlpha(g2,0f);}
        if(dyingCounter > i*7 && dyingCounter <= i*8){changeAlpha(g2,0f);}
        if(dyingCounter>i*8){
            alive=false;
        }
    }
    public void changeAlpha(Graphics2D g2,float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));

    }
    public Entity(GamePanel gp){
        this.gp = gp;
    }

}

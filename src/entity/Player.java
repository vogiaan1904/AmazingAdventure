package entity;

import main.GamePanel;
import main.KeyHandler;
import main.object.Object_FireBall;
import monster.Monster_Orc;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;

public class Player extends Entity{
    public boolean unlockFireBall = false;
    KeyHandler keyH;
    Graphics2D g2;
    public final int screenX; // the position of the player ON THE SCREEN
    public final int screenY;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public Entity currentWeapon = null;
    public boolean isHoldingAxe = false;
    public Entity currentShield;
    Entity entity;
    int axeDamage;
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
    public void setDefaultPosition(){
        worldX= gp.tileSize*23; // the position of the player ON THE MAP
        worldY= gp.tileSize*23;
    }


    public void setDefaultValues(){
        setDefaultPosition();
        speed=6;
        direction = "down";
        //Player Status
        maxLife = 6; // 2 lives = 1 heart
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        attack = 5;
        projectile = new Object_FireBall(gp , this);

    }
    public void resetGame(){
        setDefaultValues();
        inventory.clear();
        isHoldingAxe= false;
        unlockFireBall = false;
        numFinalKey = 0;
        numKey = 0;
    }
    public void setItems(){
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
        gp.eHandler.checkEvent();
        if(attacking){
            attacking();
        }
        else if (keyH.downPressed || keyH.upPressed
                || keyH.leftPressed || keyH.rightPressed ||
                keyH.enterPressed)
        {
            if(keyH.upPressed){
                direction = "up";
            }else if(keyH.downPressed){
                direction = "down";
            }else if(keyH.leftPressed){
                direction = "left";
            } else if(keyH.rightPressed) {
                direction = "right";
            }

            /*int entityRow = (int)(this.worldY/gp.tileSize);
            int entityCol = (int)(this.worldX/gp.tileSize);

            System.out.println("Player Position Col:"+ entityCol + " Row: "+entityRow);*/

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

            //check interactive tile collision
            int iTile = gp.cChecker.checkEntity(this, gp.iTile);

            //check event
            gp.eHandler.checkEvent();

            //check monster if agro
            checkMonster();

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
        if(gp.keyH.shotKeyPressed && !projectile.alive && unlockFireBall && mana > 0 && shotAvailablCounter == 30){
            //set the default value
            projectile.set(worldX,worldY,direction,true);
            //add to the projectile list
            gp.projectileList.add(projectile);
            mana-= projectile.useCost;
            shotAvailablCounter=0;
        }

        //this needs to be outside of key if statement
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter>60){
                invincible = false;
                invincibleCounter=0;
            }
        }
        if(shotAvailablCounter<30){
            shotAvailablCounter++;
        }
        if(mana<maxMana){
            if(manaRecoverCounter<200  ){
                manaRecoverCounter++;
            }else {
                    mana++;
                    manaRecoverCounter=0;
            }
        }
    }



    // ... (other methods)

    public void pickupObject(int i) {
        String notification;
        gp.eHandler.checkEvent();
        if (i != 999 && (gp.obj[i].type == type_consumable || gp.obj[i].type == type_axe || gp.obj[i].type == type_fireBall)) {
            if (inventory.size() != maxInventorySize) {
                inventory.add(gp.obj[i]);
                gp.gameState = gp.dialogueState;

                notification = "Got a " + gp.obj[i].name + "!";
                gp.ui.currentDialogue = notification;

                if (gp.obj[i].type == type_axe) {
                    gp.gameState = gp.dialogueState;
                    notification = "Press Enter to use!";
                    gp.ui.currentDialogue = notification;

                    isHoldingAxe = true;
                    axeDamage = gp.obj[i].attack;

                }
                if (gp.obj[i].type == type_fireBall) {
                    gp.gameState = gp.dialogueState;
                    notification = "You've unlocked a new skill \n Press F to see what you can do !";
                    gp.ui.currentDialogue = notification;

                    int maxIndex = gp.currentMonsterIndex;
                    gp.monster[maxIndex] = new Monster_Orc(gp);
                    gp.monster[maxIndex].worldX = gp.tileSize*35;
                    gp.monster[maxIndex].worldY = gp.tileSize*40;
                    maxIndex++;

                    gp.monster[maxIndex] = new Monster_Orc(gp);
                    gp.monster[maxIndex].worldX = gp.tileSize*34;
                    gp.monster[maxIndex].worldY = gp.tileSize*42;
                    maxIndex++;

                    gp.monster[maxIndex] = new Monster_Orc(gp);
                    gp.monster[maxIndex].worldX = gp.tileSize*31;
                    gp.monster[maxIndex].worldY = gp.tileSize*42;
                    maxIndex++;

                    gp.monster[maxIndex] = new Monster_Orc(gp);
                    gp.monster[maxIndex].worldX = gp.tileSize*36;
                    gp.monster[maxIndex].worldY = gp.tileSize*37;
                    maxIndex++;

                    gp.currentMonsterIndex = maxIndex;
                    unlockFireBall = true;
                }
                if (gp.obj[i].name == "Key") {
                    numKey++;
                }
                if (gp.obj[i].name == "FinalKey") {
                    numFinalKey++;

                }
            }
            else {
                notification = "Your inventory is full!";
                gp.ui.currentDialogue = notification;

            }
            gp.obj[i] = null;
            gp.eHandler.checkEvent();
        }
    }
    public void interactNPC(int i){
        if(gp.keyH.enterPressed){
            if(i!=999) { // check if it's an NPC
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }else if(isHoldingAxe){
                attacking = true;
            }
            gp.keyH.enterPressed = false;
        }
    }

    public void contactMonster(int i){//used for player - get hit from monster
        if(i!=999){

        }
    }

    public void damageMonster(int i, int damage){//used for monsters
        if(i!= 999){
            if(!gp.monster[i].invincible){
                gp.monster[i].life -= damage;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();
                if(gp.monster[i].life<=0){
                    gp.monster[i].dying = true;
                }
            }
        }
    }
    public void checkMonster(){
        for(int i =0;i< gp.monster.length;i++){
            if(gp.monster[i] != null){
                int xDistance = Math.abs(gp.player.worldX - gp.monster[i].worldX);
                int yDistance = Math.abs(gp.player.worldY - gp.monster[i].worldY);
                int distance  = Math.max(xDistance,yDistance);
                if(distance <= 6*gp.tileSize){
                    gp.monster[i].onPath = true;
                }else {
                    gp.monster[i].onPath = false;
                }
            }
        }
    }
    public void damageInteractiveTile(int i){
        if(i!=999 && gp.iTile[i].destructible){
            if(!gp.iTile[i].invincible){
                gp.iTile[i].life -= axeDamage;
                gp.iTile[i].invincible = true;
                if(gp.iTile[i].life <= 0){
                    gp.iTile[i] = gp.iTile[i].getDestroyedForm();
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

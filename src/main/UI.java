package main;

import entity.Entity;
import main.object.Object_Heart;
import main.object.Object_Mana;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font Arial_40, Arial_60B;
    BufferedImage heart_full, heart_half, heart_blank, mana_blank, mana_full;
    public boolean messageOn = false;
    public boolean gameFinised = false;
    public String message = "";
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    int messageCounter;
    public String currentDialogue = " ";
    public int commandNum=0;
    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gp){
        this.gp = gp;
        Arial_40 = new Font("Arial", Font.PLAIN, 40);
        Arial_60B = new Font("Arial", Font.BOLD, 60);

        //Creat HUD object
        Entity heart = new Object_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        Entity mana = new Object_Mana(gp);
        mana_blank = mana.image;
        mana_full = mana.image2;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(Arial_40);
        g2.setColor(Color.white);
        //title screen
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //play game
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            if(gp.player.unlockFireBall){
                drawPLayerMana();
            }

        }
        //pause game
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            if(gp.player.unlockFireBall){
                drawPLayerMana();
            }
            drawPauseScreen();
        }
        //dialogue
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
        if(gp.gameState == gp.characterState){
            drawInventory();
        }if(gp.player.life==0){

            gp.gameState = gp.loseState;
            drawEndScreen();
        }
        //WIN GAME
        /*if(gp.player.E){
            drawWinScreen();
        }*/
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }

    public void drawTitleScreen(){
        g2.setColor(new Color(70,120,80));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,64F));
        String text = "Gangsta Vegas 2.0";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);

        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x,y ,gp.tileSize*3, gp.tileSize*2, null);

        text = "Play";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);

        if(commandNum ==0){
            g2.setColor(new Color(255,255,255));
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "Exit";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text,x,y);
        if(commandNum ==1){
            g2.setColor(new Color(255,255,255));
            g2.drawString(">",x-gp.tileSize,y);
        }
        }
    public void drawPlayerLife(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;
        //Draw blank heart
        while(i< gp.player.maxLife/2){
            g2.drawImage(heart_blank,x,y,null);
            i++;
            x+= gp.tileSize;
        }

        //Reset
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        //Draw current life
        while (i<gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i<gp.player.life){// increase half heart in each loop
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+=gp.tileSize;
        }
    }
    public void drawPLayerMana(){
        int x = gp.tileSize/2;
        int y = gp.tileSize/2+gp.tileSize+5;
        int i = 0;

        while(i< gp.player.maxMana){
            g2.drawImage(mana_blank,x,y,null);
            i++;
            x+= gp.tileSize/2 + 10;
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2+gp.tileSize+5;
        i = 0;

        while (i<gp.player.mana){
            g2.drawImage(mana_full,x,y,null);
            i++;
            x+= gp.tileSize/2 + 10;
        }
    };

    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y;
        y = gp.screenHeight/2;
        g2.drawString(text,x,y);
    }
    public void drawEndScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,64F));
        String text = "You Lose";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);


        text = "Play Again";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);

        if(commandNum ==0){
            g2.setColor(new Color(255,255,255));
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "Exit To Desktop";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text,x,y);
        if(commandNum ==1){
            g2.setColor(new Color(255,255,255));
            g2.drawString(">",x-gp.tileSize,y);
        }
    }
    public void drawDialogueScreen(){
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x,y,width,height);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x+= gp.tileSize;
        y+= gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line,x,y);
            y+=40;
        }
    }
    public void drawInventory(){
        //Frame
        int frameX = gp.tileSize*9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*6;
        int frameHeight = gp.tileSize*5;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //Slot
        final int slotXstart = frameX+20;
        final int slotYstart = frameY+20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        //Draw player's items
        for (int i =0;i<gp.player.inventory.size();i++){

            //Equip items
            if(gp.player.inventory.get(i) == gp.player.currentWeapon ||
                gp.player.inventory.get(i) == gp.player.currentShield){
                    g2.setColor(new Color(240,190, 90));
                    g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
            }
            g2.drawImage(gp.player.inventory.get(i).down1,slotX,slotY,null);

            slotX+= slotSize;
            if(i==4 || i==8 || i==14){
                slotY += slotSize;
                slotX =slotXstart ;
            }
        }

        //Cursor
        int cursorX = slotXstart + slotSize * slotCol;
        int cursorY = slotYstart + slotSize *slotRow;
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //Draw cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3 ));
        g2.drawRoundRect(cursorX,cursorY,cursorWidth,cursorHeight,10,10);
    }
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    public void drawWinScreen(){
        g2.setColor(new Color(70,120,80));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,64F));
        String text = "YOU WON";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,y);

        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x,y ,gp.tileSize*3, gp.tileSize*2, null);

        text = "Play Again";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text,x,y);

        if(commandNum ==0){
            g2.setColor(new Color(255,255,255));
            g2.drawString(">",x-gp.tileSize,y);
        }
        text = "Exit";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text,x,y);
        if(commandNum ==1){
            g2.setColor(new Color(255,255,255));
            g2.drawString(">",x-gp.tileSize,y);
        }
    }
}

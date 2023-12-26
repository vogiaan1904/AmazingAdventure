package main;

import entity.Entity;
import main.object.Object_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font Arial_40, Arial_60B;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public boolean gameFinised = false;
    public String message = "";
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    int messageCounter;
    public String currentDialogue = " ";
    public int commandNum=0;

    public UI(GamePanel gp){
        this.gp = gp;
        Arial_40 = new Font("Arial", Font.PLAIN, 40);
        Arial_60B = new Font("Arial", Font.BOLD, 60);

        //Creat HUD object
        Entity heart = new Object_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
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
        }
        //pause game
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        //dialogue
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
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
            if(i<gp.player.life){ // increase half heart in each loop
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x+=gp.tileSize;
        }
    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y;
        y = gp.screenHeight/2;
        g2.drawString(text,x,y);
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
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}

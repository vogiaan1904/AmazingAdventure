package main;

import main.object.Object_Heart;
import main.object.Object_Key;
import main.object.SuperObject;

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
    public UI(GamePanel gp){
        this.gp = gp;
        Arial_40 = new Font("Arial", Font.PLAIN, 40);
        Arial_60B = new Font("Arial", Font.BOLD, 60);

        //Creat HUD object
        SuperObject heart = new Object_Heart(gp);
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
        if(gp.gameState == gp.playState){
            drawPlayerLife();
        }
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
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
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}

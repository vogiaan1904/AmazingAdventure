package main;

import main.object.Object_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font Arial_40, Arial_60B;
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

        }
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
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

package entity.src.entity;

import entity.src.main.GamePanel;
import entity.src.main.KeyHandler;

import java.awt.*;

public class Player extends Entity {
    GamePanel qp;
    KeyHandler keyH;
    public Player(GamePanel qp, KeyHandler keyH){
        this.qp = qp;
        this.keyH = keyH;
        setDefaultValues();
    }
    public void setDefaultValues(){
        x=100;
        y=100;
        speed=4;
    }
    public void update(){
        if(keyH.upPressed){
            y-= speed;
        }else if(keyH.downPressed){
            y+=speed;
        }else if(keyH.leftPressed){
            x-=speed;
        } else if(keyH.rightPressed) {
            x+=speed;
        }
    }
    public void draw(Graphics2D g2){
        g2.setColor(Color.white);
        g2.fillRect(x,y,qp.tileSize,qp.tileSize);
    }
}

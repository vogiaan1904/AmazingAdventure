package main.object;

import entity.Entity;
import main.GamePanel;


public class Object_Heart extends Entity{
    public Object_Heart(GamePanel gp){
        super(gp);
        name = "Heart";
        image = setup("/objects/heart_full",gp.tileSize,gp.tileSize);
        image2 = setup("/objects/heart_half",gp.tileSize,gp.tileSize);
        image3 = setup("/objects/heart_blank",gp.tileSize,gp.tileSize);
    }
}

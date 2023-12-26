package main.object;

import entity.Entity;
import main.GamePanel;


public class Object_Heart extends Entity{
    public Object_Heart(GamePanel gp){
        super(gp);
        name = "Heart";
        image = setup("/objects/heart_full");
        image = setup("/objects/heart_half");
        image = setup("/objects/heart_blank");
    }
}

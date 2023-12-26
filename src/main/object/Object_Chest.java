package main.object;

import entity.Entity;
import main.GamePanel;

public class Object_Chest extends Entity {
    public Object_Chest(GamePanel gp){
        super(gp);
        name = "Chest";
        down1 = setup("/objects/chest");
    }
}

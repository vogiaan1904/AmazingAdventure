package main.object;

import entity.Entity;
import main.GamePanel;

public class Object_Axe extends Entity {
    public Object_Axe(GamePanel gp) {
        super(gp);
        name = "Axe";
        down1 = setup("/objects/axe");
    }
}

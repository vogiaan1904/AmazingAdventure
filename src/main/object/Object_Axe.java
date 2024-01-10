package main.object;

import entity.Entity;
import main.GamePanel;

public class Object_Axe extends Entity {
    public Object_Axe(GamePanel gp) {
        super(gp);
        attack = 5;
        type = type_axe;
        name = "Axe";
        down1 = setup("/objects/axe",gp.tileSize,gp.tileSize);
    }
}

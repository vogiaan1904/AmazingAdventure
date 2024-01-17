package main.object;

import entity.Entity;
import main.GamePanel;

public class Object_Star extends Entity {
    public Object_Star(GamePanel gp) {
        super(gp);
        type = type_consumable;
        name = "Star";
        down1 = setup("/objects/star_object",gp.tileSize,gp.tileSize);
    }
}

package main.object;

import entity.Entity;
import main.GamePanel;

public class Object_FinalKey extends Entity {
    public Object_FinalKey(GamePanel gp) {
        super(gp);
        type = type_consumable;
        name = "FinalKey";
        down1 = setup("/objects/key_final",gp.tileSize,gp.tileSize);
    }
}

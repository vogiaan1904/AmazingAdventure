package main.object;

import entity.Entity;
import main.GamePanel;

public class Object_Mana extends Entity {
    public Object_Mana(GamePanel gp) {
        super(gp);
        name = "Mana";
        image = setup("/objects/manacrystal_blank",gp.tileSize,gp.tileSize);
        image2 = setup("/objects/manacrystal_full",gp.tileSize,gp.tileSize);
    }
}

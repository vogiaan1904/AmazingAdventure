package main.object;

import entity.Entity;
import main.GamePanel;

public class Object_Metal_Plate extends Entity {
    public Object_Metal_Plate(GamePanel gp) {
        super(gp);
        type = 0;
        name = "Metal_Plate";
        down1 = setup("/objects/metalplate",gp.tileSize,gp.tileSize);
        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}

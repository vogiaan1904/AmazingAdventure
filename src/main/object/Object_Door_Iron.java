package main.object;

import entity.Entity;
import main.GamePanel;

public class Object_Door_Iron extends Entity {
    public Object_Door_Iron(GamePanel gp) {
        super(gp);
        type = 0;
        name = "Door";
        down1 = setup("/objects/door_iron",gp.tileSize,gp.tileSize);
        down2 = setup("/objects/door_opened",gp.tileSize,gp.tileSize);
        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}

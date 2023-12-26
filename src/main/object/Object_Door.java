package main.object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Object_Door extends Entity {
    public Object_Door(GamePanel gp) {
        super(gp);
        name = "Door";
        down1 = setup("/objects/door");
        collision = true;
        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}

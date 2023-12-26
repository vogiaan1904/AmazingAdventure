package main.object;
import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
public class Object_Boots extends Entity {
    public Object_Boots(GamePanel gp){
        super(gp);
        name = "Boots";
        down1 = setup("/objects/boots");
    }
}

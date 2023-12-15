package main.object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Object_Axe extends SuperObject{
    GamePanel gp;
    public Object_Axe(GamePanel gp) {
        this.gp = gp;
        name = "Axe";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/axe.png"));
            utilityTool.scaledImage(image,gp.tileSize,gp.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}

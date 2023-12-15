package main.object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Object_Door extends SuperObject {
    GamePanel gp;
    public Object_Door(GamePanel gp){
        this.gp = gp;
        try{
        name = "Door";
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
            utilityTool.scaledImage(image,gp.tileSize,gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}

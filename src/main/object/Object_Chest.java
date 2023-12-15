package main.object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Object_Chest extends SuperObject{
    GamePanel gp;
    public Object_Chest(GamePanel gp){
        this.gp = gp;
        name = "Chest";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
            utilityTool.scaledImage(image,gp.tileSize,gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

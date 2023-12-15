package main.object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Object_Key extends SuperObject{
    GamePanel gp;
    public Object_Key(GamePanel gp){
        this.gp = gp;
        name = "Key";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
            utilityTool.scaledImage(image,gp.tileSize,gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}

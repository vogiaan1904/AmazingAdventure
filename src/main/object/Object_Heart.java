package main.object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Object_Heart extends SuperObject{
    GamePanel gp;
    public Object_Heart(GamePanel gp){
        this.gp = gp;
        name = "Heart";
        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_full.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_half.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart_blank.png")));
            image = utilityTool.scaledImage(image,gp.tileSize,gp.tileSize);
            image2 = utilityTool.scaledImage(image2,gp.tileSize,gp.tileSize);
            image3 = utilityTool.scaledImage(image3,gp.tileSize,gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

package main.object;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
public class Object_Boots extends SuperObject{
    GamePanel gp;
    public Object_Boots(GamePanel gp){
        this.gp = gp;
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            utilityTool.scaledImage(image,gp.tileSize,gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

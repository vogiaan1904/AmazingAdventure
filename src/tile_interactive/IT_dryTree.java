package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IT_dryTree extends InteractiveTile{
    GamePanel gp;
    public IT_dryTree(GamePanel gp, int row, int col) {
        super(gp,col,row);
        this.gp = gp;
        life = 20;
        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;
        invincibleTime = 20;
        down1 = setup("/tiles_interactive/drytree",gp.tileSize,gp.tileSize);
        destructible = true;
    }
    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = true;
        return isCorrectItem;
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_Trunk(gp,worldY/gp.tileSize,worldX/gp.tileSize);
        return tile;
    }
    public Color getParticleColor(){
        Color color = new Color(65,50,30);
        return color;
    }
    public int getParticleSize(){
        int size = 6; // 6 pixels
        return size;
    }
    public int getParticleSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
    public void draw(Graphics2D g2){
        int screenX = worldX - gp.player.worldX + gp.player.screenX; //worldX and worldY are the updating position
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if(worldX + gp.tileSize > gp.player.worldX-gp.player.screenX &&
                worldX - gp.tileSize< gp.player.worldX+gp.player.screenX &&
                worldY + gp.tileSize> gp.player.worldY-gp.player.screenY &&
                worldY - gp.tileSize< gp.player.worldY+gp.player.screenY){

            g2.drawImage(down1, screenX,screenY, null);
        }
    }
}

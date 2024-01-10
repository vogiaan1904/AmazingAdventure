package tile_interactive;

import entity.Entity;
import main.GamePanel;

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
}

package tiles;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.IntStream;

public class TileManager {
    GamePanel qp;
    public Tile[] tile;
    public int mapTileNum[][];
    public TileManager(GamePanel qp) throws IOException {
        this.qp = qp;
        tile = new Tile[50];
        mapTileNum = new int[qp.maxWorldCol][qp.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldV2.txt");
    }
    public void getTileImage(){

        setup(0,"grass00",false);
        setup(1,"grass00",false);
        setup(2,"grass00",false);
        setup(3,"grass00",false);
        setup(4,"grass00",false);
        setup(5,"grass00",false);
        setup(6,"grass00",false);
        setup(7,"grass00",false);
        setup(8,"grass00",false);
        setup(9,"grass00",false);

        setup(10,"grass00",false);
        setup(11,"grass01",false);
        setup(12,"water00",true);
        setup(13,"water01",true);
        setup(14,"water02",true);
        setup(15,"water03",true);
        setup(16,"water04",true);
        setup(17,"water05",true);
        setup(18,"water06",true);
        setup(19,"water07",true);
        setup(20,"water08",true);
        setup(21,"water09",true);
        setup(22,"water10",true);
        setup(23,"water11",true);
        setup(24,"water12",true);
        setup(25,"water13",true);
        setup(26,"road00",false);
        setup(27,"road01",false);
        setup(28,"road02",false);
        setup(29,"road03",false);
        setup(30,"road04",false);
        setup(31,"road05",false);
        setup(32,"road06",false);
        setup(33,"road07",false);
        setup(34,"road08",false);
        setup(35,"road09",false);
        setup(36,"road10",false);
        setup(37,"road11",false);
        setup(38,"road12",false);
        setup(39,"earth",false);
        setup(40,"wall",true);
        setup(41,"tree",true);

    }
    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index]= new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/"+imageName+".png")));
            tile[index].image = uTool.scaledImage(tile[index].image,qp.tileSize,qp.tileSize);
            tile[index].collision = collision;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String path) throws IOException {
        try {
            InputStream is = getClass().getResourceAsStream(path); //import the txt file
            BufferedReader br = new BufferedReader(new InputStreamReader(is));// read the content of the txt file
            int col = 0;
            int row = 0;
            while (col < qp.maxWorldCol && row < qp.maxWorldRow) {
                String line = br.readLine(); // read the line as String
                while(col<qp.maxWorldCol){
                    String numbers[] = line.split(" ");//read the numbers in the String one by one split by " "
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == qp.maxWorldCol){
                    row++;
                    col=0;
                }
            }
            br.close();
        }catch(Exception e){
        }
    }
    public void draw(Graphics2D g2){ // draw the updating map
        int worldRow = 0; // the index to load the map
        int worldCol = 0;
        while (worldRow < qp.maxWorldRow && worldCol < qp.maxWorldCol){ // loop to draw the map row by row
            int tileNum = mapTileNum[worldCol][worldRow]; // get the tile according to the index
            int worldX = worldCol * qp.tileSize; //
            int worldY = worldRow * qp.tileSize;
            int screenX = worldX - qp.player.worldX + qp.player.screenX; //worldX and worldY are the updating position
            // of the player on the map, so that when the worldX and worldY change => the map on the screen will change
            int screenY = worldY - qp.player.worldY + qp.player.screenY;
            if(worldX + qp.tileSize > qp.player.worldX-qp.player.screenX &&
                worldX - qp.tileSize< qp.player.worldX+qp.player.screenX &&
                worldY + qp.tileSize> qp.player.worldY-qp.player.screenY &&
                worldY - qp.tileSize< qp.player.worldY+qp.player.screenY){
                g2.drawImage(tile[tileNum].image, screenX, screenY,null);
            }
            worldCol++;
            if(worldCol==qp.maxWorldCol){
                worldCol=0;
                worldRow++;
            }
        }
    }
}

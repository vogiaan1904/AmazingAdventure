package main;

import entity.NPC_OldMan;
import main.object.*;
import monster.Monster_Orc;
import tile_interactive.IT_dryTree;
import tile_interactive.InteractiveTile;

public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        int i = 0;
        gp.obj[i] = new Object_Door(gp);
        gp.obj[i].worldX = gp.tileSize*8;
        gp.obj[i].worldY = gp.tileSize*27;
        i++;
        gp.obj[i] = new Object_Key(gp);
        gp.obj[i].worldX = gp.tileSize*21;
        gp.obj[i].worldY = gp.tileSize*18;
        i++;
        gp.obj[i] = new Object_Axe(gp);
        gp.obj[i].worldX = gp.tileSize*23;
        gp.obj[i].worldY = gp.tileSize*38;
        i++;
        gp.obj[i] = new Object_Door(gp);
        gp.obj[i].worldX = gp.tileSize*38;
        gp.obj[i].worldY = gp.tileSize*12;
        i++;

        gp.obj[i] = new Object_Chest(gp);
        gp.obj[i].worldX = gp.tileSize*35;
        gp.obj[i].worldY = gp.tileSize*8;
        i++;

        gp.obj[i] = new Object_Door(gp);
        gp.obj[i].worldX = gp.tileSize*9;
        gp.obj[i].worldY = gp.tileSize*11;
        i++;

    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
    public void setMonster(){
        gp.monster[0] = new Monster_Orc(gp);
        gp.monster[0].worldX = gp.tileSize*24;
        gp.monster[0].worldY = gp.tileSize*24;
    }
    public void setInteractiveTile(){
        int i =0;
        gp.iTile[i] = new IT_dryTree(gp,12,27);i++;
        gp.iTile[i] = new IT_dryTree(gp,12,28);i++;
        gp.iTile[i] = new IT_dryTree(gp,12,29);i++;
        gp.iTile[i] = new IT_dryTree(gp,12,30);i++;
        gp.iTile[i] = new IT_dryTree(gp,12,31);i++;
        gp.iTile[i] = new IT_dryTree(gp,12,32);i++;
        gp.iTile[i] = new IT_dryTree(gp,21,26);i++;
        gp.iTile[i] = new IT_dryTree(gp,22,26);i++;
        gp.iTile[i] = new IT_dryTree(gp,22,27);i++;
        gp.iTile[i] = new IT_dryTree(gp,22,28);i++;
        gp.iTile[i] = new IT_dryTree(gp,21,28);i++;
        gp.iTile[i] = new IT_dryTree(gp,21,29);i++;
        gp.iTile[i] = new IT_dryTree(gp,21,30);i++;
        gp.iTile[i] = new IT_dryTree(gp,21,31);i++;
        gp.iTile[i] = new IT_dryTree(gp,21,32);i++;
        gp.iTile[i] = new IT_dryTree(gp,22,31);i++;
        gp.iTile[i] = new IT_dryTree(gp,22,32);i++;
    }
}

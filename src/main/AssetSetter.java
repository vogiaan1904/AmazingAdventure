package main;

import entity.NPC_OldMan;
import main.object.*;
import monster.Monster_Orc;
import tile_interactive.IT_dryTree;
import tile_interactive.InteractiveTile;

import java.util.Random;

public class AssetSetter {
    GamePanel gp;
    public boolean chest1Opened = false;
    public boolean chest2Opened = false;
    public int chest1 = 0;
    public int chest2 = 1;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        int i = 0;
        gp.obj[i] = new Object_Chest(gp);
        gp.obj[i].worldX = gp.tileSize*35;
        gp.obj[i].worldY = gp.tileSize*8;
        i++;

        gp.obj[i] = new Object_Chest(gp);
        gp.obj[i].worldX = gp.tileSize*30;
        gp.obj[i].worldY = gp.tileSize*37;
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

        if(chest2Opened){
            gp.obj[i] = new Object_Potion_Red(gp);
            gp.obj[i].worldX = gp.tileSize*31;
            gp.obj[i].worldY = gp.tileSize*37;
            i++;
        }

        if(chest1Opened){
            gp.obj[i] = new Object_Potion_Red(gp);
            gp.obj[i].worldX = gp.tileSize*36;
            gp.obj[i].worldY = gp.tileSize*8;
            i++;
        }

    }

    public void setNPC(){
        int i =0;
        gp.npc[i] = new NPC_OldMan(gp);
        gp.npc[i].worldX = gp.tileSize*18;
        gp.npc[i].worldY = gp.tileSize*21;
        i++;
    }
    public void setMonster(){
        int i =0;


    }
    public void setInteractiveTile(){
        int i =0;

        gp.iTile[i] = new IT_dryTree(gp,22,34);i++;
        gp.iTile[i] = new IT_dryTree(gp,20,34);i++;
        gp.iTile[i] = new IT_dryTree(gp,21,34);i++;
        gp.iTile[i] = new IT_dryTree(gp,22,26);i++;
        gp.iTile[i] = new IT_dryTree(gp,20,26);i++;
        gp.iTile[i] = new IT_dryTree(gp,21,26);i++;

        gp.iTile[i] = new IT_dryTree(gp,37,28);i++;
        gp.iTile[i] = new IT_dryTree(gp,37,29);i++;
        gp.iTile[i] = new IT_dryTree(gp,37,32);i++;

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

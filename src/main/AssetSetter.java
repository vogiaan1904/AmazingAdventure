package main;

import entity.NPC_OldMan;
import main.object.*;
import monster.Monster_Orc;
import tile_interactive.IT_dryTree;
import tile_interactive.InteractiveTile;

import java.util.Random;

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
        gp.obj[i].worldX = gp.tileSize*10;
        gp.obj[i].worldY = gp.tileSize*12;
        i++;

        gp.obj[i] = new Object_Chest(gp);
        gp.obj[i].worldX = gp.tileSize*30;
        gp.obj[i].worldY = gp.tileSize*37;
        i++;




    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

    }
    public void setMonster(){
        int index =0;

        gp.monster[index] = new Monster_Orc(gp);
        gp.monster[index].worldX = gp.tileSize*24;
        gp.monster[index].worldY = gp.tileSize*24;
        index++;
        gp.monster[index] = new Monster_Orc(gp);
        gp.monster[index].worldX = gp.tileSize*36;
        gp.monster[index].worldY = gp.tileSize*20;
        index++;
        gp.monster[index] = new Monster_Orc(gp);
        gp.monster[index].worldX = gp.tileSize*10;
        gp.monster[index].worldY = gp.tileSize*30;
        index++;
        gp.monster[index] = new Monster_Orc(gp);
        gp.monster[index].worldX = gp.tileSize*36;
        gp.monster[index].worldY = gp.tileSize*10;
        index++;
        gp.monster[index] = new Monster_Orc(gp);
        gp.monster[index].worldX = gp.tileSize*37;
        gp.monster[index].worldY = gp.tileSize*10;
        index++;
        gp.monster[index] = new Monster_Orc(gp);
        gp.monster[index].worldX = gp.tileSize*38;
        gp.monster[index].worldY = gp.tileSize*10;
        index++;
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
        gp.iTile[i] = new IT_dryTree(gp,37,31);i++;
        gp.iTile[i] = new IT_dryTree(gp,37,32);i++;
    }
}

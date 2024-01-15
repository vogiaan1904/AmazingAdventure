package main;

import entity.NPC_OldMan;
import main.object.*;
import monster.Monster_Orc;
import monster.Monster_Skeleton;
import monster.Monster_Slime;
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
        gp.obj[i].worldY = gp.tileSize*20;
        i++;
        gp.obj[i] = new Object_Key(gp);
        gp.obj[i].worldX = gp.tileSize*33;
        gp.obj[i].worldY = gp.tileSize*12;
        i++;
        gp.obj[i] = new Object_Axe(gp);
        gp.obj[i].worldX = gp.tileSize*23;
        gp.obj[i].worldY = gp.tileSize*38;
        i++;

        gp.obj[i] = new Object_Door(gp);
        gp.obj[i].worldX = gp.tileSize*38;
        gp.obj[i].worldY = gp.tileSize*12;
        i++;

        gp.obj[i] = new Object_Door_Iron(gp);
        gp.obj[i].worldX = gp.tileSize*10;
        gp.obj[i].worldY = gp.tileSize*12;
        i++;

        gp.obj[i] = new Object_Metal_Plate(gp);
        gp.obj[i].worldX = gp.tileSize*14;
        gp.obj[i].worldY = gp.tileSize*27;
        i++;
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

        gp.monster[i] = new Monster_Orc(gp);
        gp.monster[i].worldX = gp.tileSize*38;
        gp.monster[i].worldY = gp.tileSize*10;
        i++;
        gp.monster[i] = new Monster_Skeleton(gp);
        gp.monster[i].worldX = gp.tileSize*10;
        gp.monster[i].worldY = gp.tileSize*32;
        i++;

        gp.monster[i] = new Monster_Slime(gp);
        gp.monster[i].worldX = gp.tileSize*19;
        gp.monster[i].worldY = gp.tileSize*38;
        i++;

        gp.monster[i] = new Monster_Slime(gp);
        gp.monster[i].worldX = gp.tileSize*23;
        gp.monster[i].worldY = gp.tileSize*40;
        i++;

        gp.monster[i] = new Monster_Slime(gp);
        gp.monster[i].worldX = gp.tileSize*26;
        gp.monster[i].worldY = gp.tileSize*35;
        i++;
        gp.currentMonsterIndex = i;

    }
    public void clearMonster(){
        for(int i = gp.currentMonsterIndex;i<20;i++){
            if(gp.monster[i]!=null){
                gp.monster[i] = null;
            }
        }
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

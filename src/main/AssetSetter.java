package main;

import entity.NPC_OldMan;
import main.object.*;
import monster.Monster_Orc;

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

        gp.npc[1] = new NPC_OldMan(gp);
        gp.npc[1].worldX = gp.tileSize*22;
        gp.npc[1].worldY = gp.tileSize*21;

        gp.npc[2] = new NPC_OldMan(gp);
        gp.npc[2].worldX = gp.tileSize*20;
        gp.npc[2].worldY = gp.tileSize*21;
    }
    public void setMonster(){
        gp.monster[0] = new Monster_Orc(gp);
        gp.monster[0].worldX = gp.tileSize*24;
        gp.monster[0].worldY = gp.tileSize*24;
    }
}

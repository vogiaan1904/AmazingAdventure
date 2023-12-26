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
        gp.obj[0] = new Object_Door(gp);
        gp.obj[0].worldX = gp.tileSize*21;
        gp.obj[0].worldY = gp.tileSize*22;

        gp.obj[1] = new Object_Key(gp);
        gp.obj[1].worldX = gp.tileSize*21;
        gp.obj[1].worldY = gp.tileSize*18;

        gp.obj[2] = new Object_Axe(gp);
        gp.obj[2].worldX = gp.tileSize*23;
        gp.obj[2].worldY = gp.tileSize*23;
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

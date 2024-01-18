package main;

import entity.Entity;
import main.object.Object_FinalKey;
import main.object.Object_Potion_Red;
import monster.Monster_Orc;

public class EntitySpawner {
    GamePanel gp;
    EntitySpawner(GamePanel gp){
        this.gp = gp;
    }
    public int chest1 = 0;
    public int chest2 = 1;
    public void spawnObject(int i){
        if(i==chest1){
            int lastIndex = gp.obj.length - 1;
            gp.obj[lastIndex] = new Object_FinalKey(gp);
            gp.obj[lastIndex].worldX = gp.tileSize*36;
            gp.obj[lastIndex].worldY = gp.tileSize*8;
        }
        if(i==chest2){
            int lastIndex = gp.obj.length - 1;
            gp.obj[lastIndex] = new Object_Potion_Red(gp);
            gp.obj[lastIndex].worldX = gp.tileSize*31;
            gp.obj[lastIndex].worldY = gp.tileSize*37;
        }
        for (int j = 0; j < gp.player.inventory.size(); j++) {
            if (gp.player.inventory.get(j).name == "Key") {
                gp.player.inventory.remove(j);
            }
        }
        gp.player.numKey--;
    }
    public void spawnMonster(int maxIndex){
        gp.monster[maxIndex] = new Monster_Orc(gp);
        gp.monster[maxIndex].worldX = gp.tileSize*35;
        gp.monster[maxIndex].worldY = gp.tileSize*40;
        maxIndex++;

        gp.monster[maxIndex] = new Monster_Orc(gp);
        gp.monster[maxIndex].worldX = gp.tileSize*34;
        gp.monster[maxIndex].worldY = gp.tileSize*42;
        maxIndex++;

        gp.monster[maxIndex] = new Monster_Orc(gp);
        gp.monster[maxIndex].worldX = gp.tileSize*31;
        gp.monster[maxIndex].worldY = gp.tileSize*42;
        maxIndex++;

        gp.monster[maxIndex] = new Monster_Orc(gp);
        gp.monster[maxIndex].worldX = gp.tileSize*36;
        gp.monster[maxIndex].worldY = gp.tileSize*37;
        maxIndex++;

        gp.currentMonsterIndex = maxIndex;
    }

}

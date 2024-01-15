package main;

import entity.Entity;
import main.object.Object_FinalKey;
import main.object.Object_Potion_Red;

import java.sql.SQLOutput;

public class CollisionChecker {
    GamePanel qp;

    public CollisionChecker(GamePanel qp) {
        this.qp = qp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = (int) entityLeftWorldX / qp.tileSize;
        int entityRightCol = (int) entityRightWorldX / qp.tileSize;
        int entityTopRow = (int) entityTopWorldY / qp.tileSize;
        int entityBottomRow = (int) entityBottomWorldY / qp.tileSize;

        int tileNum1, tileNum2;
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / qp.tileSize;// convert the location to Row and Col
                tileNum1 = qp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = qp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (qp.tileM.tile[tileNum1].collision || qp.tileM.tile[tileNum2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / qp.tileSize;
                tileNum1 = qp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = qp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (qp.tileM.tile[tileNum1].collision || qp.tileM.tile[tileNum2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / qp.tileSize;
                tileNum1 = qp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                tileNum2 = qp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (qp.tileM.tile[tileNum1].collision || qp.tileM.tile[tileNum2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / qp.tileSize;
                tileNum1 = qp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = qp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                if (qp.tileM.tile[tileNum1].collision || qp.tileM.tile[tileNum2].collision) {
                    entity.collisionON = true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (int i = 0; i < qp.obj.length; i++) {
            if (qp.obj[i] != null) {
                //get the entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x; //updating the solid area x and y
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //get the object's solid area position
                qp.obj[i].solidArea.x = qp.obj[i].worldX + qp.obj[i].solidArea.x;
                qp.obj[i].solidArea.y = qp.obj[i].worldY + qp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(qp.obj[i].solidArea)) {
                    if (qp.obj[i].collision) {
                        entity.collisionON = true;
                    }
                    if (player) {
                        if (qp.obj[i].name == "Chest") {
                            if (qp.player.numKey > 0) {
                                qp.obj[i].down1 = qp.obj[i].down2;

                                if(i==0){
                                    int lastIndex = qp.obj.length - 1;
                                    qp.obj[lastIndex] = new Object_FinalKey(qp);
                                    qp.obj[lastIndex].worldX = qp.tileSize*36;
                                    qp.obj[lastIndex].worldY = qp.tileSize*8;
                                }

                                if(i==1){
                                    int lastIndex = qp.obj.length - 1;
                                    qp.obj[lastIndex] = new Object_Potion_Red(qp);
                                    qp.obj[lastIndex].worldX = qp.tileSize*31;
                                    qp.obj[lastIndex].worldY = qp.tileSize*37;
                                }

                                for (int j = 0; j < qp.player.inventory.size(); j++) {
                                    if (qp.player.inventory.get(j).name == "Key") {
                                        qp.player.inventory.remove(j);
                                    }
                                }
                                qp.player.numKey--;
                            }
                        } else if (qp.obj[i].name == "Door") {
                            qp.obj[i].down1 = qp.obj[i].down2;
                            qp.obj[i].collision = false;

                        } else if (qp.obj[i].name == "Door_Iron") {
                            if(qp.player.numFinalKey>0){
                                qp.obj[i].down1 = qp.obj[i].down2;
                                qp.obj[i].collision = false;
                            }
                        } else if (qp.obj[i].name == "Metal_Plate") {
                            if(entity == qp.player){
                                if(entity.direction == "down"){
                                    qp.obj[i].collision = false;
                                }
                            }
                            if(entity.direction == "up"){
                                qp.obj[i].collision = true;
                            }
                        } else {
                            index = i;
                        }
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                qp.obj[i].solidArea.x = qp.obj[i].solidAreaDefaultX;
                qp.obj[i].solidArea.y = qp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }
    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;
        for(int i = 0;i< target.length;i++){
            if(target[i] != null){
                //get the entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x; //updating the solid area x and y
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //get the target's solid area position
                target[i].solidArea.x = target[i].worldX +target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY +target[i].solidArea.y;

                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;break;
                    case "down":
                        entity.solidArea.y += entity.speed;break;
                    case  "left":
                        entity.solidArea.x -= entity.speed;break;
                    case "right":
                        entity.solidArea.x += entity.speed;break;
                }
                if(entity.solidArea.intersects(target[i].solidArea)) {
                    if(target[i]!=entity) {
                        entity.collisionON = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX; //set the solidArea to the default
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return  index;
    }
    public boolean checkPlayer(Entity entity, boolean player) {
            boolean contactPlayer = false;

            //get the entity's solid area position
            entity.solidArea.x = entity.worldX + entity.solidArea.x; //updating the solid area x and y
            entity.solidArea.y = entity.worldY + entity.solidArea.y;

            //get the object's solid area position
            qp.player.solidArea.x = qp.player.worldX + qp.player.solidArea.x;
            qp.player.solidArea.y = qp.player.worldY + qp.player.solidArea.y;
            switch (entity.direction) {
                case "up":
                    entity.solidArea.y -= entity.speed;break;
                case "down":
                    entity.solidArea.y += entity.speed;break;
                case "left":
                    entity.solidArea.x -= entity.speed;break;
                case "right":
                    entity.solidArea.x += entity.speed;break;
            }
        if (entity.solidArea.intersects(qp.player.solidArea)) {
            entity.collisionON = true;
            contactPlayer = true;
        }
        qp.player.solidArea.x = qp.player.solidAreaDefaultX;
        qp.player.solidArea.y = qp.player.solidAreaDefaultY;
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        return contactPlayer;
    }
}

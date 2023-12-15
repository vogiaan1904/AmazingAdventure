package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel qp;
    public CollisionChecker(GamePanel qp){
        this.qp = qp;
    }
    public void checkTile(Entity entity){
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/qp.tileSize;
        int entityRightCol = entityRightWorldX/qp.tileSize;
        int entityTopRow = entityTopWorldY/qp.tileSize;
        int entityBottomRow = entityBottomWorldY/qp.tileSize;

        int tileNum1, tileNum2;
        switch (entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/qp.tileSize;// convert the location to Row and Col
                tileNum1 = qp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = qp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(qp.tileM.tile[tileNum1].collision || qp.tileM.tile[tileNum2].collision){
                    entity.collisionON = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/qp.tileSize;
                tileNum1 = qp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = qp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(qp.tileM.tile[tileNum1].collision || qp.tileM.tile[tileNum2].collision){
                    entity.collisionON = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/qp.tileSize;
                tileNum1 = qp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                tileNum2 = qp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(qp.tileM.tile[tileNum1].collision || qp.tileM.tile[tileNum2].collision){
                    entity.collisionON = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/qp.tileSize;
                tileNum1 = qp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = qp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                if(qp.tileM.tile[tileNum1].collision || qp.tileM.tile[tileNum2].collision){
                    entity.collisionON = true;
                }
                break;
        }
    }
    public int checkObject(Entity entity, boolean player){
        int index = 999;
        for(int i = 0;i< qp.obj.length;i++){
            if(qp.obj[i] != null){
                //get the entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x; //updating the solid area x and y
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //get the object's solid area position
                qp.obj[i].solidArea.x = qp.obj[i].worldX +qp.obj[i].solidArea.x;
                qp.obj[i].solidArea.y = qp.obj[i].worldY +qp.obj[i].solidArea.y;

                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if(entity.solidArea.intersects(qp.obj[i].solidArea)) {
                            if(qp.obj[i].collision){
                                entity.collisionON = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if(entity.solidArea.intersects(qp.obj[i].solidArea)){
                            if(qp.obj[i].collision){
                                entity.collisionON = true;
                            }if(player){
                                index = i;
                            }
                        }break;
                    case  "left":
                        entity.solidArea.x -= entity.speed;
                        if(entity.solidArea.intersects(qp.obj[i].solidArea)){
                            if(qp.obj[i].collision){
                                entity.collisionON = true;
                            }if(player){
                                index = i;
                            }
                        }break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if(entity.solidArea.intersects(qp.obj[i].solidArea)){
                            if(qp.obj[i].collision){
                                entity.collisionON = true;
                            }if(player){
                                index = i;
                            }
                        }break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                qp.obj[i].solidArea.x = qp.obj[i].solidAreaDefaultX;
                qp.obj[i].solidArea.y = qp.obj[i].solidAreaDefaultY;
            }
        }
        return  index;
    }
}

package entity;

import main.GamePanel;

public class Projectile extends Entity{
    GamePanel gp;
    Entity user;

    public Projectile(GamePanel gp, Entity user) {
        super(gp);
        this.gp = gp;
        this.user = user;
    }
    public void set(int worldX, int worldY, String direction, boolean alive){
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.life = this.maxLife;
    }
    public void update(){
        //detect monster and damage monster
        if(user == gp.player){
            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            if(monsterIndex != 999){
                gp.player.damageMonster(monsterIndex,attack);
                generateParticle(user.projectile,gp.monster[monsterIndex]);
                alive = false;
            }
        }
        if(user != gp.player){
            boolean contactPlayer = gp.cChecker.checkPlayer(this,false);
            if(!gp.player.invincible && contactPlayer){
                damagePlayer(attack);
                generateParticle(user.projectile,gp.player);
                alive = false;
            }
        }
        //animation set up
        switch (direction){
            case "up" : worldY -=speed;break;
            case "down" : worldY +=speed;break;
            case "left" : worldX -=speed;break;
            case "right" : worldX +=speed;break;
        }
        life--;
        if(life<0){
            alive = false;
        }
    }
}

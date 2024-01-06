package entity;

import main.GamePanel;

public class Projectile extends Entity{
    GamePanel gp;
    public Projectile(GamePanel gp) {
        super(gp);
        this.gp = gp;
    }
    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){


    }
    public void update(){

    }
}

package main.object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class Object_Rock extends Projectile {
    GamePanel gp;
    public Object_Rock(GamePanel gp, Entity user) {
        super(gp,user);
        this.gp = gp;
        name = "Rock";
        speed = 6;
        maxLife = 80; // 80frames
        life = maxLife;
        attack = 2;
        useCost = 0;
        alive = false;
        getImage();
    }
    public void getImage(){
        up1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/rock_down_1",gp.tileSize,gp.tileSize);
    }
}

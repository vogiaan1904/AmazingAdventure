package main.object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class Object_FireBall extends Projectile {
    GamePanel gp;
    public Object_FireBall(GamePanel gp) {
        super(gp);
        this.gp = gp;
        name = "FireBall";
        speed = 5;
        maxLife = 80; // 80frames
        life = maxLife;
        attack = 5;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage(){
        up1 = setup("/projectile/fireball_up1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/fireball_up2",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/fireball_down1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/fireball_down2",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/fireball_left1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/fireball_left2",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/fireball_right1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/fireball_right2",gp.tileSize,gp.tileSize);
    }
}

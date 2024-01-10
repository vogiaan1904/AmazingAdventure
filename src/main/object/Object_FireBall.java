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
        speed = 8;
        maxLife = 80; // 80frames
        life = maxLife;
        attack = 5;
        useCost = 1;
        alive = false;
        getImage();
    }
    public void getImage(){
        up1 = setup("/projectile/fireball_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/fireball_up_2",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/fireball_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/fireball_down_2",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/fireball_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/fireball_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/fireball_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/fireball_right_2",gp.tileSize,gp.tileSize);
    }
}

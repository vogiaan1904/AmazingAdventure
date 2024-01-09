package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class  KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    public boolean checkDrawTime = false;
    GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //title screen
        
        if (gp.gameState == gp.titleState) {
            if(code== KeyEvent.VK_W){
                gp.ui.commandNum --;
                if(gp.ui.commandNum <0){
                    gp.ui.commandNum=0;
                }

            }
            if(code== KeyEvent.VK_S){
                gp.ui.commandNum ++;
                if(gp.ui.commandNum >1){
                    gp.ui.commandNum=1;
                }
            }
            if(code==KeyEvent.VK_ENTER){
                if(gp.ui.commandNum==0){
                    gp.gameState = gp.playState;
                }
                if(gp.ui.commandNum==1){
                    System.exit(0);
                }
            }
        }

        else if(gp.gameState == gp.playState ){
            if(code== KeyEvent.VK_W){
                upPressed = true;
            }
            if(code== KeyEvent.VK_S){
                downPressed = true;
            }
            if(code== KeyEvent.VK_A){
                leftPressed = true;
            }
            if(code== KeyEvent.VK_D){
                rightPressed = true;
            }


            if(code== KeyEvent.VK_P){
                gp.gameState = gp.pauseState;
            }
            if(code==KeyEvent.VK_ENTER){
                enterPressed = true;
            }
            if(code==KeyEvent.VK_F){
                shotKeyPressed = true;
            }
            if(code== KeyEvent.VK_T){
                checkDrawTime = !checkDrawTime;
            }
            if(code==KeyEvent.VK_I){
                gp.gameState = gp.characterState;
            }
        }
        else if(gp.gameState == gp.pauseState){
            if(code== KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }
        }
        else if(gp.gameState == gp.dialogueState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState;
            }
        }
        else if (gp.gameState == gp.characterState) {
            if(code == KeyEvent.VK_I){
                gp.gameState = gp.playState;
            }
            if(code == KeyEvent.VK_W){
                if(gp.ui.slotRow != 0)
                    gp.ui.slotRow--;
            }
            if(code == KeyEvent.VK_A){
                if(gp.ui.slotCol != 0)
                    gp.ui.slotCol--;
            }
            if(code == KeyEvent.VK_S){
                if(gp.ui.slotRow != 3)
                    gp.ui.slotRow++;
            }
            if(code == KeyEvent.VK_D){
                if(gp.ui.slotCol != 4)
                    gp.ui.slotCol++;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code== KeyEvent.VK_W){
            upPressed = false;
        }
        if(code== KeyEvent.VK_S){
            downPressed = false;
        }
        if(code== KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code== KeyEvent.VK_D){
            rightPressed = false;// on-off switch check draw time
        }
        if(code==KeyEvent.VK_F){
            shotKeyPressed = false;
        }
    }
}

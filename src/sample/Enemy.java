package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Enemy {

    protected int hp;
    protected int maxHp;
    protected int xp;
    protected String symbol;
    protected String name;
    protected long actRate = 1000; //1000 ms = 1 second
    protected long lastAct; //time enemy last acted
    protected Color color;
    protected int row;
    protected int col;
    protected Image forwardImage;
    protected Image fightImage;

    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        //gc.fillText(symbol,
        //        Main.OFFSET+col*20,
        //        Main.OFFSET+row*20);
        gc.drawImage(forwardImage,Main.OFFSET+col*20,
                Main.OFFSET+row*20-20);
    }

    public void drawFightImage(GraphicsContext gc) {
        gc.drawImage(fightImage,500,200);
    }

    public abstract void act(Player player, World world);
    public abstract void attack(Player player);
}

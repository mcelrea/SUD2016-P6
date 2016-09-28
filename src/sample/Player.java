package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by mcelrea on 9/2/2016.
 */
public class Player {
    private String name;
    private int row; //room row
    private int col; //room col
    private int worldRow; //where he is in the world - row
    private int worldCol; //where he is in the world - column
    private int hp = 6;
    private int xp = 0;
    private int vitality = 1; //more vitality = more health
    private int strength = 1; //more strength = more damage
    private int luck = 1; //more luck = more crits
    private int intelligence = 1; //more int = better magick
    private int wisdom = 1; //more wisdom = more magick
    private int magick = 3; //magick points
    private int damageRating = 1; //how hard you hit
    private int level = 1;
    private int xpLevels[] = {0,0,10,20,35,60};

    public Player(String n) {
        name = n;
        row = 10;
        col = 10;
        worldRow = 10;
        worldCol = 10;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillText("@", Main.OFFSET+col*20, Main.OFFSET+row*20);


        gc.setFill(Color.BLACK);
        gc.fillText(name + " - " + level, 510, 35);
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(500,50,290,300,50,50);
        gc.setFill(Color.BLACK);
        gc.fillText("       Health: " + hp, 510, 80);
        gc.setFill(Color.BLACK);
        gc.fillText("       Magick: " + magick, 510, 110);
        gc.setFill(Color.BLACK);
        gc.fillText("   Experience: " + xp, 510, 140);
        gc.setFill(Color.BLACK);
        gc.fillText("     Vitality: " + vitality, 510, 170);
        gc.setFill(Color.BLACK);
        gc.fillText("     Strength: " + strength, 510, 200);
        gc.setFill(Color.BLACK);
        gc.fillText("         Luck: " + luck, 510, 230);
        gc.setFill(Color.BLACK);
        gc.fillText(" Intelligence: " + intelligence, 510, 260);
        gc.setFill(Color.BLACK);
        gc.fillText("       Wisdom: " + wisdom, 510, 290);
        gc.setFill(Color.BLACK);
        gc.fillText("Damage Rating: " + damageRating, 510, 320);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void moveUp(Room room) {
        //can't step out of world
        if(row == 0) {
            //if player is leaving the current room
            if(room.getCell(row,col) == 100) {
                worldRow--; //change rooms
                row = 18; //place him at bottom
            }
            return;//exit, do nothing
        }
        //if there is wall above me
        else if(room.getCell(row-1,col) == 1)  {
            return;//exit, do nothing
        }
        //else allow the player to move
        else {
            row--;
        }
    }

    public void moveDown(Room room) {
        //can't step out of the world
        if(row == 19) {
            if(room.getCell(row,col) == 100) {
                worldRow++;
                row = 1;
            }
            return;//exit, don't move
        }
        //don't let player move on top of wall
        if(room.getCell(row+1,col) == 1) {
            return;//exit, don't move
        }
        //else player can move
        else {
            row++;
        }
    }

    public void moveLeft(Room room) {
        //don't let them leave the room
        if(col == 0) {
            if(room.getCell(row,col) == 100) {
                worldCol--;
                col = 18;
            }
            return; //exit, don't move
        }
        //don't let move on top of a wall
        else if(room.getCell(row,col-1) == 1) {
            return; //exit, don't move
        }
        //else let them move
        else {
            col--;
        }
    }

    public void moveRight(Room room) {
        //don't let them leave the room
        if(col == 19) {
            if(room.getCell(row,col) == 100) {
                worldCol++;
                col = 1;
            }
            return; //exit, don't move
        }
        //don't let move on top of a wall
        else if(room.getCell(row,col+1) == 1) {
            return; //exit, don't move
        }
        //else let them move
        else {
            col++;
        }
    }

    public int getWorldRow() {
        return worldRow;
    }

    public void setWorldRow(int worldRow) {
        this.worldRow = worldRow;
    }

    public int getWorldCol() {
        return worldCol;
    }

    public void setWorldCol(int worldCol) {
        this.worldCol = worldCol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getVitality() {
        return vitality;
    }

    public void setVitality(int vitality) {
        this.vitality = vitality;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getMagick() {
        return magick;
    }

    public void setMagick(int magick) {
        this.magick = magick;
    }

    public int getDamageRating() {
        return damageRating;
    }

    public void setDamageRating(int damageRating) {
        this.damageRating = damageRating;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void updateStats() {
        hp = (vitality * 2) + level + 3;
        damageRating = strength * 2 + level;
        magick = wisdom * 2 + level;

        //check for level gain
        for(int i=0; i < xpLevels.length; i++) {
            if(xp < xpLevels[i]) {
                level = i;
                break;//exit
            }
        }
    }
}
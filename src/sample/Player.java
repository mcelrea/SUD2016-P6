package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
    private int maxHp = 6;
    private int xp = 0;
    private int strength; //more strength = more damage
    private int strengthModifier;
    private int dexterity;
    private int dexterityModifier;
    private int constitution;
    private int constitutionModifier;
    private int wisdom; //more wisdom = more magick
    private int wisdomModifier;
    private int armorClass;
    private int gold;
    private int level = 1;
    private int xpLevels[] = {0,0,10,20,35,60};
    private Ability[] activeAbilities = new Ability[6];
    private ArrayList<Ability> inactiveAbilities = new ArrayList<Ability>();
    private Image forwardImage;

    public Player(String n) {
        name = n;
        row = 10;
        col = 10;
        worldRow = 10;
        worldCol = 10;
        File file = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\images\\characterForward.png");
        try {
            forwardImage = new Image(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        //gc.fillText("@", Main.OFFSET+col*20, Main.OFFSET+row*20);
        gc.drawImage(forwardImage,Main.OFFSET+col*20, Main.OFFSET+row*20-20);

        gc.setFill(Color.BLACK);
        gc.fillText(name + " - " + level, 510, 35);
        gc.setStroke(Color.BLACK);
        gc.strokeRoundRect(500,50,290,300,50,50);
        gc.setFill(Color.BLACK);
        gc.fillText("       Health: " + hp, 510, 80);
        gc.setFill(Color.BLACK);
        gc.fillText("   Experience: " + xp, 510, 110);
        gc.setFill(Color.BLACK);
        gc.fillText("     Strength: " + strength, 510, 140);
        gc.setFill(Color.BLACK);
        gc.fillText("       Wisdom: " + wisdom, 510, 170);
        gc.setFill(Color.BLACK);
        gc.fillText("    Dexterity: " + dexterity, 510, 200);
        gc.setFill(Color.BLACK);
        gc.fillText(" Constitution: " + constitution, 510, 230);
        gc.setFill(Color.BLACK);
        gc.fillText("  Armor Class: " + armorClass, 510, 260);
        gc.setFill(Color.BLACK);
        gc.fillText("         Gold: " + gold, 510, 290);
    }

    public int statRoll() {
        int die1 = (int) (1 + Math.random() * 6);
        int die2 = (int) (1 + Math.random() * 6);
        int die3 = (int) (1 + Math.random() * 6);
        int die4 = (int) (1 + Math.random() * 6);

        //if die1 is the smallest
        if(die1 <= die2 && die1 <= die3 && die1 <= die4) {
            return die2+die3+die4;
        }
        //if die2 is the smallest
        else if(die2 <= die1 && die2 <= die3 && die2 <= die4) {
            return die1+die3+die4;
        }
        //if die3 is the smallest
        else if(die3 <= die1 && die3 <= die2 && die4 <= die4) {
            return die1+die2+die4;
        }
        //die 4 is the smallest
        else {
            return die1+die2+die3;
        }
    }

    public void drawAbilities(GraphicsContext gc) {
        gc.setFill(Color.BISQUE);
        for(int i=0; i < activeAbilities.length; i++) {
            if(activeAbilities[i] == null) {
                gc.fillText((i+1) + ". no ability", 100, 170+(i*30));
            }
            else {
                gc.fillText((i+1) + ". " + activeAbilities[i].getName(), 100, 170+(i*30));
            }
        }
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

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addAbility(Ability a) {
        inactiveAbilities.add(a);//add abilites to the inactiveAbilities every time

        //go through all the active abilities
        for(int i=0; i < activeAbilities.length; i++) {
            //if there is an empty space then activate this ability
            if(activeAbilities[i] == null) {
                activeAbilities[i] = a;
                return;//exit
            }
        }
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void useAbility(int num, Enemy enemy) {
        Ability a = activeAbilities[num-1];
        if(a != null) {
            int damage = (int) (a.getMinDamage() + Math.random() * (a.getMaxDamage() - a.getMinDamage() + 1));
            enemy.hp = enemy.hp - damage;
            Main.addCombatText("You use [" + a.getName() + "] to cause " + damage + " damage.");
            Main.turn = Main.ENEMYTURN;
        }
    }
}
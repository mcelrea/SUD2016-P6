package sample;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Skeleton extends Enemy{

    public Skeleton(int hpModifier, int strengthModifier,
                    int dexterityModifier, int wisdomModifier) {
        super(hpModifier,strengthModifier,dexterityModifier,wisdomModifier);
        lastAct = System.currentTimeMillis();
        File file = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\images\\skeleton.png");
        File file2 = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\images\\skeletonFullSize.png");
        try {
            forwardImage = new Image(new FileInputStream(file));
            fightImage = new Image(new FileInputStream(file2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        hpNumOfDice = 3;
        hpDiceSides = 6;
        hp = Dice.rollDice(hpNumOfDice,hpDiceSides) + hpModifier;
        maxHp = hp;
        strength = 3;
        dexterity = 8;
        wisdom = 3;
        addAbility(new Ability("Swipe",
                               "description",
                               0,
                               "1d4",
                               0,
                               0,
                               0,
                               Ability.DEXTERITY));
        addAbility(new Ability("Putrid Bile",
                               "description",
                                0,
                               "1d4",
                                0,
                                0,
                                0,
                                Ability.WISDOM));
    }

    @Override
    public void act(Player player, World world) {
        if(lastAct + actRate < System.currentTimeMillis()) {
            Room currentRoom = world.getRoom(player.getWorldRow(),player.getWorldCol());
            //generate a random number between 1-4
            while(true) {
                int random = (int) (1 + Math.random() * 4);
                if (random == 1 && currentRoom.getCell(row+1,col) == 0) {
                    row++;
                    break;
                } else if (random == 2 && currentRoom.getCell(row-1,col) == 0) {
                    row--;
                    break;
                } else if (random == 3 && currentRoom.getCell(row,col+1) == 0) {
                    col++;
                    break;
                } else if (random == 4 && currentRoom.getCell(row,col-1) == 0) {
                    col--;
                    break;
                }
            }
            lastAct = System.currentTimeMillis();
        }
    }

    @Override
    public void attack(Player player) {
        player.setHp(player.getHp()-1);
        Main.addCombatText("Skeleton uses [dry rot] for 1 damage");
        Main.turn = Main.PLAYERTURN;
    }
}

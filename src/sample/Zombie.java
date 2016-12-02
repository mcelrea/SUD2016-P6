package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Zombie extends Enemy{

    public Zombie(int hpModifier, int strengthModifier,
                    int dexterityModifier, int wisdomModifier,
                    int damageModifier) {
        super(hpModifier,strengthModifier,dexterityModifier,wisdomModifier);
        this.damageModifier = damageModifier;
        lastAct = System.currentTimeMillis();
        File file = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\images\\characterForward.png");
        File file2 = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\images\\skeletonFullSize.png");
        try {
            forwardImage = new Image(new FileInputStream(file));
            fightImage = new Image(new FileInputStream(file2));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        actRate = 500; //move every 1/2 second
        xp = 5;
        hpNumOfDice = 3;
        hpDiceSides = 8;
        hp = Dice.rollDice(hpNumOfDice,hpDiceSides) + hpModifier;
        maxHp = hp;
        strength = 8;
        dexterity = 5;
        wisdom = 1;
        addAbility(new Ability("Kick",
                "description",
                0,
                "2d3",
                0,
                0,
                0,
                Ability.STRENGTH));
        addAbility(new Ability("Head Butt",
                "description",
                0,
                "2d4",
                0,
                0,
                0,
                Ability.STRENGTH));
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
        int r = (int) (1 + Math.random() * 2);
        Ability a;
        if(r == 1) {
            a = abilities.get(0);
        }
        else {
            a = abilities.get(1);
        }

        int damage = Dice.rollDice(a.getNumOfDice(),a.getDiceSides());
        int criticalHitMiss = Dice.rollDie(20);
        //roll a 20, do critical damage
        if(criticalHitMiss == 20) {
            damage *= 2;
        }
        //roll a 1, critical miss
        if(criticalHitMiss == 1) {
            Main.addCombatText(name + " attempts to use [" + a.getName()+ "], but critically misses!!!!", Color.RED);
            Main.turn = Main.PLAYERTURN;
            return; //exit
        }
        damage += damageModifier;
        if(a.getDamageType() == Ability.STRENGTH) {
            damage += strength/2 + strengthModifier;
        }
        else if(a.getDamageType() == Ability.DEXTERITY) {
            damage += dexterity/2 + dexterityModifier;
        }
        else {
            damage += wisdom/2 + wisdomModifier;
        }

        if(damage > 0) {
            player.setHp(player.getHp() - damage);
            if(criticalHitMiss != 20) {
                Main.addCombatText("Zombie uses [" + a.getName() + "] for " + damage + " damage", Color.RED);
            }
            else {
                Main.addCombatText("Zombie uses [" + a.getName() + "] for " + damage + " critical damage", Color.RED);
            }
        }
        else {
            Main.addCombatText("Zombie attempts to use [" + a.getName() + "] but misses.", Color.RED);
        }
        Main.turn = Main.PLAYERTURN;
    }

    @Override
    public int getDroppedGold() {
        int chance = (int) (1 + Math.random() * 100);

        //20% chance to drop gold
        if(chance <= 20) {
            int gold = (int) (4 + Math.random() * 5); //4,5,6,7,8 gold
            Main.addCombatText("Zombie dropped " + gold + " gold", Color.GOLD);
            return gold;
        }
        else {
            return 0;
        }
    }
}

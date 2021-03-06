package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Store {
    private ArrayList<Ability> abilities = new ArrayList<Ability>();
    int row;
    int col;

    public void addAbility(Ability a) {
        abilities.add(a);
    }

    public void purchaseAbility(Player player, int num) {
        if(num > abilities.size()) {
            return; //they are trying to buy something that no exist
        }
        Ability a = abilities.get(num-1);
        if(player.getGold() >= a.getCost() &&
                player.getStrength() >= a.getStrengthPrereq() &&
                player.getDexterity() >= a.getDexterityPrereq() &&
                player.getWisdom() >= a.getWisdomPrereq()) {
            System.out.println("You bought an item.");
            player.addAbility(a);//give player the ability
            player.setGold(player.getGold() - a.getCost());//take money
            Main.addCombatText("You bought [" + a.getName() + "] for " + a.getCost() + " gold.", Color.GOLD);
        }
        else {
            Main.addCombatText("You do not meet the requirements for that item", Color.GRAY);
        }
    }

    public void draw(GraphicsContext gc, Player player) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,800,600);
        gc.setFill(Color.WHITE);
        gc.fillText("STORE", 400,20);

        //show players gold
        gc.setFill(Color.GOLD);
        gc.fillText("Gold: " + player.getGold(), 650, 20);

        gc.setFill(Color.WHITE);
        gc.fillText("Item                Cost  Strength  Dexterity  Wisdom", 5, 100);
        for(int i=0; i < abilities.size(); i++) {
            gc.setFill(Color.WHITE);
            gc.fillText((i+1) + "." + abilities.get(i).getName(), 5, 140+(i*25));
            gc.setFill(Color.GOLD);
            gc.fillText("" + abilities.get(i).getCost(), 300, 140+(i*25));

            if(player.getStrength() >= abilities.get(i).getStrengthPrereq()) {
                gc.setFill(Color.GREEN);
            }
            else {
                gc.setFill(Color.RED);
            }
            gc.fillText("" + abilities.get(i).getStrengthPrereq(), 425, 140+(i*25));

            if(player.getDexterity() >= abilities.get(i).getDexterityPrereq()) {
                gc.setFill(Color.GREEN);
            }
            else {
                gc.setFill(Color.RED);
            }
            gc.fillText("" + abilities.get(i).getDexterityPrereq(), 580, 140+(i*25));

            if(player.getWisdom() >= abilities.get(i).getWisdomPrereq()) {
                gc.setFill(Color.GREEN);
            }
            else {
                gc.setFill(Color.RED);
            }
            gc.fillText("" + abilities.get(i).getWisdomPrereq(), 715, 140+(i*25));
        }
    }
}


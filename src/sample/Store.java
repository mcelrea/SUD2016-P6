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
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,800,600);
        gc.setFill(Color.WHITE);
        gc.fillText("STORE", 400,20);
        for(int i=0; i < abilities.size(); i++) {
            gc.fillText((i+1) + ". " + abilities.get(i).getName() + " "
                    + abilities.get(i).getCost(), 50, 70+(i*25));
        }
    }
}


package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by mcelrea on 9/6/2016.
 */
public class Room {
    int myRoom[][];
    ArrayList<Item> items = new ArrayList<Item>();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    public Room(String path) {
        myRoom = new int[20][20];
        File file = new File(path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String nextLine;
            int count = 0;
            int row = 0;
            int col = 0;
            while((nextLine = reader.readLine()) != null) {
                if(count != 0) {
                    for (int i = 0; i < nextLine.length(); i++) {
                        if(nextLine.substring(i,i+1).equals("1")) {
                            myRoom[row][col] = 1;
                        }
                        else if(nextLine.substring(i,i+1).equals(" ")) {
                            myRoom[row][col] = 0;
                        }
                        else if(nextLine.substring(i,i+1).equals("E")) {
                            myRoom[row][col] = 100;
                        }
                        else if(nextLine.substring(i,i+1).equals("R")) {
                            Item item = new Item("Ring of Vitality",
                                    "A small glint of metal shows on the floor");
                            item.setRow(row);
                            item.setCol(col);
                            item.setVitality(2);
                            items.add(item);
                        }
                        else if(nextLine.substring(i,i+1).equals("S")) {
                            Skeleton skeleton = new Skeleton();
                            skeleton.hp = 3;
                            skeleton.color = Color.AZURE;
                            skeleton.name = "Skeleton";
                            skeleton.xp = 5;
                            skeleton.symbol = "S";
                            skeleton.row = row;
                            skeleton.col = col;
                            enemies.add(skeleton);
                        }
                        col++;
                    }
                    row++;
                    col=0;
                }
                count++;
            }
        } catch(Exception e) {
            System.out.println("COULD NOT LOAD FILE");
            e.printStackTrace();
        }
    }

    public void debugRoom() {
        for (int i=0; i < myRoom.length; i++) {
            for(int j=0; j < myRoom[i].length; j++) {
                if(myRoom[i][j] == 0)
                    System.out.print(" ");
                else
                    System.out.print(myRoom[i][j]);
            }
            System.out.println();
        }
    }

    public void draw(GraphicsContext gc, Player player) {

        //go through the entire room array (table)
        for(int row=0; row < myRoom.length; row++) {
            for(int col=0; col < myRoom[row].length; col++) {
                if(myRoom[row][col] == 1) {
                    gc.setFill(Color.BLACK);
                    gc.fillText("1",Main.OFFSET+col*20,Main.OFFSET+row*20);
                }
            }
        }

        //go through and draw all the items
        for(int i=0; i < items.size(); i++) {
            System.out.println("looping");
            if(items.get(i).getName().equals("Ring of Vitality")) {
                gc.setFill(Color.BLACK);
                gc.fillText("O",
                        Main.OFFSET+items.get(i).getCol()*20,
                        Main.OFFSET+items.get(i).getRow()*20);
                if(items.get(i).getRow() == player.getRow() &&
                        items.get(i).getCol() == player.getCol()) {
                    gc.setFill(Color.BLACK);
                    gc.fillText(items.get(i).getDescription(), 30, 475);
                }
            }
        }

        //draw all the enemies
        for(int i=0; i < enemies.size(); i++) {
            enemies.get(i).draw(gc);
        }
    }

    public void enemiesAct(Player player, World world) {
        for(int i=0; i < enemies.size(); i++) {
            enemies.get(i).act(player,world);
        }
    }

    public void pickUpItem(Player player) {
        //go through all the items
        for(int i=0; i < items.size(); i++) {
            if(items.get(i).getRow() == player.getRow() &&
                    items.get(i).getCol() == player.getCol()) {
                //erase the item from the room
                Item temp = items.get(i); //grab the item
                player.setVitality(player.getVitality()+temp.getVitality());
                player.setWisdom(player.getWisdom()+temp.getWisdom());
                player.setIntelligence(player.getIntelligence()+temp.getIntelligence());
                player.setLuck(player.getLuck()+temp.getLuck());
                player.setDamageRating(player.getDamageRating()+temp.getDamageRating());
                player.setHp(player.getHp()+temp.getHp());
                player.setMagick(player.getMagick()+temp.getMagick());
                player.setStrength(player.getStrength()+temp.getStrength());
                player.setXp(player.getXp()+temp.getXp());
                items.remove(i);
                i--;
                player.updateStats();
            }
        }
    }

    public int getCell(int row, int col) {
        return myRoom[row][col];
    }

}
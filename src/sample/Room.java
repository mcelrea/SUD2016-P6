package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by mcelrea on 9/6/2016.
 */
public class Room {
    int myRoom[][];
    ArrayList<Item> items = new ArrayList<Item>();
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    Image wallImage;
    Store store;

    public Room(String path) {
        File imageFile = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\images\\wall.png");
        myRoom = new int[20][20];
        File file = new File(path);
        try {
            wallImage = new Image(new FileInputStream(imageFile));
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
                                    "A small glint of metal shows on the floor",
                                    "C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\images\\ring.png");
                            item.setRow(row);
                            item.setCol(col);
                            item.setVitality(2);
                            item.setAbility(new Ability("Slash",
                                                        "description",
                                                        10,
                                                        "1d6",
                                                        0,
                                                        12,
                                                        0,
                                                        Ability.DEXTERITY));
                            items.add(item);
                        }
                        else if(nextLine.substring(i,i+1).equals("S")) {
                            Skeleton skeleton = new Skeleton(0,-4,-1,-1,0);
                            skeleton.row = row;
                            skeleton.col = col;
                            enemies.add(skeleton);
                        }
                        else if(nextLine.substring(i,i+1).equals("Z")) {
                            Zombie zombie = new Zombie(0,0,0,0,0);
                            zombie.row = row;
                            zombie.col = col;
                            enemies.add(zombie);
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
                    //gc.fillText("1",Main.OFFSET+col*20,Main.OFFSET+row*20);
                    gc.drawImage(wallImage,Main.OFFSET+col*20,Main.OFFSET+row*20-20);
                }
            }
        }

        //go through and draw all the items
        for(int i=0; i < items.size(); i++) {
            items.get(i).draw(gc);
                if(items.get(i).getRow() == player.getRow() &&
                        items.get(i).getCol() == player.getCol()) {
                    gc.setFill(Color.BLACK);
                    gc.fillText(items.get(i).getDescription(), 30, 475);
                }
        }

        //draw all the enemies
        for(int i=0; i < enemies.size(); i++) {
            enemies.get(i).draw(gc);
        }

        if(store != null) {
            gc.fillText("$",Main.OFFSET+store.col*20,Main.OFFSET+store.row*20);
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
                player.setWisdom(player.getWisdom()+temp.getWisdom());
                player.setHp(player.getHp()+temp.getHp());
                player.setStrength(player.getStrength()+temp.getStrength());
                player.setXp(player.getXp()+temp.getXp());
                //if this item grants the player an ability
                if(temp.getAbility() != null) {
                    player.addAbility(temp.getAbility());
                }
                items.remove(i);
                i--;
            }
        }
    }

    public int getCell(int row, int col) {
        return myRoom[row][col];
    }

    public Enemy getEnemy(Player player) {
        for(int i=0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if(player.getRow() == e.row &&
                    player.getCol() == e.col) {
                return e;//player is on Enemy e
            }
        }
        return null; //player is not on an Enemy
    }

    public Store getStore(Player p) {

        if(store != null && p.getRow() == store.row &&
                p.getCol() == store.col) {
            return store;
        }

        return null;//the player is not on a store
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

}
package sample;

import javafx.scene.canvas.GraphicsContext;

public class World {
    Room rooms[][] = new Room[15][15];

    public World() {
        rooms[9][10] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\Rooms\\room9-10");
        rooms[10][10] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\Rooms\\room10-10");
        rooms[9][11] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\Rooms\\room9-11");
        rooms[8][11] = new Room("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\Rooms\\room8-11");

        rooms[10][10].store = new Store();
        rooms[10][10].store.row = 7;
        rooms[10][10].store.col = 16;
        rooms[10][10].store.addAbility(new Ability("Dagger",
                "description",
                10,
                "1d6",
                0,
                12,
                0,
                Ability.DEXTERITY));
        rooms[10][10].store.addAbility(new Ability("Long Sword",
                "description",
                15,
                "1d8",
                12,
                0,
                0,
                Ability.STRENGTH));
        rooms[10][10].store.addAbility(new Ability("Fireball",
                "description",
                15,
                "1d8",
                0,
                0,
                12,
                Ability.WISDOM));
        rooms[10][10].store.addAbility(new Ability("Greatsword",
                "description",
                25,
                "2d5",
                19,
                0,
                0,
                Ability.STRENGTH));
        rooms[10][10].store.addAbility(new Ability("Scorch",
                "description",
                25,
                "2d5",
                0,
                0,
                19,
                Ability.WISDOM));
        rooms[10][10].store.addAbility(new Ability("Dual Sai",
                "description",
                25,
                "3d4",
                0,
                19,
                0,
                Ability.DEXTERITY));

        rooms[8][11].store = new Store();
        rooms[8][11].store.row = 7;
        rooms[8][11].store.col = 16;
        rooms[8][11].store.addAbility(new Ability("Edged Dagger",
                "description",
                24,
                "2d5",
                0,
                23,
                0,
                Ability.DEXTERITY));
        rooms[8][11].store.addAbility(new Ability("Battle Axe",
                "description",
                30,
                "2d6",
                25,
                0,
                0,
                Ability.STRENGTH));
        rooms[8][11].store.addAbility(new Ability("Ice Blast",
                "description",
                23,
                "2d5",
                0,
                0,
                23,
                Ability.WISDOM));
    }

    public void drawRoom(int row, int col, Player player, GraphicsContext gc) {
        rooms[row][col].draw(gc,player);
    }

    public Room getRoom(int row, int col) {
        return rooms[row][col];
    }
}

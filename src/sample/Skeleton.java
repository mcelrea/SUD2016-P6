package sample;

public class Skeleton extends Enemy{

    public Skeleton() {
        lastAct = System.currentTimeMillis();
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

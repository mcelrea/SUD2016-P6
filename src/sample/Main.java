package sample;

import com.sun.xml.internal.fastinfoset.algorithm.BuiltInEncodingAlgorithm;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class Main extends Application {

    Player player = new Player("Kelsier");
    public static final int CELLSIZE = 20;
    ArrayList<String> input = new ArrayList<String>();
    World world = new World();
    public static final int OFFSET = 40;
    Enemy currentEnemy = null;
    public static final int MAP=1, FIGHT=2, PLAYERTURN=3, ENEMYTURN=4, GAMEOVER=5;
    public int gameState = MAP;
    public static int turn = PLAYERTURN;
    public static String combatText1 = "COMBAT TEXT";
    public static String combatText2 = "COMBAT TEXT";
    public static String combatText3 = "COMBAT TEXT";
    public static String combatText4 = "COMBAT TEXT";
    public static String combatText5 = "COMBAT TEXT";
    Image gameOverImage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("SUD Alpha 0.0.0.4");
        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(800,600);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Font myFont = Font.font("Courier New", FontWeight.BOLD, 24);
        gc.setFont(myFont);

        File file = new File("C:\\Users\\mcelrea\\Documents\\Game Programming P6\\Graphical SUDD P6\\src\\images\\endScreen.png");
        gameOverImage = new Image(new FileInputStream(file));

        world.getRoom(10,10).debugRoom();

        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String code = event.getCode().toString();
                if(!input.contains(code)) {
                    input.add(code);//add it to the list
                }
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(gameState == MAP) {
                    //System.out.println(input);
                    processInput();
                    //backdrop
                    gc.setFill(Color.WHEAT);
                    gc.fillRect(0, 0, 800, 600);

                    //all current enemies act
                    Room currentRoom = world.getRoom(player.getWorldRow(), player.getWorldCol());
                    currentRoom.enemiesAct(player, world);
                    currentEnemy = currentRoom.getEnemy(player);
                    if(currentEnemy != null) {
                        gameState = FIGHT;
                    }

                    //map
                    world.drawRoom(player.getWorldRow(), player.getWorldCol(), player, gc);

                    //character
                    player.draw(gc);

                    //draw the combat text
                    gc.setFill(Color.BLACK);
                    gc.fillText(combatText1, 10,450);
                    gc.fillText(combatText2, 10,475);
                    gc.fillText(combatText3, 10,500);
                    gc.fillText(combatText4, 10,525);
                    gc.fillText(combatText5, 10,550);
                }
                else if(gameState == FIGHT) {

                    if(turn == PLAYERTURN) {
                        processFightInput();
                        if(currentEnemy.hp <= 0) {
                            Room currentRoom = world.getRoom(player.getWorldRow(), player.getWorldCol());
                            currentRoom.removeEnemy(currentEnemy);
                            gameState = MAP;
                        }
                    }
                    else {//else its the enemies turn
                        currentEnemy.attack(player);
                        if(player.getHp() <= 0) {
                            gameState = GAMEOVER;
                        }
                    }

                    //backdrop
                    gc.setFill(Color.ROYALBLUE);
                    gc.fillRect(0, 0, 800, 600);

                    drawFight(gc);
                }
                else if(gameState == GAMEOVER) {
                    gc.drawImage(gameOverImage,0,0);
                    processGameOverInput();
                }
            }
        }.start();

        //last line
        primaryStage.show();
    }

    private void processGameOverInput() {
        //go through the entire list of input
        for(int i=0; i < input.size(); i++) {
            //if the input is equal to SPACE
            if (input.get(i).equals("SPACE")) {
                gameState = MAP;
                player = new Player(player.getName()); //reset player
                world = new World(); //reset world
                //remove W from list
                input.remove(i);
                i--;
            }
        }
    }

    private void drawFight(GraphicsContext gc) {
        gc.setFill(Color.CORNSILK);
        gc.fillText("FIGHT", 340,50);

        gc.setFill(Color.AQUA);
        gc.fillText(player.getName(), 100, 100);

        gc.setFill(Color.LAVENDER);
        gc.fillText(currentEnemy.name, 500, 100);

        //draw player health bar
        gc.setFill(Color.YELLOW);
        gc.fillRect(100, 120, 100, 20);
        gc.setFill(Color.HOTPINK);
        gc.fillRect(100, 120, 100*(player.getHp()/(double)player.getMaxHp()),20);

        //draw enemy health bar
        gc.setFill(Color.YELLOW);
        gc.fillRect(500, 120, 100, 20);
        gc.setFill(Color.HOTPINK);
        gc.fillRect(500, 120, 100*(currentEnemy.hp/(double)currentEnemy.maxHp),20);

        player.drawAbilities(gc);

        //draw the combat text
        gc.setFill(Color.ORANGE);
        gc.fillText(combatText1, 10,400);
        gc.fillText(combatText2, 10,425);
        gc.fillText(combatText3, 10,450);
        gc.fillText(combatText4, 10,475);
        gc.fillText(combatText5, 10,500);

        currentEnemy.drawFightImage(gc);
    }

    public static void addCombatText(String text) {
        //scroll the combat text down
        combatText5 = combatText4;
        combatText4 = combatText3;
        combatText3 = combatText2;
        combatText2 = combatText1;

        //add new text
        combatText1 = text;
    }

    private void processFightInput() {
        //go through the entire list of input
        for(int i=0; i < input.size(); i++) {
            //if the input is equal to W
            if (input.get(i).equals("L")) {
                gameState = MAP;
                //remove W from list
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("O")) {
                player.setHp(player.getHp()-1);
                //remove W from list
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("P")) {
                currentEnemy.hp = currentEnemy.hp - 1;
                //remove W from list
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT1")) {
                player.useAbility(1,currentEnemy);
                //remove W from list
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT2")) {
                player.useAbility(2,currentEnemy);
                //remove W from list
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT3")) {
                player.useAbility(3,currentEnemy);
                //remove W from list
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT4")) {
                player.useAbility(4,currentEnemy);
                //remove W from list
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT5")) {
                player.useAbility(5,currentEnemy);
                //remove W from list
                input.remove(i);
                i--;
            }
            else if (input.get(i).equals("DIGIT6")) {
                player.useAbility(6,currentEnemy);
                //remove W from list
                input.remove(i);
                i--;
            }
        }
    }

    private void processInput() {
        //go through the entire list of input
        for(int i=0; i < input.size(); i++) {
            //if the input is equal to W
            if(input.get(i).equals("W")) {
                player.moveUp(world.getRoom(player.getWorldRow(),player.getWorldCol()));
                //remove W from list
                input.remove(i);
                i--;
            }
            //if the input is equal to S
            else if(input.get(i).equals("S")) {
                player.moveDown(world.getRoom(player.getWorldRow(),player.getWorldCol()));
                //remove S from list
                input.remove(i);
                i--;
            }
            //if the input is equal to A
            else if(input.get(i).equals("A")) {
                player.moveLeft(world.getRoom(player.getWorldRow(),player.getWorldCol()));
                //remove A from list
                input.remove(i);
                i--;
            }
            //if the input is equal to D
            else if(input.get(i).equals("D")) {
                player.moveRight(world.getRoom(player.getWorldRow(),player.getWorldCol()));
                //remove D from list
                input.remove(i);
                i--;
            }
            else if(input.get(i).equals("E")) {
                world.getRoom(player.getWorldRow(),player.getWorldCol()).pickUpItem(player);
                input.remove(i);
                i--;
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
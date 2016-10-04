package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    Player player = new Player("Kelsier");
    public static final int CELLSIZE = 20;
    ArrayList<String> input = new ArrayList<String>();
    World world = new World();
    public static final int OFFSET = 40;
    Enemy currentEnemy = null;
    public static final int MAP=1, FIGHT=2;
    public int gameState = MAP;

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
                }
                else if(gameState == FIGHT) {

                    processFightInput();

                    //backdrop
                    gc.setFill(Color.ROYALBLUE);
                    gc.fillRect(0, 0, 800, 600);

                    drawFight(gc);
                }
            }
        }.start();

        //last line
        primaryStage.show();
    }

    private void drawFight(GraphicsContext gc) {
        gc.setFill(Color.CORNSILK);
        gc.fillText("FIGHT", 340,50);

        gc.setFill(Color.AQUA);
        gc.fillText(player.getName(), 100, 100);
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
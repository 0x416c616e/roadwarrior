import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.ArrayList;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;
import java.util.logging.LogManager;
import javafx.scene.control.Label;

public class Main extends Application {

    private ArrayList<PipeButton> buttonsArr;
    private ArrayList<ArrayList<PipeButton>> buttons2DArr;
    private ImageView[] pipeImageViews;
    private StackPane sPane;

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    private boolean reset;

    private Scene scene;

    // global constants
    public static final int STAGE_WIDTH = 700;
    public static final int STAGE_HEIGHT = 800;
    public static final int SCENE_WIDTH = 400;
    public static final int SCENE_HEIGHT = 400;
    public static final int BUTTON_WIDTH = 70;
    public static final int BUTTON_HEIGHT = 70;
    public static final int NUM_BUTTONS = 100;
    public static final int BUTTON_COLS = NUM_BUTTONS/10;
    public static final int BUTTON_ROWS = NUM_BUTTONS/10;
    public static final int ROCK_SPAWN_PROB = 800;
    public static final double BUTTON_VGAP = 2.0;
    public static final double BUTTON_HGAP = 2.0;
    public static final int[] START_COORD = {0,4};
    public static final int[] END_COORD = {9,4};

    public void start(Stage stage) {

        GridPane gPane = new GridPane();
//        gPane.setHgap(BUTTON_HGAP);
//        gPane.setVgap(BUTTON_VGAP);
        BorderPane bPane = new BorderPane();
        Button winButton = new Button("Win");
        Button loseButton = new Button("Give Up");
        Button aboutButton = new Button("About");
        Button resetButton = new Button("Reset");
        HBox submitBox = new HBox();
        sPane = new StackPane();
        VBox aboutBox = new VBox();

        reset = false;


        // alignment
        Label topLabel = new Label("Connect the roads. Click to add something. You can't go through through houses, rocks, or mountains.");
        Label aboutLabel = new Label("This game was made by William and Alan at eHacks 2018. It was made with JavaFX and CSS.");
        bPane.setTop(topLabel);
        Button closeAbout = new Button("OK");
        bPane.setCenter(gPane);
        bPane.setBottom(submitBox);
        submitBox.setAlignment(Pos.CENTER);
        topLabel.setAlignment(Pos.CENTER);


        Button submitButton = new Button("Submit");
        submitBox.getChildren().addAll(submitButton, loseButton, aboutButton, resetButton); //took out winButton
        sPane.getChildren().addAll(bPane);
        aboutBox.getChildren().addAll(aboutLabel, closeAbout);

        // instantiate scene with bPane
        scene = new Scene(sPane, SCENE_WIDTH, SCENE_HEIGHT);

        // create all pipeButtons
        buttonsArr = new ArrayList<PipeButton>(); // this holds all the buttons
        for(int i = 0; i < NUM_BUTTONS; i++){
            PipeButton newButton = new PipeButton();
            newButton.setMinHeight(BUTTON_HEIGHT);
            newButton.setMinWidth(BUTTON_WIDTH);

            //NEXT LINE IS WHERE YOU SHOULD DO THE RANDOM CASE/SWITCH FOR SETTING GREENERY/difficulty
            Random randGenerator = new Random();
            int randInt = randGenerator.nextInt();
            if (randInt < 0) randInt *= -1;


            int randIntTen = (randInt % 10 )+ 1;

            for(int z = 0; z < ROCK_SPAWN_PROB; z++){
                if(randInt % 100 == 0) randIntTen = 8;
            }

            switch (randIntTen) {
                case 1:
                    newButton.setGraphic(new ImageView(new Image("tree.png")));
                    break;
                case 2:
                    newButton.setGraphic(new ImageView(new Image("bush.png")));
                    break;
                case 3:
                    newButton.setGraphic(new ImageView(new Image("tree2.png")));
                    break;
                case 4:
                case 5:
                case 6:
                    //leaves the default amount, makes more tiles look regular by having 2 here
                    break;
                case 7:
                    newButton.setGraphic(new ImageView(new Image("grass.png")));
                    break;
                case 8:
                    newButton.setGraphic(new ImageView(new Image("rocks.png")));
                    newButton.setObstacle(true);

                    break;
                case 9:
                    Random randInt2 = new Random();
                    int whatever = randInt2.nextInt();
                    if (whatever % 10 > 4) {
                        newButton.setGraphic(new ImageView(new Image("house.png")));
                        newButton.setObstacle(true);
                    }


                    break;

                default:
                    Random randInt3 = new Random();
                    int whatever2 = randInt3.nextInt();
                    if (whatever2 % 10 > 7) {
                        newButton.setGraphic(new ImageView(new Image("mountains.png")));
                        newButton.setObstacle(true);
                    }

                    break;
            }

            //newButton.setGraphic(pipeImageViews[0]);
            newButton.setStyle("-fx-background-radius: 0px;");

            if(!newButton.isObstacle() && !(newButton.getX() == 4 && newButton.getY() == 0)
                                        && !(newButton.getX() == 4 && newButton.getY() == 9)){
                newButton.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        newButton.togglePipeDirection();
                        //newButton.setGraphic(pipeImageViews[newButton.getPipeNumber()]);
                        // re draw

                    }
                });
            }


            buttonsArr.add(newButton);
        }

        // account

        // add all buttons to gPane
        int counter = 0; // used to track which button we are getting from the ArrayList
        for(int x = 0; x < BUTTON_COLS; x++) {
            for (int y = 0; y < BUTTON_ROWS; y++) {
                buttonsArr.get(counter).setXY(x,y);
                gPane.add(buttonsArr.get(counter), x, y); // gpane.add(object, column, row)
                counter++;
                assert counter < buttonsArr.size(); // prevents array out of bounds exception
            }
        }

        submitButton.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println("Is connected: " + isConnected() + "\n");
                if(isConnected())
                    win();
                else
                    lose();
            }
        });
        winButton.setOnAction(e -> {
            win();

        });


        loseButton.setOnAction(e -> {
            lose();
        });

        aboutButton.setOnAction(e -> {
            // Button was clicked, do something...
            //put info about the people who made it here
            sPane.getChildren().addAll(aboutBox);

        });

        closeAbout.setOnAction(e -> {
           sPane.getChildren().remove(aboutBox);
        });



        fill2DArr();
        buttons2DArr.get(4).get(0).togglePipeDirection();
        buttons2DArr.get(4).get(0).setObstacle(false);
        buttons2DArr.get(4).get(9).togglePipeDirection();
        buttons2DArr.get(4).get(9).setObstacle(false);

        LogManager.getLogManager().reset();


        stage.setHeight(765);
        stage.setWidth(700);
        stage.setTitle("Road Warrior");
        stage.setResizable(false);
        scene.getStylesheets().add("stylesheet.css");
        stage.setScene(scene);
        stage.show();
    } // end of main (start())

    public void lose(){
        sPane.getChildren().addAll(new ImageView(new Image("gameover.gif")));
    }

    public void win() {
        sPane.getChildren().addAll(new ImageView(new Image("goodjob.gif")));
    }

    public boolean isConnected(){
        fill2DArr();

        // while these conditions exist
        boolean isEndPoint = false;
        boolean outOfBounds = false;
        boolean hasNeightbor = true;
        int lastYDirection = 0;
        int lastXDirection = 0;

        PipeButton currentPipe = buttons2DArr.get(4).get(0);
        PipeButton nextPipe;

        boolean connectionSuccess = false;
        int counter = 0;

        while(true) {
            System.out.println("Looping...\nCurrent Pipe:\n" + currentPipe.toString() + "\n");
            counter++;
            currentPipe.setSeen(true);
            nextPipe = null;
            boolean []currentPipeDirection = currentPipe.getDirection();
            if(currentPipe.getY() == 4 && currentPipe.getX() == 9){
                connectionSuccess = true;
                break;
            }
            // check if it has a neighbor to the right
            int coords[] = getNeighborCoords(currentPipe,1,0);

            if(coords != null && lastXDirection != -1){ // if neighbor exists
                nextPipe = buttons2DArr.get(coords[1]).get(coords[0]); // maybe swawp indeices
                System.out.println("Candidate found to the right:\n" + nextPipe.toString() + "\n");
                //if(nextPipe.wasSeen()) break;

                boolean []nextPipeDirection = nextPipe.getDirection();


                if(currentPipeDirection[1] && nextPipeDirection[0]){    // check if pipe has opening for current pipe to connect
                    currentPipe = nextPipe;
                    lastXDirection = 1;
                    lastYDirection = 0;
                    System.out.println("They connect!\n");
                    continue;
                }

                System.out.println("Candidate was invalid.\n");

            }

            // check below
            coords = getNeighborCoords(currentPipe,0,1);
            if(coords != null && lastYDirection != -1){ // if neighbor exists
                nextPipe = buttons2DArr.get(coords[1]).get(coords[0]);
                System.out.println("Candidate found below:\n" + nextPipe.toString() + "\n");
                //if(nextPipe.wasSeen()) break;
                boolean []nextPipeDirection = nextPipe.getDirection();


                if(currentPipeDirection[3] && nextPipeDirection[2]){    // check if pipe has opening for current pipe to connect
                    currentPipe = nextPipe;
                    lastYDirection = 1;
                    lastXDirection = 0;
                    System.out.println("They connect!\n");
                    continue;
                }

                System.out.println("Candidate was invalid.\n");

            }
            // check up
            coords = getNeighborCoords(currentPipe,0,-1);
            if(coords != null && lastYDirection != 1){ // if neighbor exists
                nextPipe = buttons2DArr.get(coords[1]).get(coords[0]);
                System.out.println("Candidate found above:\n" + nextPipe.toString() + "\n");
                //if(nextPipe.wasSeen()) break;
                boolean []nextPipeDirection = nextPipe.getDirection();


                if(currentPipeDirection[2] && nextPipeDirection[3]){    // check if pipe has opening for current pipe to connect
                    currentPipe = nextPipe;
                    lastXDirection = 0;
                    lastYDirection = -1;
                    System.out.println("They connect!\n");
                    continue;
                }

                System.out.println("Candidate was invalid.\n");

            }

            // check left
            coords = getNeighborCoords(currentPipe,-1,0);
            if(coords != null && lastXDirection != 1){ // if neighbor exists
                nextPipe = buttons2DArr.get(coords[1]).get(coords[0]);
                System.out.println("Candidate found to the left:\n" + nextPipe.toString() + "\n");
                //if(nextPipe.wasSeen()) break;
                boolean []nextPipeDirection = nextPipe.getDirection();


                if(currentPipeDirection[0] && nextPipeDirection[1]){    // check if pipe has opening for current pipe to connect
                    currentPipe = nextPipe;
                    lastXDirection = -1;
                    lastYDirection = 0;
                    System.out.println("They connect!\n");
                    continue;
                }

                System.out.println("Candidate was invalid.\n");



            }

            if(counter >= 10001){
                System.out.println("Infinite loop detected. Terminating while loop.");
                break;
            }


        } // end of while


        return connectionSuccess;



    }

    public int[] getNeighborCoords(PipeButton currentPipe, int xDir, int yDir){
        int newX = currentPipe.getX()+xDir;
        int newY = currentPipe.getY()+yDir;

        // if the index is valid AND the pipe there is valid
        if(isValidIndex(newX,newY) && buttons2DArr.get(newY).get(newX).getPipeNumber() != 0){
            // index is valid - do stuff here
            int []temp = {newX,newY};
            return temp;
        }

        return null;
    }

    public void fill2DArr(){
        buttons2DArr = new ArrayList<ArrayList<PipeButton>>();
        buttons2DArr.ensureCapacity(9);

        assert buttonsArr.size() > 0;
        // initiallize inner arrays
        for(int i = 0; i < 10; i++){
            ArrayList<PipeButton> temp;
            //temp = buttons2DArr.get(i);
            temp = new ArrayList<PipeButton>();
            temp.ensureCapacity(9);
            buttons2DArr.add(i,temp);
        }
        assert buttons2DArr.get(4) != null;

        for(int i = 0; i < buttonsArr.size(); i++){
            int xIndex = buttonsArr.get(i).getX();
            int yIndex = buttonsArr.get(i).getY();


            buttons2DArr.get(yIndex).add(xIndex,buttonsArr.get(i));
        }
    }

    public static boolean isValidIndex(int x, int y){
        return ((x >= 0 && x <= 9) && (y >= 0 && y <= 9));
    }

}
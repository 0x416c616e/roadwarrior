import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Array;


public class PipeButton extends Button {

    /*

    Directions





     */


    //private boolean facingLeft, facingRight, facingUp, facingDown;
//    private int xCoord, yCoord;
    private boolean[] direction;
    private ImageView imgView;
    private int x,y;
    private boolean seen;

    public boolean isObstacle() {
        return isObstacle;
    }

    public void setObstacle(boolean obstacle) {
        isObstacle = obstacle;
    }

    private boolean isObstacle;

    // pipe is blank by default
    public PipeButton(){
        super();
        direction = new boolean[4];
        direction[0] = false;
        direction[1] = false;
        direction[2] = false;
        direction[3] = false;
        //imgView = new ImageView(new Image("pipe0.png"));
        updateImgView();
        // direction = {left,right,up,down}
        //direction[] = {true,true,false,false};
        seen = false;


    }

    public PipeButton(int x, int y){
        super();
        direction = new boolean[4];
        direction[0] = false;
        direction[1] = false;
        direction[2] = false;
        direction[3] = false;
        //imgView = new ImageView(new Image("pipe0.png"));
        updateImgView();
        this.x = x;
        this.y = y;
        // direction = {left,right,up,down}
        //direction[] = {true,true,false,false};
        seen = false;

    }


    public PipeButton(boolean left, boolean right, boolean up, boolean down){
        super();
        // if no direction, cell is blank
        direction[0] = left;
        direction[1] = right;
        direction[2] = up;
        direction[3] = down;

        assert checkValid();

        updateImgView();
        seen = false;

    }

    public boolean checkValid(){
        // sum of facing sides; cannot be greater than 2
        int sum = 0;
        for(int i = 0; i < direction.length; i++){
            sum += ((direction[i]) ? 1 : 0);
        }


        return sum <= 2;
    }


    // used to determine which image to use for the button
    public int getPipeNumber(){
        /*
        0 - none
        1 - horizontal
        2 - vertical
        3 - left/down
        4 - left/up
        5 - right/up
        6 - right/down


         */
        //direction[] = {left,right,up,down};
        // check if none
        int temp = 0;
        for(int i = 0; i < direction.length; i++){
            temp += ((direction[i]) ? 1 : 0);
        }
        if(temp == 0)
            return 0;

        // check horizontal
        if(direction[0] && direction[1])
            return 1;

        // check vertical
        if(direction[2] && direction[3])
            return 2;

        // check left/down
        if(direction[0] && direction[3])
            return 3;

        // check left/up
        if(direction[0] && direction[2])
            return 4;

        // check right/up
        if(direction[1] && direction[2])
            return 5;

        // check right/down
        if(direction[1] && direction[3])
            return 6;



        System.err.println("No direction could be determined for this object.\nPipeButton getPipeNumber()");
        return -1;

    }
    /*
        0 - none
        1 - horizontal
        2 - vertical
        3 - left/down
        4 - left/up
        5 - right/up
        6 - right/down


         */
    public void togglePipeDirection() {
        int currentDirection = getPipeNumber();
        int newDirection = currentDirection;
        if(currentDirection == 6){
            newDirection = 0;
        } else {
            newDirection = currentDirection + 1;
        }

        switch (newDirection) {
            case 0: setDirection(false,false,false,false);
                    break;
            case 1: setDirection(true,true,false,false);
                    break;
            case 2: setDirection(false,false,true,true);
                    break;
            case 3: setDirection(true,false,false,true);
                    break;
            case 4: setDirection(true,false,true,false);
                    break;
            case 5: setDirection(false,true,true,false);
                    break;
            case 6: setDirection(false,true,false,true);
                    break;
            case 7: setDirection(false,true,false,false);
                    break;
            case 8: setDirection(true,false,false,false);
                    break;
            default:
                    System.err.println("New direction could not be found.\nPipeButton - togglePipeDirection()");

        }



        assert checkValid();

        updateImgView();
    }

    public boolean[] getDirection(){
        return direction;
    }

    public void setDirection(boolean left, boolean right, boolean up, boolean down){
        direction[0] = left;
        direction[1] = right;
        direction[2] = up;
        direction[3] = down;

        assert checkValid();

        updateImgView();
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void updateImgView(){
        if(getPipeNumber() != 7 && getPipeNumber() != 8){
            imgView = new ImageView(new Image("pipe" + getPipeNumber() + ".gif"));
            setGraphic(imgView);
        } else if(getPipeNumber() == 7){
            imgView = new ImageView(new Image("start.png"));
        } else {
            imgView = new ImageView(new Image("finish.png"));
        }

    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setSeen(boolean b){
        seen = b;
    }

    public boolean wasSeen(){
        return seen;
    }

    public String toString(){
        // coordinates
        // direction
        // been seen
        String s = "";
        s = "Location: (" + x + "," + y + ")\n" + "Direction: {" +
                direction[0] + ","+
                direction[1] + ","+
                direction[2] + ","+
                direction[3] + "}" + "\n" + "Been seen: " + seen;
        return s;
    }

    public static boolean pipesConnect(PipeButton a, PipeButton rightPipe){

        // figure out types of pipes (6 cases)






        return false;
    }





}
